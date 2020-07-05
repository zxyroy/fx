package com.zxyroy.fx.controller;

import com.zxyroy.fx.domain.DTO.TransactionHistoryDTO;
import com.zxyroy.fx.domain.DTO.TransactionHistoryListDTO;
import com.zxyroy.fx.domain.request.TransactionHistoryRequest;
import com.zxyroy.fx.service.TransactionHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping(value = "/api/transaction/history")
@Validated
@Slf4j
public class TransactionHistoryController {
    @Autowired
    TransactionHistoryService transactionHistoryService;

    @GetMapping(value = "")
    public Mono<TransactionHistoryListDTO> getTransactionHistory(@RequestParam("page") @Min(0) int page, @RequestParam("size") @Min(1) @Max(1000) int size) {
        return Mono.just(transactionHistoryService.getTransactionHistory(page, size));
    }

    @PostMapping(value = "")
    public Mono<TransactionHistoryDTO> saveTransactionHistory(@RequestBody @Valid TransactionHistoryRequest transactionHistoryRequest) {
        return Mono.just(transactionHistoryService.saveTransactionHistory(transactionHistoryRequest));
    }
}
