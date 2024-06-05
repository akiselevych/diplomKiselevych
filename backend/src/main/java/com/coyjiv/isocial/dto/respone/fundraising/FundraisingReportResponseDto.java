package com.coyjiv.isocial.dto.respone.fundraising;

import com.coyjiv.isocial.domain.AbstractEntity;
import com.coyjiv.isocial.domain.Fundraising;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FundraisingReportResponseDto {
  private Long id;
  private String textContent;
}
