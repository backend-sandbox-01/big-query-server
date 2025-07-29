package dev.bigquery.big_query_server.repository;

import dev.bigquery.big_query_server.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("SELECT c FROM Content c WHERE c.id <= :maxId AND MOD(c.id, 2) = 1")
    List<Content> findOddUnderId(@Param("maxId") Long maxId);

    @Query("SELECT c FROM Content c WHERE c.id <= :maxId AND MOD(c.id, 2) = 1")
    Stream<Content> streamOddUnderId(@Param("maxId") Long maxId);
}