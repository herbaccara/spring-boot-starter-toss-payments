package herbaccara.toss.payments.form.brandpay

data class BrandPayPaymentConfirmForm(
    val paymentKey: String,
    val orderId: String,
    val customerKey: String,
    val amount: Long
)
