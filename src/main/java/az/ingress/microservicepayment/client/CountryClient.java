package az.ingress.microservicepayment.client;

import az.ingress.microservicepayment.model.country.CountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "micro-service-country", url = "${client.micro-service-country}")
public interface CountryClient {

    @GetMapping("/api/countries")
    List<CountryDto> getAllAvailable(@RequestParam String currency);
}
