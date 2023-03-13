package herbaccara.toss.payments.model.brandpay

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class PointCard(
    val companyCode: String,
    val minimumPaymentAmount: Long,
    @field:JsonFormat(pattern = "yyyy-MM-dd") val dueDate: LocalDate
)
