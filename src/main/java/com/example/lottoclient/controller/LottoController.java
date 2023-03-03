package com.example.lottoclient.controller;

import com.example.lottoclient.dto.LottoResultDTO;
import com.example.lottoclient.service.LottoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class LottoController {

    private final LottoService service;

    public LottoController(LottoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @PostMapping("/result")
    public String getLottoResult(@RequestParam("fixedNumbers[]") List<Integer> fixedNumbers, @RequestParam("combinationNumbers[]") List<Integer> combinationNumbers, Model model) {
        List<Integer> allNumbers = new ArrayList<>(fixedNumbers);
        List<Integer> vNum = pickRandomNumbers(combinationNumbers, 5);
        List<Integer> duplicationCheck = allNumbers.stream().filter(o -> vNum.stream()
                .anyMatch(Predicate.isEqual(o))).collect(Collectors.toList());

        if (duplicationCheck.size() != 0 || overNumbersCheck(allNumbers) == false || overNumbersCheck(vNum) == false || combinationNumbers.size() >= 39) {
            return "redirect:";
        }
        List<List<Integer>> numbers = generateCombinations(allNumbers, vNum);
        List<LottoResultDTO> result = service.getLottoResult(numbers);
        model.addAttribute("result", result);
        return "result";
    }

    private List<Integer> pickRandomNumbers(List<Integer> numbers, int count) {
        Random random = new Random();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(numbers.size());
            result.add(numbers.get(index));
            numbers.remove(index);
        }
        return result;
    }


    private List<List<Integer>> generateCombinations(List<Integer> fixedNumbers, List<Integer> combinationNumbers) {
        List<List<Integer>> result = new ArrayList<>();
        for (Integer fixedNumber : fixedNumbers) {
            List<Integer> numbers = new ArrayList<>(combinationNumbers);
            numbers.add(fixedNumber);
            Collections.sort(numbers);
            result.add(numbers);
        }
        return result;
    }

    private boolean overNumbersCheck(List<Integer> numbers) {
        int max = Collections.max(numbers);
        if (max > 45) {
            return false;
        } else {
            return true;
        }
    }


}
