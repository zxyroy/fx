package com.zxyroy.fx.service;

import com.zxyroy.fx.domain.DTO.TransactionHistoryDTO;
import com.zxyroy.fx.domain.DTO.TransactionHistoryListDTO;
import com.zxyroy.fx.domain.request.TransactionHistoryRequest;

public interface TransactionHistoryService {
    TransactionHistoryDTO saveTransactionHistory(TransactionHistoryRequest transactionHistoryRequest);

    TransactionHistoryListDTO getTransactionHistory(int page, int size);
}
