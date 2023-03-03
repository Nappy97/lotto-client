package com.example.lottoclient.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LottoNumber {

    private List<Integer> fixedNumbers;
    private List<Integer> combinationNumbers;
    private LottoResultDTO result;

    public LottoNumber(List<Integer> fixedNumbers, List<Integer> combinationNumbers) {
        this.fixedNumbers = fixedNumbers;
        this.combinationNumbers = combinationNumbers;
    }

}

