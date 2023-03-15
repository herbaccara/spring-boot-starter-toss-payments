package herbaccara.toss.payments.form.brandpay

import herbaccara.toss.payments.form.ConfirmForm

data class BrandPayPaymentConfirmForm(
    val paymentKey: String,
    val orderId: String,
    val customerKey: String,
    val amount: Long
) : ConfirmForm
