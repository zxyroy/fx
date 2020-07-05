package com.zxyroy.fx.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionHistoryDTO {
    private long id;
    private long userId;
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal amountSell;
    private BigDecimal amountBuy;
    private BigDecimal rate;
    private LocalDateTime timePlaced;
    private String originatingCountry;
}
