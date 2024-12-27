package vn.edward.jobhunter.domain;

import java.time.Instant;
import java.util.List;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Table(name = "companies")
@Entity
@Getter
@Setter
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank(message = "name không được để trống")
  private String name;

  @Column(columnDefinition = "MEDIUMTEXT")
  private String description;

  private String address;

  private String logo;

  private Instant createdAt;

  private Instant updatedAt;

  private String createdBy;

  private String updatedBy;

  @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
  @JsonIgnore
  List<User> users;

  // @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
  // @JsonIgnore
  // List<Job> jobs;

  // @PrePersist
  // public void handleBeforeCreate() {
  // this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
  // ? SecurityUtil.getCurrentUserLogin().get()
  // : "";

  // this.createdAt = Instant.now();
  // }

  // @PreUpdate
  // public void handleBeforeUpdate() {
  // this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
  // ? SecurityUtil.getCurrentUserLogin().get()
  // : "";

  // this.updatedAt = Instant.now();
  // }

}
