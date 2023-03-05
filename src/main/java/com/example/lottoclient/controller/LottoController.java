package com.example.lottoclient.controller;

import com.example.lottoclient.dto.LottoResultDTO;
import com.example.lottoclient.service.LottoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
        List<Integer> vNum = new ArrayList<>(combinationNumbers);
        List<Integer> duplicationCheck = allNumbers.stream().filter(o -> vNum.stream()
                .anyMatch(Predicate.isEqual(o))).collect(Collectors.toList());

        if (duplicationCheck.size() != 0 || overNumbersCheck(allNumbers) == false || overNumbersCheck(vNum) == false || combinationNumbers.size() >= 39) {
            if (duplicationCheck.size() != 0) {
                model.addAttribute("errorMessage1", "중복된 숫자가 있습니다.");
            }
            if (overNumbersCheck(allNumbers) == false || overNumbersCheck(vNum) == false) {
                model.addAttribute("errorMessage2", "1부터 45 사이의 숫자를 입력해주세요.");
            }
            if (combinationNumbers.size() >= 39) {
                model.addAttribute("errorMessage3", "조합숫자는 39개 이상의 숫자는 입력할 수 없습니다.");
            }
            return "/index";
        }
        List<List<Integer>> numbers = generateCombinations(allNumbers, vNum);
        List<LottoResultDTO> result = service.getLottoResult(numbers);
        model.addAttribute("result", result);
        return "result";
    }

    private List<Integer> pickRandomNumbers(List<Integer> numbers, int count) {
        Random random = new Random();
        List<Integer> result = new ArrayList<>();
        List<Integer> remaining = new ArrayList<>(numbers);
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(remaining.size());
            result.add(remaining.get(index));
            remaining.remove(index);
        }
        return result;
    }


    private List<List<Integer>> generateCombinations(List<Integer> fixedNumbers, List<Integer> combinationNumbers) {
        List<List<Integer>> result = new ArrayList<>();
        for (Integer fixedNumber : fixedNumbers) {
            List<Integer> vnum = pickRandomNumbers(combinationNumbers, 5);
            List<Integer> numbers = new ArrayList<>(vnum);
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
