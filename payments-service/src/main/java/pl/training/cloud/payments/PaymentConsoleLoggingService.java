package pl.training.cloud.payments;

import lombok.extern.java.Log;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Service;

@Aspect
@Service
@Log
public class PaymentConsoleLoggingService {

    @Pointcut("execution(* pl.training.cloud.payments.PaymentsService.process(..))")
    public void processPayment() {
    }

    @Before("processPayment() && args(paymentRequest)")
    public void beforePayment(PaymentRequest paymentRequest) {
        log.info("New payment request: " + paymentRequest);
    }

    @After("processPayment()")
    public void afterPayment() {
        log.info("Payment processed");
    }

    @AfterReturning(value = "processPayment()", returning = "payment")
    public void afterPayment(Payment payment) {
        log.info("Payment created: " + payment);
    }

    @AfterThrowing(value = "processPayment()", throwing = "exception")
    public void afterPayment(RuntimeException exception) {
        log.info("Payment processing failed: " + exception);
    }

}
