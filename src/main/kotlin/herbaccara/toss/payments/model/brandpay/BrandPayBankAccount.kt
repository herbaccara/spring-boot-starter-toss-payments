package herbaccara.toss.payments.model.brandpay

data class BrandPayBankAccount(
    val id: String,
    val methodKey: String,
    val accountName: String,
    val accountNumber: String,
    val alias: String,
    val bankCode: String,
    val icon: Icon,
    val iconUrl: String,
    val registeredAt: String,
    val status: Status,
    val color: Color
)
