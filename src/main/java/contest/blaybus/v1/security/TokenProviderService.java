package contest.blaybus.v1.security;

public interface TokenProviderService {
    String create(String personalId);

    boolean validate(String token);

    String extract(String token);
}
