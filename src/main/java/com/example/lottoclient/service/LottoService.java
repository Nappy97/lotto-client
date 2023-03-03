package com.example.lottoclient.service;

import com.example.lottoclient.dto.LottoResultDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class LottoService {

    public List<LottoResultDTO> getLottoResult(List<List<Integer>> numbers) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/result";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<LottoResultDTO> results = new ArrayList<>();
        for (List<Integer> number : numbers) {
            HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(number, headers);
            ResponseEntity<LottoResultDTO> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, LottoResultDTO.class);
            results.add(responseEntity.getBody());
        }

        return results;
    }

}
