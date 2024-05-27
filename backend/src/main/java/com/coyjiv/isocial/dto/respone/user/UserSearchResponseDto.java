package com.coyjiv.isocial.dto.respone.user;

import com.coyjiv.isocial.domain.UserActivityStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResponseDto {
  private Long id;
  private String firstName;
  private String lastName;
  @JsonProperty("is_volunteer")
  private boolean isVolunteer;
  private List<String> avatarsUrl;
  private UserActivityStatus activityStatus;
}
