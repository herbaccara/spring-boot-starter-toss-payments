package herbaccara.toss.payments.model.cashreceipt

data class CashReceiptList(
    val hasNext: Boolean,
    val lastCursor: Long,
    val data: List<CashReceipt> = emptyList()
)
