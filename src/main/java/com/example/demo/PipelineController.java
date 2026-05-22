package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PipelineController {

    @Value("${github.token}")
    private String token;

    @GetMapping("/test")
    public String test() {
        return "Controller Working";
    }

    @GetMapping("/triggerPipeline")
    public String triggerPipeline() {

        String url =
                "https://api.github.com/repos/ptlsasindran/GitTestPipeRepo/actions/workflows/pipeline.yml/dispatches";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
                {
                  "ref":"main"
                }
                """;

        HttpEntity<String> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        String.class
                );

        return "Pipeline Triggered Successfully : "
                + response.getStatusCode();
    }
}