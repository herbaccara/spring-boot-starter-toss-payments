package herbaccara.toss.payments.form.billing

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BillingApproveForm(
    val customerKey: String,
    val amount: Long,
    val orderId: String,
    val orderName: String,
    val customerEmail: String? = null,
    val customerName: String? = null,
    val customerMobilePhone: String? = null,
    val taxFreeAmount: Long? = null,
    val cardInstallmentPlan: Long? = null
)
