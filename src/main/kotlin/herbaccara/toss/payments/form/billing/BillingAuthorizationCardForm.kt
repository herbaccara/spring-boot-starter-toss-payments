package herbaccara.toss.payments.form.billing

import com.fasterxml.jackson.annotation.JsonInclude
import herbaccara.toss.payments.form.Vbv

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BillingAuthorizationCardForm(
    val customerKey: String,
    val cardNumber: String,
    val cardExpirationYear: String,
    val cardExpirationMonth: String,
    val customerIdentityNumber: String,
    val cardPassword: String? = null,
    val customerName: String? = null,
    val customerEmail: String? = null,
    val vbv: Vbv? = null
)
