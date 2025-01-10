package vn.edward.jobhunter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SkillController {
  private final SkillService skillService;

  public SkillController(SkillService skillService) {
    this.skillService = skillService;
  }

}
