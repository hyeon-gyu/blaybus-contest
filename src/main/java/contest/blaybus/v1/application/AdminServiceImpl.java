package contest.blaybus.v1.application;

import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.repository.AdminRepository;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.presentation.dto.NewMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;

    @Override
    public String addMember(NewMemberDTO dto) {
        memberRepository.save(new Member(dto.number(), dto.pwd()));
        return "성공";
    }
}
