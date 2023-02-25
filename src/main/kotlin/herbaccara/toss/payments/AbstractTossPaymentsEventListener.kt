package herbaccara.toss.payments

import herbaccara.toss.payments.model.webhook.DepositCallback
import herbaccara.toss.payments.model.webhook.Event
import herbaccara.toss.payments.model.webhook.Event.CustomerStatusChanged
import herbaccara.toss.payments.model.webhook.Event.MethodUpdated
import herbaccara.toss.payments.model.webhook.PaymentStatusChanged
import herbaccara.toss.payments.model.webhook.PayoutStatusChanged
import org.springframework.context.event.EventListener

abstract class AbstractTossPaymentsEventListener {

    @EventListener
    abstract fun onDepositCallback(depositCallback: DepositCallback)

    @EventListener
    abstract fun onPaymentStatusChanged(paymentStatusChangedEvent: Event<PaymentStatusChanged>)

    @EventListener
    abstract fun onPayoutStatusChanged(payoutStatusChangedEvent: Event<PayoutStatusChanged>)

    @EventListener
    abstract fun onMethodUpdated(methodUpdatedEvent: Event<MethodUpdated>)

    @EventListener
    abstract fun onCustomerStatusChanged(customerStatusChangedEvent: Event<CustomerStatusChanged>)
}
