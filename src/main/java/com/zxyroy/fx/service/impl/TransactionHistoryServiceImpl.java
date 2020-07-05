package com.zxyroy.fx.service.impl;

import com.zxyroy.fx.Constant;
import com.zxyroy.fx.domain.DTO.TransactionHistoryDTO;
import com.zxyroy.fx.domain.DTO.TransactionHistoryListDTO;
import com.zxyroy.fx.domain.TransactionHistory;
import com.zxyroy.fx.domain.request.TransactionHistoryRequest;
import com.zxyroy.fx.exception.RateNotMatchException;
import com.zxyroy.fx.repository.TransactionHistoryRepository;
import com.zxyroy.fx.service.TransactionHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionHistoryServiceImpl implements TransactionHistoryService {
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public TransactionHistoryDTO saveTransactionHistory(TransactionHistoryRequest transactionHistoryRequest) {
        log.debug("Records add : {}", transactionHistoryRequest.toString());
        //AmountSell multiple Rate should match AmountBuy. Some accuracy difference is allowance
        BigDecimal difference = transactionHistoryRequest.getAmountSell().multiply(transactionHistoryRequest.getRate()).subtract(transactionHistoryRequest.getAmountBuy()).abs();
        if (difference.doubleValue() > Constant.RATE_ACCURACY_TOLERANCE)
            throw new RateNotMatchException(difference);
        TransactionHistory transactionHistory = new TransactionHistory();
        BeanUtils.copyProperties(transactionHistoryRequest, transactionHistory);
        transactionHistory.setTimePlaced(transactionHistoryRequest.getTimePlacedDateTimeFormat());
        log.debug(transactionHistory.toString());
        transactionHistory = transactionHistoryRepository.save(transactionHistory);
        return convert(transactionHistory);
    }

    @Override
    public TransactionHistoryListDTO getTransactionHistory(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        return new TransactionHistoryListDTO(transactionHistoryRepository.count(), transactionHistoryRepository.findAll(pageable).stream().map(this::convert).collect(Collectors.toList()));
    }

    private TransactionHistoryDTO convert(TransactionHistory transactionHistory) {
        TransactionHistoryDTO transactionHistoryDTO = new TransactionHistoryDTO();
        BeanUtils.copyProperties(transactionHistory, transactionHistoryDTO);
        return transactionHistoryDTO;
    }
}
