package org.llam.app.candlechart.api;

import java.time.Duration;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class StreamApi {

  record StockData(String date, double open, double close, double highest, double lowest, int volume) {
  }

  @RequestMapping(value = "/stream", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<?> something() {

    var webClient = WebClient.create("https://echarts.apache.org/examples/data/asset/data/stock-DJI.json");

    return webClient
        .get()
        .retrieve()
        .bodyToMono(List.class)
        .flatMapMany(t -> Flux.fromIterable(t))
        .delayElements(Duration.ofSeconds(1))
        .onErrorComplete()
        .retry(5);
  }

  @RequestMapping(value = "/data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<?> all() {

    var webClient = WebClient.create("https://echarts.apache.org/examples/data/asset/data/stock-DJI.json");

    return webClient
        .get()
        .retrieve()
        .bodyToMono(List.class);
  }

}
