package herbaccara.toss.payments.model.brandpay

import com.fasterxml.jackson.annotation.JsonProperty

data class Token(
    @field:JsonProperty("accessToken") val accessToken: String,
    @field:JsonProperty("refreshToken") val refreshToken: String,
    @field:JsonProperty("tokenType") val tokenType: String,
    @field:JsonProperty("expiresIn") val expiresIn: Long
)
