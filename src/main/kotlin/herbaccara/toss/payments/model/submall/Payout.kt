package herbaccara.toss.payments.model.submall

import com.fasterxml.jackson.annotation.JsonFormat
import herbaccara.toss.payments.model.Failure
import java.time.LocalDate
import java.time.LocalDateTime

data class Payout(
    val payoutKey: String? = null,
    val requestId: String? = null,
    val subMallId: String? = null,

    @field:JsonFormat(pattern = "yyyyMMdd")
    val payoutDate: LocalDate? = null,
    val payoutAmount: Long? = null,
    val account: Account? = null,

    @field:JsonFormat(pattern = "yyyyMMddHHmmss")
    val requestedAt: LocalDateTime? = null,
    val status: Status? = null,
    val failure: Failure? = null,
    val transferSummary: String? = null,
    val metadata: Map<String, Any>? = null
)
