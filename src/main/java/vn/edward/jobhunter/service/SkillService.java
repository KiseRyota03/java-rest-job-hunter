package vn.edward.jobhunter.service;

import org.springframework.stereotype.Service;

@Service
public class SkillService {
  private final SkillRepository skillRepository;

  public SkillService(SkillRepository skillRepository) {
    this.skillRepository = skillRepository;
  }

}
