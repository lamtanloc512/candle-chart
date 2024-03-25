package org.llam.app.candlechart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ApiService {

  private final WebClient webClient;

  public ApiService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder
        .baseUrl("https://echarts.apache.org/examples/data/asset/data/stock-DJI.json")
        .build();
  }

  public Mono<?> callApi() {
    return webClient.get()
        .retrieve()
        .bodyToMono(List.class);
  }

}
