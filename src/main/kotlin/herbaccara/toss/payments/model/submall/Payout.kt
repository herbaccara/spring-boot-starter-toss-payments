package herbaccara.toss.payments.model.submall

import com.fasterxml.jackson.annotation.JsonFormat
import herbaccara.toss.payments.model.Failure
import java.time.LocalDate
import java.time.LocalDateTime

data class Payout(
    val payoutKey: String,
    val requestId: String? = null,
    val subMallId: String,

    @field:JsonFormat(pattern = "yyyyMMdd")
    val payoutDate: LocalDate,
    val payoutAmount: Long,
    val account: Account? = null,

    @field:JsonFormat(pattern = "yyyyMMddHHmmss")
    val requestedAt: LocalDateTime,
    val status: Status,
    val failure: Failure? = null,
    val transferSummary: String? = null,
    val metadata: Map<String, Any>? = null
)
