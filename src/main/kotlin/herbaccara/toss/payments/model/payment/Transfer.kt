package herbaccara.toss.payments.model.payment

data class Transfer(
    val bankCode: String,
    val settlementStatus: SettlementStatus
)
