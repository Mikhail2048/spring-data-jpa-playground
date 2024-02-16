package io.mpolivakha.repository;

import io.mpolivakha.model.ClassProjection;
import io.mpolivakha.model.InterfaceProjection;
import io.mpolivakha.model.Invoice;
import io.mpolivakha.model.RecordProjection;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
class InvoiceRepositoryTest {

  private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
      DockerImageName.parse("postgres:15.2")
  );

  @DynamicPropertySource
  static void register(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
  }

  @BeforeAll
  static void beforeAll() {
    postgreSQLContainer.start();
  }

  @AfterAll
  static void afterAll() {
    postgreSQLContainer.stop();
  }

  @Autowired
  private InvoiceRepository invoiceRepository;

  @Test
  void testInterfaceProjections() {

    Invoice usd = new Invoice()
        .setAmount(BigDecimal.valueOf(123))
        .setCurrency("USD")
        .setUserId(144L);

    Invoice save = invoiceRepository.save(usd);

    InterfaceProjection invoiceById = invoiceRepository.findInvoiceById(save.getId());

    Assertions.assertThat(invoiceById.getAmount()).isEqualTo(BigDecimal.valueOf(123));
    Assertions.assertThat(invoiceById.getCurrency()).isEqualTo("USD");
  }

  @Test
  @Disabled("Class projections seems to be broken") //TODO fix
  void testClassProjections() {

    Invoice usd = new Invoice()
        .setAmount(BigDecimal.valueOf(123))
        .setCurrency("USD")
        .setUserId(144L);

    Invoice save = invoiceRepository.save(usd);

    ClassProjection invoiceById = invoiceRepository.findInvoicePleaseById(save.getId());

    Assertions.assertThat(invoiceById.getAmount()).isEqualTo(BigDecimal.valueOf(123));
    Assertions.assertThat(invoiceById.getCurrency()).isEqualTo("USD");
  }

  @Test
  void testRecordProjections() {

    Invoice usd = new Invoice()
        .setAmount(BigDecimal.valueOf(123))
        .setCurrency("USD")
        .setUserId(144L);

    Invoice save = invoiceRepository.save(usd);

    RecordProjection invoiceById = invoiceRepository.findSomethingById(save.getId());

    Assertions.assertThat(invoiceById.amount()).isEqualTo(BigDecimal.valueOf(123));
    Assertions.assertThat(invoiceById.currency()).isEqualTo("USD");
  }

  @Test
  void testDynamicProjections() {

    invoiceRepository.saveAll(
        Set.of(
            new Invoice()
                .setAmount(BigDecimal.valueOf(100))
                .setCurrency("USD")
                .setUserId(144L),
            new Invoice()
                .setAmount(BigDecimal.valueOf(150))
                .setCurrency("USD")
                .setUserId(122L),
            new Invoice()
                .setAmount(BigDecimal.valueOf(200))
                .setCurrency("TRY")
                .setUserId(144L)
        )
    );

    Collection<Invoice> invoices = invoiceRepository.findByUserId(144L, Invoice.class);
    Collection<RecordProjection> recordProjections = invoiceRepository.findByUserId(144L, RecordProjection.class);
    Collection<InterfaceProjection> interfaceProjections = invoiceRepository.findByUserId(144L, InterfaceProjection.class);

    Assertions.assertThat(invoices).hasSize(2);
    Assertions.assertThat(recordProjections).hasSize(2);
    Assertions.assertThat(interfaceProjections).hasSize(2);
  }
}