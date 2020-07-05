package com.zxyroy.fx.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class RateNotMatchException extends RuntimeException {
    private final BigDecimal difference;
}
