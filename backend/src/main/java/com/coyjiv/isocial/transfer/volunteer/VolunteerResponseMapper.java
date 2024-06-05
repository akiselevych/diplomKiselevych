package com.coyjiv.isocial.transfer.volunteer;


import com.coyjiv.isocial.domain.Volunteer;
import com.coyjiv.isocial.dto.respone.fundraising.FundraisingResponseDto;
import com.coyjiv.isocial.dto.respone.volunteer.VolunteerResponseDto;
import com.coyjiv.isocial.transfer.DtoMapperFacade;
import com.coyjiv.isocial.transfer.fundraising.FundraisingResponseMapper;
import com.coyjiv.isocial.transfer.user.UserSearchResponseMapper;
import org.springframework.stereotype.Service;

@Service
public class VolunteerResponseMapper extends DtoMapperFacade<Volunteer, VolunteerResponseDto> {

  private final UserSearchResponseMapper userSearchResponseMapper;
  private final FundraisingResponseMapper fundraisingResponseMapper;

  public VolunteerResponseMapper(UserSearchResponseMapper userSearchResponseMapper,
                                 FundraisingResponseMapper fundraisingResponseMapper) {
    super(Volunteer.class, VolunteerResponseDto.class);
    this.fundraisingResponseMapper = fundraisingResponseMapper;
    this.userSearchResponseMapper = userSearchResponseMapper;
  }


  @Override
  protected void decorateDto(VolunteerResponseDto dto, Volunteer entity) {
    dto.setFundraisings(entity.getFundraisings().stream().map(fundraisingResponseMapper::convertToDto).toList());
    dto.setUser(userSearchResponseMapper.convertToDto(entity.getUser()));
  }
}
