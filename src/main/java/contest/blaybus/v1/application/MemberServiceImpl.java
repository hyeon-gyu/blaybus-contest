package contest.blaybus.v1.application;


import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.presentation.NewPwdDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        return MemberInfoResponse.fromEntity(member);
    }

    public String changePwd(NewPwdDTO dto) {
        Member member = memberRepository.findById(dto.memberId()).orElseThrow(EntityNotFoundException::new);
        member.updatePwd(dto.pwd());
        memberRepository.save(member);
        return "성공";
    }

    public Boolean checkDupPwd(NewPwdDTO dto) {
        Member member = memberRepository.findById(dto.memberId()).orElseThrow(EntityNotFoundException::new);
        return member.getPassword().equals(dto.pwd());
    }
}
