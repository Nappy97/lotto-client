package com.example.lottoclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LottoResultDTO {
    private int turn;
    private int ranking;
    private List<Integer> matchedNumbers;
    private List<Integer> unmatchedNumbers;
}
