package contest.blaybus.v1.application;

import contest.blaybus.v1.domain.ExperiencePointHistory;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.repository.ExpHistoryRepository;
import contest.blaybus.v1.domain.repository.ExpRepository;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;
import contest.blaybus.v1.infrastructure.dto.RecentExpInfoResponse;
import contest.blaybus.v1.presentation.exception.EmptyDataException;
import contest.blaybus.v1.util.LevelCheckUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static contest.blaybus.v1.util.LevelCheckUtil.levelInfoMap;


@Service
@RequiredArgsConstructor
public class ExpServiceImpl implements ExpService {

    private static final int medianAverageExp = 9000;

    private final ExpHistoryRepository expHistoryRepository;
    private final MemberRepository memberRepository;
    private final ExpRepository expRepository;

    public ExpStatusResponse getExpStatus(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));


        // 작년 누적 경험치
        LocalDate now = LocalDate.now();
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
                .build();
    }

    public RecentExpInfoResponse getRecentExpInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        ExperiencePointHistory expHistory = expHistoryRepository.findFirstByMemberOrderByDateDesc(member)
                .orElseThrow(() -> new EmptyDataException("조회되는 값이 없습니다."));

        return RecentExpInfoResponse.fromEntity(expHistory);
    }

    public List<RecentExpInfoResponse> getRecentExpInfoList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        List<ExperiencePointHistory> expHistoryList = expHistoryRepository.findByMemberOrderByDateDesc(member);
        return expHistoryList.stream()
                        .map(RecentExpInfoResponse::fromEntity)
                        .collect(Collectors.toList());
    }

    public Long getExpBar(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        // 작년까지 누적된 경험치 계산
        final Long totalExp = expRepository.getSumTotalExpUtilLastYear(member, LocalDate.now().getYear());
        // 현재 멤버의 레벨의 총 필요 경험치 산정
        final long expRequiredForNextLevel = LevelCheckUtil.getExpRequiredForNextLevel(member);
        double percentage = (totalExp.doubleValue() / expRequiredForNextLevel) * 100;
        return Math.round(percentage);
    }

    public Long getExpBarThisYear(Long memberId) {
        // experiencePoint 테이블에서 작년 날짜 기준 expTotal 값 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        final Long totalExpThisYear = expRepository.findTotalExpByMemberAndYear(member, LocalDate.now().getYear())
                .orElseThrow(() -> new EmptyDataException("조회되는 값이 없습니다."));
        double percentage = (totalExpThisYear.doubleValue() / medianAverageExp) * 100;
        return Math.round(percentage);
    }

    private List<Long> getRequiredExpInfoForLevelUp(Member member) {
        List<LevelCheckUtil.LevelInfo> levels = levelInfoMap.get(member.getJobType());
        String currentLevel = member.getLevel();
        long totalExp = member.getTotalExp();

        for(int i = 0; i < levels.size(); i++) {
            if(levels.get(i).getLevel().equals(currentLevel)) {
                if(i+1 <= levels.size()) {
                    final long requiredExp = levels.get(i + 1).getRequiredExp();
                    return List.of(requiredExp, requiredExp - totalExp);
                }
            }
        }
        return List.of(0L, 0L);
    }
}
