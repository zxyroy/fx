package com.zxyroy.fx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxyroy.fx.domain.DTO.TransactionHistoryDTO;
import com.zxyroy.fx.domain.DTO.TransactionHistoryListDTO;
import com.zxyroy.fx.domain.TransactionHistory;
import com.zxyroy.fx.domain.request.TransactionHistoryRequest;
import com.zxyroy.fx.exception.RateNotMatchException;
import com.zxyroy.fx.repository.TransactionHistoryRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionHistoryServiceImplTest {
    @MockBean
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private TransactionHistoryServiceImpl transactionHistoryServiceImpl;

    private TransactionHistoryRequest transactionHistoryRequestValid;
    private TransactionHistoryRequest transactionHistoryRequestInvalidRate;

    @SneakyThrows
    @PostConstruct
    private void init() {
        ObjectMapper mapper = new ObjectMapper();
        URL transactionHistoryRequestValidJson = TransactionHistoryServiceImplTest.class.getClassLoader().getResource("data/transactionRequest.json");
        transactionHistoryRequestValid = mapper.readValue(Path.of(transactionHistoryRequestValidJson.toURI()).toFile(), TransactionHistoryRequest.class);
        URL transactionHistoryRequestInValidRateJson = TransactionHistoryServiceImplTest.class.getClassLoader().getResource("data/transactionRequestInvalidRate.json");
        transactionHistoryRequestInvalidRate = mapper.readValue(Path.of(transactionHistoryRequestInValidRateJson.toURI()).toFile(), TransactionHistoryRequest.class);
        TransactionHistory transactionHistory = new TransactionHistory();
        BeanUtils.copyProperties(transactionHistoryRequestValid, transactionHistory);
        transactionHistory.setId(1L);
        when(transactionHistoryRepository.save(any())).thenReturn(transactionHistory);
        List<TransactionHistory> list = new ArrayList<>();
        list.add(transactionHistory);
        when(transactionHistoryRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl<>(list));
        when(transactionHistoryRepository.count()).thenReturn(1L);
    }

    @Test
    void saveTransactionHistory() {
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryServiceImpl.saveTransactionHistory(transactionHistoryRequestValid);
        Assertions.assertEquals(1L, transactionHistoryDTO.getId());
    }

    @Test
    void saveTransactionHistoryInvalidRate() {
        Assertions.assertThrows(RateNotMatchException.class, () -> transactionHistoryServiceImpl.saveTransactionHistory(transactionHistoryRequestInvalidRate));
    }

    @Test
    void getTransactionHistory() {
        TransactionHistoryListDTO transactionHistoryListDTO = transactionHistoryServiceImpl.getTransactionHistory(1, 1);
        Assertions.assertEquals(1, transactionHistoryListDTO.getTransactionHistoryList().size());
        Assertions.assertEquals(1L, transactionHistoryListDTO.getCount());
    }
}