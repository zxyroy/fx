package com.zxyroy.fx.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionHistoryListDTO {
    private long count;
    private List<TransactionHistoryDTO> transactionHistoryList;
}
