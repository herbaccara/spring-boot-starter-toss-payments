package herbaccara.toss.payments.form.cashreceipt

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CashReceiptListForm(
    val requestDate: LocalDate,
    val cursor: Long? = null,
    val limit: Long? = null
)
