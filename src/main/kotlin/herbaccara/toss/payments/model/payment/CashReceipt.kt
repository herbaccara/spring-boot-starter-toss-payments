package herbaccara.toss.payments.model.payment

data class CashReceipt(
    val receiptKey: String? = null,
    val type: String? = null,
    val amount: String? = null,
    val taxFreeAmount: Long? = null,
    val issueNumber: String? = null,
    val receiptUrl: String? = null
)
