package com.zxyroy.fx.domain.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.time.format.DateTimeParseException;

class TransactionHistoryRequestTest {

    @Test
    void setTimePlaced() {
        TransactionHistoryRequest transactionHistoryRequest = new TransactionHistoryRequest();
        transactionHistoryRequest.setTimePlaced("24-JAN-18 10:27:44");
        Assertions.assertEquals(24, transactionHistoryRequest.getTimePlacedDateTimeFormat().getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, transactionHistoryRequest.getTimePlacedDateTimeFormat().getMonth());
        Assertions.assertEquals(2018, transactionHistoryRequest.getTimePlacedDateTimeFormat().getYear());
        Assertions.assertEquals(10, transactionHistoryRequest.getTimePlacedDateTimeFormat().getHour());
        Assertions.assertEquals(27, transactionHistoryRequest.getTimePlacedDateTimeFormat().getMinute());
        Assertions.assertEquals(44, transactionHistoryRequest.getTimePlacedDateTimeFormat().getSecond());
    }

    @Test
    void setTimePlacedFail() {
        TransactionHistoryRequest transactionHistoryRequest = new TransactionHistoryRequest();
        Assertions.assertThrows(DateTimeParseException.class, () -> transactionHistoryRequest.setTimePlaced("24-JAN-2018 10:27:44"));
    }
}