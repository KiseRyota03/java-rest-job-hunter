package vn.edward.jobhunter.domain.response;

import lombok.Getter;
import lombok.Setter;

public class ResultPaginationDTO {
  private Meta meta;
  private Object result;

  @Getter
  @Setter
  public static class Meta {
    private int page;
    private int pageSize;
    private int pages;
    private long total;
  }
}
