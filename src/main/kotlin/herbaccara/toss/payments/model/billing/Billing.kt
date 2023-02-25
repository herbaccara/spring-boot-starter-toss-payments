package herbaccara.toss.payments.model.billing

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class Billing(
    @JsonProperty("mId")
    val mId: String? = null,
    val customerKey: String? = null,
    val authenticatedAt: OffsetDateTime? = null,
    val method: String? = null,
    val billingKey: String? = null,
    val card: Card? = null,

    // cardCompany, cardNumber 는 문서에 없는 프로퍼티다.
    val cardCompany: String? = null,
    val cardNumber: String? = null
)
