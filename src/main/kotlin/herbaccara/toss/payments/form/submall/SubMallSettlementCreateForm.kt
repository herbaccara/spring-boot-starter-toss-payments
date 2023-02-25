package herbaccara.toss.payments.form.submall

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class SubMallSettlementCreateForm(
    val subMallId: String,
    @field:JsonFormat(pattern = "yyyy-MM-dd")
    val payoutDate: LocalDate,
    val payoutAmount: Long,
    val transferSummary: String? = null,
    val metadata: Map<String, Any> = emptyMap()
)
