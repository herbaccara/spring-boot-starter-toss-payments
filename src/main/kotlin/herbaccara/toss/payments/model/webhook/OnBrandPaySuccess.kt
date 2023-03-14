package herbaccara.toss.payments.model.webhook

data class OnBrandPaySuccess(
    val paymentKey: String,
    val orderId: String,
    val amount: Long,
    val methodId: String
)
