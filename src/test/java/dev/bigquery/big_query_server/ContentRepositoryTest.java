package dev.bigquery.big_query_server.repository;

import dev.bigquery.big_query_server.domain.Content;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback
class ContentRepositoryTest {
  
  @Autowired
  private ContentRepository contentRepository;

  @Test
  void saveAndFindContent() {
    Content content = new Content();
    content.setName("테스트 콘텐츠");

    contentRepository.save(content);

    Optional<Content> result = contentRepository.findById(content.getId());

    assertTrue(result.isPresent());
    assertEquals("테스트 콘텐츠", result.get().getName());
  }
}