package herbaccara.toss.payments.form.brandpay

import com.fasterxml.jackson.annotation.JsonProperty
import herbaccara.toss.payments.model.brandpay.Scope

data class BrandPayTermAgreeForm(
    @field:JsonProperty("customerKey") val customerKey: String,
    @field:JsonProperty("scope") val scope: List<Scope>,
    @field:JsonProperty("termsId") val termsId: List<Int>
)
