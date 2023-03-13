package herbaccara.toss.payments

import herbaccara.toss.payments.TossPaymentsWebhookController.Companion.DEFAULT_WEBHOOK_PATH
import herbaccara.toss.payments.model.webhook.DepositCallback
import herbaccara.toss.payments.model.webhook.Event
import herbaccara.toss.payments.model.webhook.Event.*
import herbaccara.toss.payments.model.webhook.PaymentStatusChanged
import herbaccara.toss.payments.model.webhook.PayoutStatusChanged
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${toss.payments.webhook.path:$DEFAULT_WEBHOOK_PATH}")
class TossPaymentsWebhookController(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    companion object {
        const val DEFAULT_WEBHOOK_PATH = "/toss/payments/webhook"
        val DEFAULT_ALLOW_IPS = listOf("13.124.18.147", "13.124.108.35", "3.36.173.151", "3.38.81.32")
    }
    // TODO : 허용 IP 필터링 처리

    /***
     * 가상계좌 결제 상태를 알려주는 웹훅
     * [deposit_callback](https://docs.tosspayments.com/guides/webhook#deposit_callback)
     */
    @RequestMapping("/deposit-callback")
    fun onDepositCallback(@RequestBody depositCallback: DepositCallback) {
        applicationEventPublisher.publishEvent(depositCallback)
    }

    /***
     * 카드, 계좌이체, 휴대폰, 상품권 결제 상태를 알려주는 웹훅입니다.
     * [...](https://docs.tosspayments.com/guides/webhook#payment_status_changed)
     */
    @RequestMapping("/payment-status-changed")
    fun onPaymentStatusChanged(@RequestBody paymentStatusChangedEvent: Event<PaymentStatusChanged>) {
        applicationEventPublisher.publishEvent(paymentStatusChangedEvent)
    }

    /***
     * 지급대행 요청 상태가 COMPLETED, FAILED로 변경되면 웹훅이 전송됩니다. 자세한 상태 설명은 status에서 확인하세요.
     * [...](https://docs.tosspayments.com/guides/webhook#payout_status_changed)
     */
    @RequestMapping("/payout-status-changed")
    fun onPayoutStatusChanged(@RequestBody payoutStatusChangedEvent: Event<PayoutStatusChanged>) {
        applicationEventPublisher.publishEvent(payoutStatusChangedEvent)
    }

    /***
     * 브랜드페이 고객의 결제 수단이 변경되면 웹훅이 전송됩니다.
     * [...](https://docs.tosspayments.com/guides/webhook#method_updated)
     */
    @RequestMapping("/method-updated")
    fun onMethodUpdated(@RequestBody methodUpdatedEvent: Event<MethodUpdated>) {
        applicationEventPublisher.publishEvent(methodUpdatedEvent)
    }

    /***
     * 브랜드페이 고객의 상태가 변경되면 웹훅이 전송됩니다.
     * [...](https://docs.tosspayments.com/guides/webhook#customer_status_changed)
     */
    @RequestMapping("/customer-status-changed")
    fun onCustomerStatusChanged(@RequestBody customerStatusChangedEvent: Event<CustomerStatusChanged>) {
        applicationEventPublisher.publishEvent(customerStatusChangedEvent)
    }
}
