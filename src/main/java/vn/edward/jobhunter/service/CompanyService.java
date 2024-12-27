package vn.edward.jobhunter.service;

import vn.edward.jobhunter.domain.Company;
import vn.edward.jobhunter.repository.CompanyRepository;
import vn.edward.jobhunter.repository.UserRepository;

public class CompanyService {
  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;

  public CompanyService(
      CompanyRepository companyRepository,
      UserRepository userRepository) {
    this.companyRepository = companyRepository;
    this.userRepository = userRepository;
  }

  public Company handleCreateCompany(Company c) {
    return this.companyRepository.save(c);
  }

}
