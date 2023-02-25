package herbaccara.toss.payments.model.billing

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class Billing(
    @JsonProperty("mId")
    val mId: String,
    val customerKey: String,
    val authenticatedAt: OffsetDateTime,
    val method: String,
    val billingKey: String,
    val card: Card,

    // cardCompany, cardNumber 는 문서에 없는 프로퍼티다.
    val cardCompany: String,
    val cardNumber: String
)
