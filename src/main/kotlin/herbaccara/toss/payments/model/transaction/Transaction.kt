package herbaccara.toss.payments.model.transaction

import com.fasterxml.jackson.annotation.JsonProperty
import herbaccara.toss.payments.form.payment.Method
import herbaccara.toss.payments.model.payment.Status
import java.time.OffsetDateTime

data class Transaction(
    @field:JsonProperty("mId")
    val mId: String,
    val transactionKey: String,
    val paymentKey: String,
    val orderId: String,

    /**
     * 코드값이 아닌 한글 문자열로 들어온다.
     * [Method]
     */
    val method: String,
    val customerKey: String? = null,
    val useEscrow: Boolean,
    val receiptUrl: String,
    val status: Status,
    val transactionAt: OffsetDateTime,
    val currency: String,
    val amount: Long
)
