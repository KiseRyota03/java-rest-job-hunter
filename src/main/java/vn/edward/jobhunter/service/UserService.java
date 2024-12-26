package vn.edward.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.edward.jobhunter.domain.User;
import vn.edward.jobhunter.domain.response.ResCreateUserDTO;
import vn.edward.jobhunter.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;
  // private final CompanyService companyService;
  // private final RoleService roleService;

  public UserService(UserRepository userRepository
  // CompanyService companyService,
  // RoleService roleService
  ) {
    this.userRepository = userRepository;
    // this.companyService = companyService;
    // this.roleService = roleService;
  }

  public boolean isEmailExist(String email) {
    return this.userRepository.existsByEmail(email);
  }

  public User handleCreateUser(User user) {
    // check company
    // if (user.getCompany() != null) {
    // Optional<Company> companyOptional =
    // this.companyService.findById(user.getCompany().getId());
    // user.setCompany(companyOptional.isPresent() ? companyOptional.get() : null);
    // }

    // check role
    // if (user.getRole() != null) {
    // Role r = this.roleService.fetchById(user.getRole().getId());
    // user.setRole(r != null ? r : null);
    // }

    return this.userRepository.save(user);
  }

  public ResCreateUserDTO convertToResCreateUserDTO(User user) {
    ResCreateUserDTO res = new ResCreateUserDTO();
    // ResCreateUserDTO.CompanyUser com = new ResCreateUserDTO.CompanyUser();

    res.setId(user.getId());
    res.setEmail(user.getEmail());
    res.setName(user.getName());
    res.setAge(user.getAge());
    res.setCreatedAt(user.getCreatedAt());
    res.setGender(user.getGender());
    res.setAddress(user.getAddress());

    // if (user.getCompany() != null) {
    // com.setId(user.getCompany().getId());
    // com.setName(user.getCompany().getName());
    // res.setCompany(com);
    // }
    return res;
  }

}
