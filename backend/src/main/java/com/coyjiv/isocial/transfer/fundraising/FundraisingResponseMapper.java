package com.coyjiv.isocial.transfer.fundraising;


import com.coyjiv.isocial.domain.Fundraising;
import com.coyjiv.isocial.domain.Volunteer;
import com.coyjiv.isocial.dto.respone.fundraising.FundraisingResponseDto;
import com.coyjiv.isocial.dto.respone.volunteer.VolunteerResponseDto;
import com.coyjiv.isocial.service.friend.IFriendService;
import com.coyjiv.isocial.transfer.DtoMapperFacade;
import com.coyjiv.isocial.transfer.volunteer.VolunteerResponseMapper;
import org.springframework.stereotype.Service;

@Service
public class FundraisingResponseMapper extends DtoMapperFacade<Fundraising, FundraisingResponseDto> {

  private final FundraisingReportResponseMapper fundraisingReportResponseMapper;

  public FundraisingResponseMapper(FundraisingReportResponseMapper fundraisingReportResponseMapper) {
    super(Fundraising.class, FundraisingResponseDto.class);
    this.fundraisingReportResponseMapper = fundraisingReportResponseMapper;
  }

  @Override
  protected void decorateDto(FundraisingResponseDto dto, Fundraising entity) {
    dto.setVolunteerId(entity.getVolunteer().getId());
    if (entity.getFundraisingReport() != null){
      dto.setFundraisingReport(fundraisingReportResponseMapper.convertToDto(entity.getFundraisingReport()));
    }
  }
}
