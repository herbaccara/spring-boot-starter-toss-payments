package herbaccara.toss.payments.form.billing

import com.fasterxml.jackson.annotation.JsonInclude
import herbaccara.toss.payments.form.ConfirmForm

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class BillingConfirmForm @JvmOverloads constructor(
    val customerKey: String,
    val amount: Long,
    val orderId: String,
    val orderName: String,
    val customerEmail: String? = null,
    val customerName: String? = null,
    val customerMobilePhone: String? = null,
    val taxFreeAmount: Long? = null,
    val cardInstallmentPlan: Long? = null
) : ConfirmForm
