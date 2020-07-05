package com.zxyroy.fx.domain.request;

import com.zxyroy.fx.Constant;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Getter
@ToString
public class TransactionHistoryRequest {
    private final static DateTimeFormatter df = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(Constant.DATETIME_FORMAT).toFormatter().withLocale(Locale.ENGLISH);

    @Min(1)
    private long userId;
    @NotNull
    private String currencyFrom;
    @NotNull
    private String currencyTo;
    @DecimalMin("0.0001")
    private BigDecimal amountSell;
    @DecimalMin("0.0001")
    private BigDecimal amountBuy;
    @DecimalMin("0.0001")
    private BigDecimal rate;
    private LocalDateTime timePlaced;
    @NotNull
    @Size(min = 2, max = 2)
    private String originatingCountry;

    public void setTimePlaced(String timePlaced) {
        this.timePlaced = LocalDateTime.parse(timePlaced, df);
    }

    public String getTimePlaced() {
        return df.format(this.timePlaced).toUpperCase();
    }

    public LocalDateTime getTimePlacedDateTimeFormat() {
        return this.timePlaced;
    }
}
