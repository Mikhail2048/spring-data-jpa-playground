package io.mpolivakha.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ClassProjection {

  private BigDecimal amount;
  private String currency;
}
