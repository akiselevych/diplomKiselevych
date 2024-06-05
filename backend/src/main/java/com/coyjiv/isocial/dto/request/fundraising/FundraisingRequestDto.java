package com.coyjiv.isocial.dto.request.fundraising;

import com.coyjiv.isocial.domain.Volunteer;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
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
public class FundraisingRequestDto {

  @NotBlank
  private String name;

  @NotNull
  @Min(value = 1000,message = "Min value 1000")
  private int finalAmount;

  @NotBlank
  private String textContent;

}
