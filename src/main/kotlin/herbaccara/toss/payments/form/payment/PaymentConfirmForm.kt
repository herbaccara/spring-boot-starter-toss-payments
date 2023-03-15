package herbaccara.toss.payments.form.payment

import herbaccara.toss.payments.form.ConfirmForm

data class PaymentConfirmForm(
    val paymentKey: String,
    val orderId: String,
    val amount: Long
) : ConfirmForm
