package com.coyjiv.isocial.dto.respone.volunteer;

import com.coyjiv.isocial.domain.AbstractEntity;
import com.coyjiv.isocial.domain.Fundraising;
import com.coyjiv.isocial.dto.respone.fundraising.FundraisingResponseDto;
import com.coyjiv.isocial.dto.respone.user.UserSearchResponseDto;
import lombok.*;

import java.util.List;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerResponseDto {
  private Long id;
  private UserSearchResponseDto user;
  private List<FundraisingResponseDto> fundraisings;
}
