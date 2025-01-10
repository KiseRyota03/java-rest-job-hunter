package vn.edward.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.edward.jobhunter.repository.CompanyRepository;

@Service
public class JobService {
  private final JobRepository jobRepository;
  // private final SkillRepository skillRepository;
  private final CompanyRepository companyRepository;

  public JobService(JobRepository jobRepository,
      // SkillRepository skillRepository,
      CompanyRepository companyRepository) {
    this.jobRepository = jobRepository;
    // this.skillRepository = skillRepository;
    this.companyRepository = companyRepository;
  }

}
