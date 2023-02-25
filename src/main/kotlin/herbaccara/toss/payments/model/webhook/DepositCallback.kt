package herbaccara.toss.payments.model.webhook

import herbaccara.toss.payments.model.payment.Status

data class DepositCallback(
    val createdAt: String,
    val secret: String,
    val status: Status,
    val transactionKey: String,
    val orderId: String
)
