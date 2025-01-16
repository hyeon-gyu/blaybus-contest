package contest.blaybus.v1.application;

import contest.blaybus.v1.domain.ExpType;
import contest.blaybus.v1.domain.ExperiencePointHistory;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.NotificationCategory;
import contest.blaybus.v1.domain.repository.ExpHistoryRepository;
import contest.blaybus.v1.domain.repository.ExpRepository;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.infrastructure.dto.ExpBarResponse;
import contest.blaybus.v1.infrastructure.dto.ExpHistoryResponse;
import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;
import contest.blaybus.v1.infrastructure.dto.RecentExpInfoResponse;
import contest.blaybus.v1.presentation.dto.NewFcmDTO;
import contest.blaybus.v1.presentation.exception.EmptyDataException;
import contest.blaybus.v1.util.LevelCheckUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static contest.blaybus.v1.util.LevelCheckUtil.getNextLevel;
import static contest.blaybus.v1.util.LevelCheckUtil.levelInfoMap;


@Service
@RequiredArgsConstructor
public class ExpServiceImpl implements ExpService {

    private static final int medianAverageExp = 9000;

    private final ExpHistoryRepository expHistoryRepository;
    private final MemberRepository memberRepository;
    private final ExpRepository expRepository;

    private final FirebaseCloudMessageService fcmService;

    @Transactional
    public void updateMemberTotalExp(Member member) {
        LocalDate now = LocalDate.now();
        Long sumTotalExpUtilThisYear = expRepository.getSumTotalExpUtilThisYear(member, now.getYear());
        if (!(member.getTotalExp() == sumTotalExpUtilThisYear)) {
            member.updateTotalExp(sumTotalExpUtilThisYear);
            memberRepository.save(member);
        }
    }

    public ExpStatusResponse getExpStatus(Long memberId) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        updateMemberTotalExp(member); // 멤버 테이블 경험치 총 누적액 업데이트
        checkAndLevelUp(member); // 멤버 레벨 업데이트
        LocalDate now = LocalDate.now();
        // 작년 누적 경험치
        long lastYearExp = expRepository.findTotalExpByMemberAndYear(member, now.getYear() - 1)
                .orElseGet(() -> 0L);
        // 올해 획득한 경험치
        long thisYearExp = expRepository.findTotalExpByMemberAndYear(member, now.getYear())
                .orElseGet(() -> 0L);

        final List<Long> expInfo = getRequiredExpInfoForLevelUp(member);
        long requiredTotalExpForLevelUp = expInfo.get(0); // 다음 레벨에 필요한 총 경험치
        long remainingExp = expInfo.get(1); // 잔여경험치

        return ExpStatusResponse.builder()
                .currentLevel(member.getLevel())
                .totalExp(member.getTotalExp())
                .lastYearExp(lastYearExp)
                .thisYearExp(thisYearExp)
                .expForLevelup(requiredTotalExpForLevelUp)
                .remainingExp(remainingExp)
                .nextLevel(getNextLevel(member))
                .percent((int) Math.round((double) member.getTotalExp() / requiredTotalExpForLevelUp * 100))
                .build();
    }

    public RecentExpInfoResponse getRecentExpInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        ExperiencePointHistory expHistory = expHistoryRepository.findFirstByMemberOrderByDateDesc(member)
                .orElseThrow(() -> new EmptyDataException("조회되는 값이 없습니다."));

        return RecentExpInfoResponse.fromEntity(expHistory);
    }


    public ExpBarResponse getExpBar(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        // 작년까지 누적된 경험치 계산
        final Long totalExp = expRepository.getSumTotalExpUtilLastYear(member, LocalDate.now().getYear());
        // 현재 멤버의 레벨의 총 필요 경험치 산정
        final long expRequiredForNextLevel = LevelCheckUtil.getExpRequiredForNextLevel(member);
        double percentage = (totalExp.doubleValue() / expRequiredForNextLevel) * 100;
        return ExpBarResponse.builder()
                .percent(Math.round(percentage))
                .totalExp(totalExp)
                .build();
    }

    public ExpBarResponse getExpBarThisYear(Long memberId) {
        // experiencePoint 테이블에서 작년 날짜 기준 expTotal 값 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        final Long totalExpThisYear = expRepository.findTotalExpByMemberAndYear(member, LocalDate.now().getYear())
                .orElseThrow(() -> new EmptyDataException("조회되는 값이 없습니다."));
        double percentage = (totalExpThisYear.doubleValue() / medianAverageExp) * 100;
        return ExpBarResponse.builder()
                .percent(Math.round(percentage))
                .totalExp(totalExpThisYear)
                .build();
    }

    public List<ExpHistoryResponse> getExpHistoryPerCategory(Long memberId, String category, String order) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        Sort sort = "desc".equalsIgnoreCase(order) ? Sort.by("date").descending() : Sort.by("date").ascending();
        List<ExperiencePointHistory> histories;
        if (category.equals("all")) {
            histories = expHistoryRepository.findByMember(member, sort);
        } else {
            ExpType expType = ExpType.fromCode(category);
            histories = expHistoryRepository.findByMemberAndExpType(member, expType, sort);
        }

        return histories.stream()
                .map(ExpHistoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    private List<Long> getRequiredExpInfoForLevelUp(Member member) {
        List<LevelCheckUtil.LevelInfo> levels = levelInfoMap.get(member.getJobType());
        String currentLevel = member.getLevel();
        long totalExp = member.getTotalExp();

        for (int i = 0; i < levels.size(); i++) {
            if (levels.get(i).getLevel().equals(currentLevel)) {
                if (i + 1 <= levels.size()) {
                    final long requiredExp = levels.get(i + 1).getRequiredExp();
                    return List.of(requiredExp, requiredExp - totalExp);
                }
            }
        }
        return List.of(0L, 0L);
    }

    @Transactional
    public void checkAndLevelUp(Member member) throws IOException {
        List<LevelCheckUtil.LevelInfo> levels = levelInfoMap.get(member.getJobType());
        String newLevel = member.getLevel();
        for (int i = levels.size() - 1; i >= 0; i--) {
            LevelCheckUtil.LevelInfo levelInfo = levels.get(i);
            if (member.getTotalExp() >= levelInfo.getRequiredExp()) {
                newLevel = levelInfo.getLevel();
                break;
            }
        }
        // 레벨이 변경되었을 때만 업데이트
        if (!newLevel.equals(member.getLevel())) {
            member.updateLevel(newLevel);
            memberRepository.save(member);
            // 여기 자리에 레벨업 푸시알림 전송 로직 넣기

            fcmService.sendMessageTo(NewFcmDTO.builder()
                    .title("레벨업")
                    .content("\uD83C\uDF89레벨업 했어요!\uD83D\uDE80 축하드려요!")
                    .category(NotificationCategory.LEVELUP)
                    .build());
        }
    }
}
