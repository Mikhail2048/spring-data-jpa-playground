package io.mpolivakha.model;

import java.math.BigDecimal;

public record RecordProjection(
    String currency,
    BigDecimal amount
) { }
