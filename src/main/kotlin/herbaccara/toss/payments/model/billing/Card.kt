package herbaccara.toss.payments.model.billing

data class Card(
    val issuerCode: String? = null,
    val acquirerCode: String? = null,
    val number: String? = null,
    val cardType: String? = null,
    val ownerType: String? = null
)
