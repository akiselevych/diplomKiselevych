package com.coyjiv.isocial.dto.request.fundraising;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FundraisingReportRequestDto {
  @NotBlank
  private String textContent;

  @NotNull
  private Long fundraisingId;
}
