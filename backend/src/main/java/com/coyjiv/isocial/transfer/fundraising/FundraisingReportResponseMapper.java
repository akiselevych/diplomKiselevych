package com.coyjiv.isocial.transfer.fundraising;


import com.coyjiv.isocial.domain.Fundraising;
import com.coyjiv.isocial.domain.FundraisingReport;
import com.coyjiv.isocial.dto.respone.fundraising.FundraisingReportResponseDto;
import com.coyjiv.isocial.dto.respone.fundraising.FundraisingResponseDto;
import com.coyjiv.isocial.service.friend.IFriendService;
import com.coyjiv.isocial.transfer.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class FundraisingReportResponseMapper extends DtoMapperFacade<FundraisingReport, FundraisingReportResponseDto> {

  public FundraisingReportResponseMapper() {
    super(FundraisingReport.class, FundraisingReportResponseDto.class);
  }


}
