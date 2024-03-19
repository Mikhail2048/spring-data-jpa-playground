package io.mpolivakha.model.projections;

import java.math.BigDecimal;

public interface InterfaceProjection {

  String getCurrency();

  BigDecimal getAmount();
}
