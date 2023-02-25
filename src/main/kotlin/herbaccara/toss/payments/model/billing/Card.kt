package herbaccara.toss.payments.model.billing

data class Card(
    val issuerCode: String,
    val acquirerCode: String,
    val number: String,
    val cardType: String,
    val ownerType: String
)
