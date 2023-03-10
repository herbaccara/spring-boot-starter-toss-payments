package herbaccara.toss.payments.form.submall

data class Account(
    val bank: String,
    val accountNumber: String,
    val holderName: String
) {
    init {
        for (c in accountNumber) {
            if (c.isDigit().not()) throw IllegalArgumentException()
        }
    }
}
