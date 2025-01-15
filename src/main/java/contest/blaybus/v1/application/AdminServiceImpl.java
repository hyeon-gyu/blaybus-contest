package contest.blaybus.v1.application;

import contest.blaybus.v1.domain.Admin;
import contest.blaybus.v1.domain.JobType;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.Team;
import contest.blaybus.v1.domain.repository.AdminRepository;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.presentation.dto.NewMemberDTO;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    public String addMember(NewMemberDTO dto) throws ParseException {

        final Member newMember = Member.builder()
                .name(dto.name())
                .team(Team.parsing(dto.team()))
                .identificationNumber(dto.number())
                .pwd(dto.pwd())
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

}
