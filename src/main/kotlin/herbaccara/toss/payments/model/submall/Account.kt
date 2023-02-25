package herbaccara.toss.payments.model.submall

data class Account(
    val bankCode: String,
    val accountNumber: String,
    val holderName: String? = null
)
