package com.coyjiv.isocial.dto.respone;

import com.coyjiv.isocial.domain.AbstractEntity;
import com.coyjiv.isocial.domain.Fundraising;
import com.coyjiv.isocial.domain.User;
import com.coyjiv.isocial.dto.respone.user.UserSearchResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VolunteerResponseDto extends AbstractEntity {
  private UserSearchResponseDto user;
  private List<Fundraising> fundraisings;
}
