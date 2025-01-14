package contest.blaybus.v1.security;

public interface TokenProviderService {
    String create(String identificationNumber);

    boolean validate(String token);

    String extract(String token);
}
