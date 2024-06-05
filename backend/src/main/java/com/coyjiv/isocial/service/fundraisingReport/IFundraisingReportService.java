package com.coyjiv.isocial.service.fundraisingReport;

import com.coyjiv.isocial.domain.Fundraising;
import com.coyjiv.isocial.domain.FundraisingReport;
import com.coyjiv.isocial.dto.request.fundraising.FundraisingReportRequestDto;
import com.coyjiv.isocial.dto.respone.fundraising.FundraisingReportResponseDto;
import com.coyjiv.isocial.dto.respone.page.PageWrapper;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IFundraisingReportService {

  FundraisingReport findById(Long id);

  FundraisingReportResponseDto create(FundraisingReportRequestDto reportRequestDto, MultipartFile file) throws FileUploadException;

  FundraisingReportResponseDto update(FundraisingReportRequestDto reportRequestDto, MultipartFile file, Long id);

  void delete(Long id);

}
