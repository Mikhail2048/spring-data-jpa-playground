package io.mpolivakha.model.view_mapping;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Immutable;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "active_orders")
@Immutable
public class ActiveOrders {

  @Id
  private Long id;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "due_to")
  private OffsetDateTime dueTo;

  @ManyToOne(fetch = FetchType.EAGER)
  private User user;
}
