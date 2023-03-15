package herbaccara.toss.payments.model.callback

data class OnSuccess(
    val paymentKey: String,
    val orderId: String,
    val amount: Long
) : Callback.Success
