package herbaccara.toss.payments.form.payment

data class PaymentConfirmForm(
    val paymentKey: String,
    val orderId: String,
    val amount: Long
)
