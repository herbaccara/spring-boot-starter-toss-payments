package herbaccara.toss.payments.model.webhook

import herbaccara.toss.payments.model.payment.Payment
import herbaccara.toss.payments.model.submall.Payout

typealias PaymentStatusChanged = Payment
typealias PayoutStatusChanged = Payout

data class Event<T>(
    val eventType: EventType,
    val createdAt: String,
    val data: T
) {
    enum class EventType {
        PAYMENT_STATUS_CHANGED,
        PAYOUT_STATUS_CHANGED,
        METHOD_UPDATED,
        CUSTOMER_STATUS_CHANGED
    }

    data class MethodUpdated(
        val customerKey: String,
        val methodKey: String,
        val status: Status
    ) {
        enum class Status {
            ENABLED,
            DISABLED,
            ALIAS_UPDATED
        }
    }

    data class CustomerStatusChanged(
        val customerKey: String,
        val status: Status,
        val changedAt: String
    ) {
        enum class Status {
            CREATED,
            REMOVED,
            PASSWORD_CHANGED,
            ONE_TOUCH_ACTIVATED,
            ONE_TOUCH_DEACTIVATED
        }
    }
}
