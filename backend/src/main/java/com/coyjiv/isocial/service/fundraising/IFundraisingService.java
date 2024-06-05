package com.coyjiv.isocial.service.fundraising;

import com.coyjiv.isocial.dto.request.fundraising.FundraisingRequestDto;
import com.coyjiv.isocial.dto.respone.fundraising.FundraisingResponseDto;
import com.coyjiv.isocial.dto.respone.page.PageWrapper;

import java.util.List;

public interface IFundraisingService {

  PageWrapper<FundraisingResponseDto> findAll(int page, int size);

  List<FundraisingResponseDto> findAllForUser(Long userId);

  FundraisingResponseDto findById(Long id);

  void create(FundraisingRequestDto dto);

  void donate(Long fundId,int sum);

  void close(Long id) throws IllegalAccessException;

  void delete(Long id);
}
