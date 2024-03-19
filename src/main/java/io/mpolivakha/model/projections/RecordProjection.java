package io.mpolivakha.model.projections;

import java.math.BigDecimal;

public record RecordProjection(
    String currency,
    BigDecimal amount
) { }
