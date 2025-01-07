package vn.edward.jobhunter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import vn.edward.jobhunter.domain.User;
import vn.edward.jobhunter.domain.request.ReqLoginDTO;
import vn.edward.jobhunter.domain.response.ResLoginDTO;
import vn.edward.jobhunter.service.UserService;
import vn.edward.jobhunter.util.SecurityUtil;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final SecurityUtil securityUtil;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Value("${jwt.refresh-token-validity-in-seconds}")
  private long refreshTokenExpiration;

  public AuthController(
      AuthenticationManagerBuilder authenticationManagerBuilder,
      SecurityUtil securityUtil,
      UserService userService,
      PasswordEncoder passwordEncoder) {
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.securityUtil = securityUtil;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/auth/login")
  public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody ReqLoginDTO loginDto) {
    // Nạp input gồm username/password vào Security
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        loginDto.getUsername(), loginDto.getPassword());

    // xác thực người dùng => cần viết hàm loadUserByUsername
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);

    // set thông tin người dùng đăng nhập vào context (có thể sử dụng sau này)
    SecurityContextHolder.getContext().setAuthentication(authentication);

    ResLoginDTO res = new ResLoginDTO();
    User currentUserDB = this.userService.handleGetUserByUsername(loginDto.getUsername());
    if (currentUserDB != null) {
      ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
          currentUserDB.getId(),
          currentUserDB.getEmail(),
          currentUserDB.getName(),
          currentUserDB.getRole());
      res.setUser(userLogin);
    }

    // create access token
    String access_token = this.securityUtil.createAccessToken(authentication.getName(), res);
    res.setAccessToken(access_token);

    // create refresh token
    String refresh_token = this.securityUtil.createRefreshToken(loginDto.getUsername(), res);

    // update user
    this.userService.updateUserToken(refresh_token, loginDto.getUsername());

    // set cookies
    ResponseCookie resCookies = ResponseCookie
        .from("refresh_token", refresh_token)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(refreshTokenExpiration)
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, resCookies.toString())
        .body(res);
  }

}
