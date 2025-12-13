package az.ingress.microservicepayment.service;

import az.ingress.microservicepayment.client.CountryClient;
import az.ingress.microservicepayment.entity.Payment;
import az.ingress.microservicepayment.exception.NotFoundException;
import az.ingress.microservicepayment.mapper.PaymentMapper;
import az.ingress.microservicepayment.model.request.PaymentRequest;
import az.ingress.microservicepayment.model.response.PaymentResponse;
import az.ingress.microservicepayment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static az.ingress.microservicepayment.constant.ExceptionConstants.COUNTRY_NOT_FOUND_CODE;
import static az.ingress.microservicepayment.constant.ExceptionConstants.COUNTRY_NOT_FOUND_MESSAGE;
import static az.ingress.microservicepayment.mapper.PaymentMapper.mapEntityToResponse;
import static az.ingress.microservicepayment.mapper.PaymentMapper.mapRequestToEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final CountryClient countryClient;

    public void save(PaymentRequest request) {
        log.info("savePayment.started");
        countryClient.getAllAvailable(request.getCurrency())
                .stream()
                .filter(country -> country.getRemainingLimit().compareTo(request.getAmount()) > 0)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format(COUNTRY_NOT_FOUND_MESSAGE, request.getAmount(),
                        request.getCurrency()), COUNTRY_NOT_FOUND_CODE));


        paymentRepository.save(mapRequestToEntity(request));
        log.info("savePayment.success");
    }


    public List<PaymentResponse> getAllPayments() {
        log.info("getAllPayments.started");
        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::mapEntityToResponse).collect(Collectors.toList());
    }

    public void updatePayment(Long id, PaymentRequest request) {
        log.info("updatePayment.started id:{}", id);
        Payment payment = fetchPaymentIfExist(id);
        payment.setAmount(request.getAmount());
        payment.setDescription(request.getDescription());
        paymentRepository.save(payment);
    }


    public void deletePayment(Long id) {
        log.info("deletePayment.started id:{}", id);
        paymentRepository.deleteById(id);
        log.info("deletePayment.success: {}", id);
    }


    public PaymentResponse getPaymentById(Long id) {
        log.info("getPayment.start id: {}", id);
        Payment payment = fetchPaymentIfExist(id);
        log.info("getPayment.success id:{}", id);
        return mapEntityToResponse(payment);
    }

    private Payment fetchPaymentIfExist(Long id) {
        return paymentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
