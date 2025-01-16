package contest.blaybus.v1.application;

import contest.blaybus.v1.application.exception.AlreadyOurMemberException;
import contest.blaybus.v1.domain.Admin;
import contest.blaybus.v1.domain.JobType;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.Team;
import contest.blaybus.v1.domain.repository.AdminRepository;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.presentation.dto.CheckPwdDTO;
import contest.blaybus.v1.presentation.dto.ModifyDateDTO;
import contest.blaybus.v1.presentation.dto.ModifyJobTypeDTO;
import contest.blaybus.v1.presentation.dto.ModifyNameDTO;
import contest.blaybus.v1.presentation.dto.ModifyNumberDTO;
import contest.blaybus.v1.presentation.dto.ModifyPwdDTO;
import contest.blaybus.v1.presentation.dto.ModifyTeamDTO;
import contest.blaybus.v1.presentation.dto.NewMemberDTO;
import jakarta.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.identificationNumber}")
    private String adminIdentificationNumber;

    @Value("${admin.password}")
    private String adminPassWord;

    @Transactional
    public String addMember(NewMemberDTO dto) throws ParseException {
        String encodePassword = passwordEncoder.encode(dto.pwd());

        Boolean isExists = memberRepository.existsByPersonalId(dto.id());
        if (isExists) {
            throw new AlreadyOurMemberException(dto.id());
        }

        final Member newMember = Member.builder()
                .name(dto.name())
                .team(Team.parsing(dto.team()))
                .identificationNumber(dto.number())
                .personalId(dto.id())
                .pwd(encodePassword)
                .jobType(JobType.parsing(dto.jobType()))
                .date((new SimpleDateFormat("yyyy-MM-dd")).parse(dto.date()))
                .build();

        memberRepository.save(newMember);
        return "성공";
    }

    public List<String> getTeamList() {
        return Stream.of(Team.values())
                .map(Team::getDisplayName)
                .collect(Collectors.toList());
    }

    public List<String> getJobTypeList() {
        return Stream.of(JobType.values())
                .map(JobType::getDescription)
                .collect(Collectors.toList());
    }

    public Boolean checkDupId(String id) {
        return !memberRepository.existsByPersonalId(id); // 존재하면 true 리턴
    }

    public Boolean checkPwdRule(CheckPwdDTO dto) {
        String password = dto.password();
        if (password == null || password.isEmpty()) {
            return false;
        }
        // (?=.*[A-Za-z]): 최소 하나 이상의 영문자가 포함 + (?=.*\\d): 최소 하나 이상의 숫자가 포함 + [A-Za-z\\d]{8,}: 영문자와 숫자 조합으로 8자리 이상
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return Pattern.matches(regex, password);
    }

    public List<MemberInfoResponse> searchByTeam(String requestTeam) {
        final Team team = Team.parsing(requestTeam);
        return memberRepository.findByTeam(team)
                .stream()
                .map(MemberInfoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MemberInfoResponse> searchByNameOrNumber(String keyword) {
        /** 삼항 연산자로 줄이려하니 가독성이 떨어짐 */

        // 사번일 경우 10자리 숫자에 해당
        final boolean isNumber = keyword.matches("\\d{10}");
        if (isNumber) {
            // 사번으로 사원 조회
            return memberRepository.findByIdentificationNumber(keyword)
                    .stream()
                    .map(MemberInfoResponse::fromEntity).collect(Collectors.toList());
        } else {
            // 이름으로 사원 조회
            return memberRepository.findByName(keyword)
                    .stream()
                    .map(MemberInfoResponse::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    @PostConstruct
    @Transactional
    public void initAdmin() {

        if (adminIdentificationNumber == null || adminPassWord == null) {
//            throw new RuntimeException("관리자 계정이 올바르지 않습니다.");
            return;
        }

        String encodedPassword = passwordEncoder.encode(adminPassWord);

        Optional<Admin> adminOptional = adminRepository.findByIdentificationNumber(adminIdentificationNumber);

        if (adminOptional.isEmpty()) {
            Admin newAdmin = Admin.builder()
                    .identificationNumber(adminIdentificationNumber)
                    .password(encodedPassword)
                    .build();
            adminRepository.save(newAdmin);
        }

    }

    @Transactional
    public Boolean modifyName(ModifyNameDTO dto) {
        return memberRepository.findById(dto.memberId())
                .map(member -> {
                    if (member.getName().equals(dto.name())) {return false;}
                    member.updateName(dto.name());
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Transactional
    public Boolean modifyTeam(ModifyTeamDTO dto) {
        return memberRepository.findById(dto.memberId())
                .map(member -> {
                    if(member.getTeam().getDisplayName().equals(dto.team())) {return false;}
                    member.updateTeam(dto.team());
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Transactional
    public Boolean modifyNumber(ModifyNumberDTO dto) {
        return memberRepository.findById(dto.memberId())
                .map(member -> {
                    if(member.getIdentificationNumber().equals(dto.number())) {return false;}
                    member.updateNumber(dto.number());
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Transactional
    public Boolean modifyJobType(ModifyJobTypeDTO dto) {
        return memberRepository.findById(dto.memberId())
                .map(member -> {
                    if(member.getJobType().getDescription().equals(dto.jobType())) {return false;}
                    member.updateJobType(dto.jobType());
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Transactional
    public Boolean modifyPwd(ModifyPwdDTO dto) {
        return memberRepository.findById(dto.memberId())
                .map(member -> {
                    if(passwordEncoder.matches(dto.pwd(), member.getPassword())) {return false;}
                    member.updatePwd(passwordEncoder.encode(dto.pwd()));
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Transactional
    public Boolean modifyDate(ModifyDateDTO dto) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date hireDate = formatter.parse(dto.date());

        return memberRepository.findById(dto.memberId())
                .map(member -> {
                    if(member.getEffectiveDate().equals(hireDate)) {return false;}
                    member.updateDate(hireDate);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

}
