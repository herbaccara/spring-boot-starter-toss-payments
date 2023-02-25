package herbaccara.toss.payments.form.payment

import com.fasterxml.jackson.annotation.JsonInclude
import herbaccara.toss.payments.form.Vbv

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class PaymentKeyInForm(
    val amount: Long,
    val orderId: String,
    val orderName: String,
    val cardNumber: String,
    val cardExpirationYear: String,
    val cardExpirationMonth: String,
    val customerIdentityNumber: String,
    val cardPassword: String? = null,
    val cardInstallmentPlan: Long? = null,
    val useFreeInstallmentPlan: Long? = null,
    val taxFreeAmount: Long? = null,
    val customerEmail: String? = null,
    val customerName: String? = null,
    val vbv: Vbv? = null
)
