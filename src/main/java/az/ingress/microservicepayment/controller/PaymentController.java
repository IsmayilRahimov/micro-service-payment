package az.ingress.microservicepayment.controller;

import az.ingress.microservicepayment.model.request.PaymentRequest;
import az.ingress.microservicepayment.model.response.PaymentResponse;
import az.ingress.microservicepayment.service.PaymentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/getAll")
    public List<PaymentResponse> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @PostMapping("/create")
    @ResponseStatus(CREATED)
    public void createPayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.save(paymentRequest);
    }

    @GetMapping("/getById/{id}")

    public PaymentResponse getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @PutMapping("/update/{id}")
    public void updatePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest) {
        paymentService.updatePayment(id, paymentRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }

}
