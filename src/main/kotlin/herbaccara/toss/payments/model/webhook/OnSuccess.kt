package herbaccara.toss.payments.model.webhook

data class OnSuccess(
    val paymentKey: String,
    val orderId: String,
    val amount: Long
)
