package com.coyjiv.isocial.service.dia;

import com.coyjiv.isocial.cache.DiaCache;
import com.coyjiv.isocial.dto.request.dia.ValidatePassportRequestDto;
import com.coyjiv.isocial.dto.respone.dia.DiaSessionTokenResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.naming.ServiceUnavailableException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DiaService implements IDiaService {

  @Value("${DIA_API_URL}")
  private String apiUrl;

  @Value("${DIA_ACQUIRER_TOKEN}")
  private String acquirerToken;

  @Value("${DIA_AUTH_ACQUIRER_TOKEN}")
  private String authAcquirerToken;

  @Value("${DIA_BRANCH_ID}")
  private String branchId;

  private final RestTemplate restTemplate;

  @Override
  @Transactional
  public boolean validatePassport(ValidatePassportRequestDto dto) throws ServiceUnavailableException {
    String sessionToken = DiaCache.getToken();
    if (sessionToken.isEmpty()) {
      throw new ServiceUnavailableException("service unavailable");
    }

    String url = String.format("%s/v1/acquirers/document-identification", apiUrl);
    HttpHeaders headers = new HttpHeaders();

    headers.add("Authorization", "Bearer " + sessionToken);
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    JSONObject body = new JSONObject();
    body.put("branchId", branchId);
    body.put("barcode", dto.getBarcode());
    JSONObject person = new JSONObject();
    person.put("firstName", dto.getFirstName());
    person.put("lastName", dto.getLastName());
    person.put("middleName", dto.getMiddleName());
    body.put("person", person);

    HttpEntity<String> requestEntity = new HttpEntity<>(body.toString(), headers);

    try {
      ResponseEntity<String> request = restTemplate.postForEntity(url, requestEntity, String.class);


      JSONObject response = new JSONObject(request.getBody());

      return response.getBoolean("success");
    } catch (Exception e) {
      throw  new ServiceUnavailableException(parseErrorMessage(e.getMessage()));
    }
  }


  @Scheduled(fixedDelay = 119, timeUnit = TimeUnit.MINUTES)
  protected void initSessionToken() {
    String url = String.format("%s/v1/auth/acquirer/%s", apiUrl, acquirerToken);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + authAcquirerToken);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<DiaSessionTokenResponseDto> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            DiaSessionTokenResponseDto.class
    );

    int counter = 0;
    while (!response.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
      if (counter > 3) {
        break;
      }
      counter++;
      initSessionToken();
    }

    String token = response.getBody() != null ? response.getBody().getToken() : "";

    DiaCache.putSessionToken(token);
  }


  private String parseErrorMessage(String errorMessage) {
    String jsonString = errorMessage.replace("400 Bad Request: ","")
            .replace("<EOL>","")
            .replaceFirst("^\"\\{", "{")
            .replaceFirst("\\}\"$", "}")
            .replace("\\\"", "\"");;


    JSONObject jsonObject = new JSONObject(jsonString);
    return jsonObject.getString("message");
  }

}
