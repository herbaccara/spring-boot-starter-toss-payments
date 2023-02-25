package herbaccara.toss.payments.model.settlement

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.OffsetDateTime

data class Settlement(
    @field:JsonProperty("mId")
    val mId: String? = null,
    val paymentKey: String? = null,
    val transactionKey: String? = null,
    val orderId: String? = null,
    val currency: String? = null,
    val method: String? = null,
    val amount: Long? = null,
    val interestFee: Long? = null,
    val fees: List<Fee>? = null,
    val supplyAmount: Long? = null,
    val vat: Long? = null,
    val payOutAmount: Long? = null,
    val approvedAt: OffsetDateTime? = null,
    val soldDate: LocalDate? = null,
    val paidOutDate: LocalDate? = null
)
