package vn.edward.jobhunter.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import vn.edward.jobhunter.domain.response.ResLoginDTO;

public class SecurityUtil {
  private final JwtEncoder jwtEncoder;

  public SecurityUtil(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

  @Value("${jwt.base64-secret}")
  private String jwtKey;

  @Value("${jwt.access-token-validity-in-seconds}")
  private long accessTokenExpiration;

  @Value("${jwt.refresh-token-validity-in-seconds}")
  private long refreshTokenExpiration;

  public String createAccessToken(String email, ResLoginDTO dto) {
    ResLoginDTO.UserInsideToken userToken = new ResLoginDTO.UserInsideToken();
    userToken.setId(dto.getUser().getId());
    userToken.setEmail(dto.getUser().getEmail());
    userToken.setName(dto.getUser().getName());

    Instant now = Instant.now();
    Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);

    List<String> listAuthority = new ArrayList<String>();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuedAt(now)
        .expiresAt(validity)
        .subject(email)
        .claim("user", userToken)
        .claim("permission", listAuthority)
        .build();

    JwsHeader jwtHeader = JwsHeader.with(JWT_ALGORITHM).build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(jwtHeader, claims)).getTokenValue();

  }

  public String createRefreshToken(String email, ResLoginDTO dto) {
    Instant now = Instant.now();
    Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);

    ResLoginDTO.UserInsideToken userToken = new ResLoginDTO.UserInsideToken();
    userToken.setId(dto.getUser().getId());
    userToken.setEmail(dto.getUser().getEmail());
    userToken.setName(dto.getUser().getName());

    // @formatter:off
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuedAt(now)
        .expiresAt(validity)
        .subject(email)
        .claim("user", userToken)
        .build();

    JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
}



}