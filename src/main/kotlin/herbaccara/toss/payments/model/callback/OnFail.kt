package herbaccara.toss.payments.model.callback

data class OnFail(
    val code: String,
    val message: String,
    val orderId: String
) : Callback
