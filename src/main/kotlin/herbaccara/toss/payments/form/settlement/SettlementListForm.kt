package herbaccara.toss.payments.form.settlement

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class SettlementListForm(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val dateType: DateType? = null,
    val page: Long? = null,
    val size: Long? = null
) {
    @Suppress("EnumEntryName")
    enum class DateType {
        /**
         * 정산 매출일
         */
        soldDate,

        /**
         * 정산 지급일
         */
        paidOutDate
    }
}
