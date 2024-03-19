package io.mpolivakha.repository;

import io.mpolivakha.model.view_mapping.ActiveOrders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceProperty;
import java.time.OffsetDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class EntityToViewTest extends AbstractIntegrationTest {

  @PersistenceContext(properties = {
      @PersistenceProperty(name = "show_sql", value = "true")
  })
  private EntityManager entityManager;

  @Test
  void testTableViews() {
    List<ActiveOrders> resultList = entityManager.createQuery("SELECT a FROM ActiveOrders a", ActiveOrders.class).getResultList();

    System.out.println(resultList);

    Assertions.assertThat(resultList).hasSize(1);
  }

  @Test
  @Transactional
  void testInsertingIntoView() {
    entityManager.persist(new ActiveOrders().setType("SOME_TYPE").setDueTo(OffsetDateTime.now()).setId(22L));
    entityManager.flush();
  }
}
