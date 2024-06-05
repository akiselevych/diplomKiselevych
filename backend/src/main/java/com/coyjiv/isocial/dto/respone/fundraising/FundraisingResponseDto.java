package com.coyjiv.isocial.dto.respone.fundraising;

import com.coyjiv.isocial.domain.AbstractEntity;
import com.coyjiv.isocial.domain.FundraisingReport;
import com.coyjiv.isocial.domain.Volunteer;
import com.coyjiv.isocial.dto.respone.volunteer.VolunteerResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FundraisingResponseDto {
  private Long id;
  private Long volunteerId;
  private String name;
  private boolean isClosed;
  private int finalAmount;
  private int actualAmount;
  private String textContent;


  private FundraisingReportResponseDto fundraisingReport;
}
