package contest.blaybus.v1.application;


import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.presentation.dto.NewPwdDTO;
import contest.blaybus.v1.presentation.dto.NewProfileImageDTO;
import contest.blaybus.v1.presentation.dto.NewUuidDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        return MemberInfoResponse.fromEntity(member);
    }

    @Transactional
    public String changePwd(NewPwdDTO dto) {
        Member member = memberRepository.findById(dto.memberId()).orElseThrow(EntityNotFoundException::new);
        String encodedPassword = passwordEncoder.encode(dto.pwd());
        member.updatePwd(encodedPassword);
        return "성공";
    }

    public Boolean checkDupPwd(NewPwdDTO dto) {
        Member member = memberRepository.findById(dto.memberId()).orElseThrow(EntityNotFoundException::new);
        return member.getPassword().equals(dto.pwd());
    }

    @Transactional
    public String updateProfileImg(NewProfileImageDTO dto) {
        Member member = memberRepository.findById(dto.memberId()).orElseThrow(EntityNotFoundException::new);
        if(Objects.equals(member.getProfileImg(), dto.url())) {
            return "동일한 주소입니다.";
        }
        member.updateProfileImg(dto.url());
        return "성공";
    }

    @Transactional
    public String updateUuid(NewUuidDTO dto) {
        Member member = memberRepository.findById(dto.memberId()).orElseThrow(EntityNotFoundException::new);
        if(Objects.equals(member.getFcmToken(), dto.uuid())) {
            return "동일한 UUID입니다.";
        }
        member.updateFcmToken(dto.uuid());
        return "성공";
    }
}
