package herbaccara.toss.payments.model.brandpay

data class BrandPayCard(
    val id: String,
    val methodKey: String,
    val alias: String,
    val cardName: String,
    val cardNumber: String,
    val issuerCode: String,
    val acquirerCode: String,
    val ownerType: String,
    val cardType: String,
    val installmentMinimumAmount: Long,
    val registeredAt: String,
    val status: Status,
    val icon: String,
    val iconUrl: String,
    val cardImgUrl: String,
    val color: Color
)
