package vn.edward.jobhunter.controller;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.turkraft.springfilter.boot.Filter;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import vn.edward.jobhunter.domain.Company;
import vn.edward.jobhunter.domain.response.ResultPaginationDTO;
import vn.edward.jobhunter.service.CompanyService;
import vn.edward.jobhunter.util.annotation.ApiMessage;

public class CompanyController {
  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @PostMapping("/companies")
  public ResponseEntity<?> createCompany(@Valid @RequestBody Company reqCompany) {

    return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(reqCompany));
  }

  @GetMapping("/companies")
  @ApiMessage("Fetch companies")
  public ResponseEntity<ResultPaginationDTO> getCompany(
      @Filter Specification<Company> spec, Pageable pageable) {

    return ResponseEntity.ok(this.companyService.handleGetCompany(spec, pageable));
  }

}
