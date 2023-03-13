package herbaccara.toss.payments.form.brandpay

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class BrandPayAuthorizationAccessTokenForm @JvmOverloads constructor(
    @field:JsonProperty("customerKey") val customerKey: String,
    @field:JsonProperty("grantType") val grantType: GrantType,
    @field:JsonProperty("code") val code: String,
    @field:JsonProperty("refreshToken") val refreshToken: String? = null,
    @field:JsonProperty("customerIdentity") val customerIdentity: CustomerIdentity? = null
)
