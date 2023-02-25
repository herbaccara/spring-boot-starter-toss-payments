package herbaccara.toss.payments.model.payment

data class MobilePhone(
    val customerMobilePhone: String,
    val settlementStatus: SettlementStatus,
    val receiptUrl: String? = null
)
