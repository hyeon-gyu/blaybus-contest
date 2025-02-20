package contest.blaybus.v1.application;


import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.presentation.dto.CheckPwdDTO;
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
    public String changePwd(Long memberId, NewPwdDTO dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        if(passwordEncoder.matches(dto.originPwd(), member.getPassword())) {
            member.updatePwd(passwordEncoder.encode(dto.newPwd()));
            return "성공";
        }
        return "실패";
    }

    public Boolean checkDupPwd(Long memberId, CheckPwdDTO dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        return passwordEncoder.matches(dto.password(), member.getPassword());
    }

    @Transactional
    public String updateProfileImg(Long memberId, NewProfileImageDTO dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        if(Objects.equals(member.getProfileImg(), dto.url())) {
            return "동일한 주소입니다.";
        }
        member.updateProfileImg(dto.url());
        return "성공";
    }

    @Transactional
    public String updateFcmToken(Long memberId, NewUuidDTO dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        if(Objects.equals(member.getFcmToken(), dto.token())) {
            return "동일한 FCM 토큰입니다.";
        }
        member.updateFcmToken(dto.token());
        return "성공";
    }
}
