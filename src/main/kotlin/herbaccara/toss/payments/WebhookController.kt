package herbaccara.toss.payments

import herbaccara.boot.autoconfigure.toss.payments.TossPaymentsProperties
import herbaccara.toss.payments.model.webhook.DepositCallback
import herbaccara.toss.payments.model.webhook.Event
import herbaccara.toss.payments.model.webhook.Event.*
import herbaccara.toss.payments.model.webhook.PaymentStatusChanged
import herbaccara.toss.payments.model.webhook.PayoutStatusChanged
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

abstract class WebhookController(
    protected val tossPaymentsService: TossPaymentsService,
    protected val properties: TossPaymentsProperties
) {
    // TODO : 허용 IP 필터링 처리

    /***
     * 가상계좌 결제 상태를 알려주는 웹훅
     * [deposit_callback](https://docs.tosspayments.com/guides/webhook#deposit_callback)
     */
    @RequestMapping("/deposit-callback")
    @ResponseBody
    abstract fun onDepositCallback(depositCallback: DepositCallback)

    /***
     * 카드, 계좌이체, 휴대폰, 상품권 결제 상태를 알려주는 웹훅입니다.
     * [...](https://docs.tosspayments.com/guides/webhook#payment_status_changed)
     */
    @RequestMapping("/payment-status-changed")
    @ResponseBody
    abstract fun onPaymentStatusChanged(paymentStatusChangedEvent: Event<PaymentStatusChanged>)

    /***
     * 지급대행 요청 상태가 COMPLETED, FAILED로 변경되면 웹훅이 전송됩니다. 자세한 상태 설명은 status에서 확인하세요.
     * [...](https://docs.tosspayments.com/guides/webhook#payout_status_changed)
     */
    @RequestMapping("/payout-status-changed")
    @ResponseBody
    abstract fun onPayoutStatusChanged(payoutStatusChangedEvent: Event<PayoutStatusChanged>)

    /***
     * 브랜드페이 고객의 결제 수단이 변경되면 웹훅이 전송됩니다.
     * [...](https://docs.tosspayments.com/guides/webhook#method_updated)
     */
    @RequestMapping("/method-updated")
    @ResponseBody
    abstract fun onMethodUpdated(methodUpdatedEvent: Event<MethodUpdated>)

    /***
     * 브랜드페이 고객의 상태가 변경되면 웹훅이 전송됩니다.
     * [...](https://docs.tosspayments.com/guides/webhook#customer_status_changed)
     */
    @RequestMapping("/customer-status-changed")
    @ResponseBody
    abstract fun onCustomerStatusChanged(customerStatusChangedEvent: Event<CustomerStatusChanged>)
}
