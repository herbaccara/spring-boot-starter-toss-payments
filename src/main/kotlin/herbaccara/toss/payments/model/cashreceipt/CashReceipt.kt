package herbaccara.toss.payments.model.cashreceipt

import herbaccara.toss.payments.model.Failure
import java.time.OffsetDateTime

data class CashReceipt(
    val receiptKey: String? = null,
    val issueNumber: String? = null,
    val issueStatus: IssueStatus? = null,
    val amount: Long? = null,
    val taxFreeAmount: Long? = null,
    val orderId: String? = null,
    val orderName: String? = null,
    val type: String? = null,
    val transactionType: TransactionType? = null,
    val businessNumber: String? = null,
    val customerIdentityNumber: String? = null,
    val failure: Failure? = null,
    val requestedAt: OffsetDateTime? = null,
    val receiptUrl: String? = null
)
