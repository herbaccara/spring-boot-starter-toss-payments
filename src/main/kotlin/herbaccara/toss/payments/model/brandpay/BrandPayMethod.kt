package herbaccara.toss.payments.model.brandpay

import com.fasterxml.jackson.annotation.JsonProperty

data class BrandPayMethod(
    @field:JsonProperty("isIdentified") val isIdentified: Boolean,
    @field:JsonProperty("selectedMethodId") val selectedMethodId: String?,
    @field:JsonProperty("cards") val cards: List<BrandPayCard>,
    @field:JsonProperty("accounts") val accounts: List<BrandPayBankAccount>
)
