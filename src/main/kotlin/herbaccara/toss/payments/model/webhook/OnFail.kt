package herbaccara.toss.payments.model.webhook

data class OnFail(
    val code: String,
    val message: String,
    val orderId: String
)
