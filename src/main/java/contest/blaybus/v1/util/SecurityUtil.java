package contest.blaybus.v1.util;

import contest.blaybus.v1.domain.Admin;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.presentation.exception.EmptyDataException;
import contest.blaybus.v1.presentation.exception.LoginFailException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new LoginFailException();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Member) {
            return (Member) principal;
        }

        throw new EmptyDataException();
    }

    public static Admin getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new LoginFailException();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin) {
            return (Admin) principal;
        }

        throw new EntityNotFoundException();
    }
}
