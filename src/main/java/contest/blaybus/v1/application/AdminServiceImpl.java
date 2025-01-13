package contest.blaybus.v1.application;

import contest.blaybus.v1.domain.JobType;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.Team;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.presentation.dto.NewMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;

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

}
