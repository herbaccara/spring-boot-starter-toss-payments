package herbaccara.toss.payments.form.transaction

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class TransactionListForm(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    /***
     * 특정 거래 기록 이후부터 조회하고 싶다면 startingAfter에 특정하고 싶은 거래의 transactionKey 값을 넣어주면 됩니다.
     */
    val startingAfter: String? = null,
    /***
     * 한 번에 응답받을 기록의 개수입니다. 기본값은 100이고 설정할 수 있는 최대값은 10000입니다.
     */
    val limit: Long? = null
)
