package herbaccara.toss.payments.form.brandpay

import com.fasterxml.jackson.annotation.JsonProperty

data class CustomerIdentity(
    @field:JsonProperty("ci") val ci: String? = null,
    @field:JsonProperty("mobilePhone") val mobilePhone: String? = null,
    @field:JsonProperty("name") val name: String? = null,
    @field:JsonProperty("rrn") val rrn: String? = null
)
