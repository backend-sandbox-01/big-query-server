package dev.bigquery.big_query_server.controller;

import dev.bigquery.big_query_server.domain.Content;
import dev.bigquery.big_query_server.repository.ContentRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

    @Autowired
    private ContentRepository contentRepository;

    // 1. 일반 JSON 리스트 조회
    @GetMapping("/odd")
    public List<Content> getOddIdContents() {
        return contentRepository.findOddUnderId(1000000L);
    }

    // 2. NDJSON 스트리밍 응답
    @GetMapping(value = "/odd/stream", produces = "application/x-ndjson")
    @Transactional(readOnly = true)
    public void streamOddContents(HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();

        response.setCharacterEncoding("UTF-8");

        try (Stream<Content> stream = contentRepository.streamOddUnderId(1000000L);
             PrintWriter writer = response.getWriter()) {

            stream.forEach(content -> {
                writer.println("{\"id\":" + content.getId() + ",\"name\":\"" + content.getName() + "\"}");
            });

            long duration = System.currentTimeMillis() - start;
            writer.println("{\"processed_in_ms\":" + duration + "}");
        }
    }

    // 3. CSV 다운로드 응답
    @GetMapping(value = "/odd/csv", produces = "text/csv")
    @Transactional(readOnly = true)
    public void downloadOddContentsAsCsv(HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=odd_contents.csv");

        try (PrintWriter writer = response.getWriter();
             Stream<Content> stream = contentRepository.streamOddUnderId(1000000L)) {

            writer.println("id,name");

            stream.forEach(content -> {
                writer.println(content.getId() + "," + content.getName());
            });

            long duration = System.currentTimeMillis() - start;
            writer.println("# processed_in_ms=" + duration);
        }
    }
}
