package az.ingress.microservicepayment.mapper;

import az.ingress.microservicepayment.entity.Payment;
import az.ingress.microservicepayment.model.request.PaymentRequest;
import az.ingress.microservicepayment.model.response.PaymentResponse;

import java.time.LocalDateTime;

public interface PaymentMapper {

    static Payment mapRequestToEntity(PaymentRequest request) {
        return Payment.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .build();
    }

    public static PaymentResponse mapEntityToResponse(Payment entity) {
        return PaymentResponse.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .responseAt(LocalDateTime.now())
                .build();
    }
}
