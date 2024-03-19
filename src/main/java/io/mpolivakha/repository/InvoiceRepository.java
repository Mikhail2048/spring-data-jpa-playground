package io.mpolivakha.repository;

import io.mpolivakha.model.projections.ClassProjection;
import io.mpolivakha.model.projections.InterfaceProjection;
import io.mpolivakha.model.projections.Invoice;
import io.mpolivakha.model.projections.RecordProjection;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  InterfaceProjection findInvoiceById(Long id);

  ClassProjection findInvoicePleaseById(Long id);

  RecordProjection findSomethingById(Long id);

  <T> Collection<T> findByUserId(Long userId, Class<T> projectionType);
}
