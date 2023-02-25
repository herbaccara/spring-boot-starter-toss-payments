package herbaccara.toss.payments.model.payment

data class MobilePhone(
    val customerMobilePhone: String? = null,
    val settlementStatus: SettlementStatus? = null,
    val receiptUrl: String? = null
)
