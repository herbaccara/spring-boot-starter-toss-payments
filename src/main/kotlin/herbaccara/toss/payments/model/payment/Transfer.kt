package herbaccara.toss.payments.model.payment

data class Transfer(
    val bankCode: String? = null,
    val settlementStatus: SettlementStatus? = null
)
