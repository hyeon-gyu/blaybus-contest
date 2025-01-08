package contest.blaybus.v1.application;


import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        return MemberInfoResponse.fromEntity(member);
    }
}
