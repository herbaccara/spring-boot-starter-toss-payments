package herbaccara.toss.payments.form.brandpay

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CashReceipt @JvmOverloads constructor(
    val type: String,
    val registrationNumberType: String? = null,
    val registrationNumber: String? = null
)
