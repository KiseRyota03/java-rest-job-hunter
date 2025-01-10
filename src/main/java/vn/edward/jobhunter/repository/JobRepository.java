package vn.edward.jobhunter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.edward.jobhunter.domain.Job;
import vn.edward.jobhunter.domain.Skill;

@Repository
public class JobRepository extends JpaRepository<Job, Long>,JpaSpecificationExecutor<Job>
{

  List<Job> findBySkillsIn(List<Skill> skills);

}
