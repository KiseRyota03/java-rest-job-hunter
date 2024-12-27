package vn.edward.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.edward.jobhunter.domain.Company;
import vn.edward.jobhunter.domain.response.ResultPaginationDTO;
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

  public ResultPaginationDTO handleGetCompany(Specification<Company> spec, Pageable pageable) {
    Page<Company> pCompany = this.companyRepository.findAll(spec, pageable);
    ResultPaginationDTO rs = new ResultPaginationDTO();
    ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

    mt.setPage(pageable.getPageNumber() + 1);
    mt.setPageSize(pageable.getPageSize());

    mt.setPages(pCompany.getTotalPages());
    mt.setTotal(pCompany.getTotalElements());

    rs.setMeta(mt);
    rs.setResult(pCompany.getContent());
    return rs;
  }

}
