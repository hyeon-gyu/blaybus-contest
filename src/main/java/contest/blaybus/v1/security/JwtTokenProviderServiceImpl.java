package contest.blaybus.v1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProviderServiceImpl implements TokenProviderService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration-period-hours}")
    private long expirationPeriodHours;

    private Key key;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String create(String personalId) {
        Claims claims = createClaims(personalId);
        Date now = issuedAt();
        Date expirationDate = expiredAt(now);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("잘못된 키 입니다.");
        }
    }

    @Override
    public String extract(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("잘못된 키 입니다.");
        }
    }

    private Claims createClaims(String personalId) {
        return Jwts.claims().setSubject(personalId);
    }

    private Date issuedAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date expiredAt(Date now) {
        LocalDateTime expiration = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault())
                .plusHours(expirationPeriodHours);
        return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());
    }

}