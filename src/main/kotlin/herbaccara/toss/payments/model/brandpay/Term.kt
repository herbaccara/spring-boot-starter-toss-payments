package herbaccara.toss.payments.model.brandpay

data class Term(
    val id: Int,
    val title: String,
    val version: String,
    val url: String,
    val required: Boolean,
    val agreed: Boolean
)
