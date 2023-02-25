package herbaccara.toss.payments.model.payment

data class EasyPay(
    val provider: String? = null,
    val amount: Long? = null,
    val discountAmount: Long? = null
)
