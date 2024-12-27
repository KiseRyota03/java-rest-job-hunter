package vn.edward.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.edward.jobhunter.util.annotation.ApiMessage;
import vn.edward.jobhunter.util.error.IdInvalidException;
import vn.edward.jobhunter.domain.User;
import vn.edward.jobhunter.domain.response.ResCreateUserDTO;
import vn.edward.jobhunter.domain.response.ResUpdateUserDTO;
import vn.edward.jobhunter.domain.response.ResultPaginationDTO;
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

  @DeleteMapping("/users/{id}")
  @ApiMessage("Delete a user")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") long id)
      throws IdInvalidException {
    User currentUser = this.userService.fetchUserById(id);
    if (currentUser == null) {
      throw new IdInvalidException("User với id = " + id + " không tồn tại");
    }

    this.userService.handleDeleteUser(id);
    return ResponseEntity.ok(null);
  }

  // fetch all users
  @GetMapping("/users")
  @ApiMessage("fetch all users")
  public ResponseEntity<ResultPaginationDTO> getAllUser(
      @Filter Specification<User> spec,
      Pageable pageable) {

    return ResponseEntity.status(HttpStatus.OK).body(
        this.userService.fetchAllUser(spec, pageable));
  }

  @PutMapping("/users")
  @ApiMessage("Update a user")
  public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user) throws IdInvalidException {
    User updatedUser = this.userService.handleUpdateUser(user);
    if (updatedUser == null) {
      throw new IdInvalidException("User với id = " + user.getId() + " không tồn tại");
    }
    return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(updatedUser));
  }
}