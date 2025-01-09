package contest.blaybus.v1.application;

import contest.blaybus.v1.domain.ExperiencePoint;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.repository.ExpRepository;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;
import contest.blaybus.v1.util.LevelCheckUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static contest.blaybus.v1.util.LevelCheckUtil.levelInfoMap;


@Service
@RequiredArgsConstructor
public class ExpServiceImpl implements ExpService {

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
