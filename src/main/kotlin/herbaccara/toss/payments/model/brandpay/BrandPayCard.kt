package herbaccara.toss.payments.model.brandpay

import java.time.OffsetDateTime

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
    val registeredAt: OffsetDateTime,
    val status: Status,
    val icon: String,
    val iconUrl: String,
    val cardImgUrl: String,
    val color: Color,

    // 매뉴얼에 없는 프로퍼티
    val cardProductCode: String
)
