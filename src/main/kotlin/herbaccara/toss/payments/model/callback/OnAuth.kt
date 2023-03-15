package herbaccara.toss.payments.model.callback

data class OnAuth(
    val code: String,
    val customerKey: String
) : Callback
