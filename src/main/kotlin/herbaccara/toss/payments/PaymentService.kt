package herbaccara.toss.payments

import herbaccara.toss.payments.form.payment.*
import herbaccara.toss.payments.model.payment.Payment
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForObject

class PaymentService(
    private val restTemplate: RestTemplate
) {

    /***
     * 결제 생성
     * [...](https://docs.tosspayments.com/reference#%EA%B2%B0%EC%A0%9C-%EC%83%9D%EC%84%B1)
     *
     * @param form
     * @return
     */
    fun create(form: PaymentCreateForm): Payment {
        val uri = "/v1/payments"
        return restTemplate.postForObject(uri, form)
    }

    /***
     * 결제 승인
     * [...](https://docs.tosspayments.com/reference#%EA%B2%B0%EC%A0%9C-%EC%8A%B9%EC%9D%B8)
     *
     * @param form
     * @return
     */
    fun confirm(form: PaymentConfirmForm): Payment {
        val uri = "/v1/payments/confirm"
        return restTemplate.postForObject(uri, form)
    }

    /***
     * 결제 조회
     * [...](https://docs.tosspayments.com/reference#paymentkey%EB%A1%9C-%EA%B2%B0%EC%A0%9C-%EC%A1%B0%ED%9A%8C)
     *
     * @param paymentKey 결제의 키
     * @return
     */
    fun paymentByPaymentKey(paymentKey: String): Payment {
        val uri = "/v1/payments/$paymentKey"
        return restTemplate.getForObject(uri)
    }

    /***
     * 결제 조회
     * [...](https://docs.tosspayments.com/reference#orderid%EB%A1%9C-%EA%B2%B0%EC%A0%9C-%EC%A1%B0%ED%9A%8C)
     *
     * @param orderId 주문 ID
     * @return
     */
    fun paymentByOrderId(orderId: String): Payment {
        val uri = "/v1/payments/orders/$orderId"
        return restTemplate.getForObject(uri)
    }

    /**
     * 결제 취소
     * [...](https://docs.tosspayments.com/reference#%EA%B2%B0%EC%A0%9C-%EC%B7%A8%EC%86%8C)
     *
     *
     * 주의. 결제 수단이 "가상계좌", 결제 상태가 "완료" 일 때는 반드시 refundReceiveAccount(환불 받을 고객의 계좌) 정보가 있어야 한다.
     *
     * @param paymentKey
     * @param form
     * @return
     */
    fun cancel(paymentKey: String, form: PaymentCancelForm): Payment {
        val uri = "/v1/payments/$paymentKey/cancel"
        return restTemplate.postForObject(uri, form)
    }

    /***
     * 카드 번호 결제
     * [...](https://docs.tosspayments.com/reference#%EC%B9%B4%EB%93%9C-%EB%B2%88%ED%98%B8-%EA%B2%B0%EC%A0%9C)
     *
     * @param form
     * @return
     */
    fun keyIn(form: PaymentKeyInForm): Payment {
        val uri = "/v1/payments/key-in"
        return restTemplate.postForObject(uri, form)
    }

    /***
     * 가상계좌 발급 요청
     * [...](https://docs.tosspayments.com/reference#%EA%B0%80%EC%83%81%EA%B3%84%EC%A2%8C-%EB%B0%9C%EA%B8%89-%EC%9A%94%EC%B2%AD)
     *
     * @param form
     * @return
     */
    fun virtualAccount(form: PaymentVirtualAccountForm): Payment {
        val uri = "/v1/virtual-accounts"
        return restTemplate.postForObject(uri, form)
    }
}
