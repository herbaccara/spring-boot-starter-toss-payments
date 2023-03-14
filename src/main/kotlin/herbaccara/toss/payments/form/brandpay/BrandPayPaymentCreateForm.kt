package herbaccara.toss.payments.form.brandpay

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class BrandPayPaymentCreateForm @JvmOverloads constructor(
    val customerKey: String,
    val methodKey: String,
    val amount: Long,
    val orderId: String,
    val orderName: String,
    val cardInstallmentPlan: Int? = null,
    val cashReceipt: CashReceipt? = null,
    val cultureExpense: Boolean? = null,
    val customerEmail: String? = null,
    val discountCode: String? = null,
    val shippingAddress: String? = null,
    val taxFreeAmount: Long? = null,
    val useCardPoint: Boolean? = null
)
