package az.ingress.microservicepayment.mapper;

import az.ingress.microservicepayment.entity.Payment;
import az.ingress.microservicepayment.model.request.PaymentRequest;
;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


class PaymentMapperTest {

    @Test
    void toEntityTest() {


        // Arrange
        var request = new PaymentRequest();
        request.setDescription("description");
        request.setAmount(BigDecimal.TEN);


        var expected = new Payment();
        expected.setDescription("description");
        expected.setAmount(BigDecimal.TEN);


        //actual
        var actual = PaymentMapper.mapRequestToEntity(request);


        Assertions.assertThat(actual).isEqualTo(expected);

    }

}