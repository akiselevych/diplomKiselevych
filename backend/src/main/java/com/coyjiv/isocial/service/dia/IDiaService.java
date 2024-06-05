package com.coyjiv.isocial.service.dia;

import com.coyjiv.isocial.dto.request.dia.ValidatePassportRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.naming.ServiceUnavailableException;

public interface IDiaService {

  boolean validatePassport(ValidatePassportRequestDto dto) throws ServiceUnavailableException, JsonProcessingException;

}
