package vn.edward.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import vn.edward.jobhunter.util.annotation.ApiMessage;
import vn.edward.jobhunter.util.error.IdInvalidException;
import vn.edward.jobhunter.domain.User;
import vn.edward.jobhunter.domain.response.ResCreateUserDTO;
import vn.edward.jobhunter.service.UserService;

public class UserController {
  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/users")
  @ApiMessage("Create a new user")
  public ResponseEntity<ResCreateUserDTO> createNewUser(@Valid @RequestBody User postManUser)
      throws IdInvalidException {
    boolean isEmailExist = this.userService.isEmailExist(postManUser.getEmail());
    if (isEmailExist)
      throw new IdInvalidException(
          "Email " + postManUser.getEmail() + "đã tồn tại, vui lòng sử dụng email khác.");

    {
      String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
      postManUser.setPassword(hashPassword);
      User ericUser = this.userService.handleCreateUser(postManUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(ericUser));
    }

  }
}