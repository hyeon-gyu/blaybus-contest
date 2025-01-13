package contest.blaybus.v1.presentation.dto;

import contest.blaybus.v1.domain.JobType;
import jakarta.validation.constraints.NotBlank;

public record NewMemberDTO(
        @NotBlank(message = "성함은 필수 입력사항입니다.")
        String name,
        @NotBlank(message = "소속은 필수 입력사항입니다.")
        String team,
        @NotBlank(message = "사번은 필수 입력사항입니다.")
        String number,
        @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
        String pwd,
        @NotBlank(message = "직군은 필수 입력사항입니다.")
        String jobType,
        @NotBlank(message = "입사일은 필수 입력사항입니다.")
        String date) {}
