package herbaccara.toss.payments.form.payment

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaymentVirtualAccountForm(
    val amount: Long,
    val orderId: String,
    val orderName: String,
    val customerName: String,
    val bank: String,
    val accountType: String? = null,
    val accountKey: String? = null,
    val validHours: String? = null,
    val dueDate: String? = null,
    val customerEmail: String? = null,
    val customerMobilePhone: String? = null,
    val taxFreeAmount: Long? = null,
    val useEscrow: Boolean? = null,
    val cashReceipt: CashReceipt? = null,
    val escrowProducts: List<EscrowProduct>? = null
) {
    data class CashReceipt(
        /***
         * 현금영수증 발급 용도입니다. "소득공제", "지출증빙", "미발행" 중 하나입니다.
         */
        val type: String,
        val registrationNumber: String
    )

    data class EscrowProduct(
        val id: String,
        val name: String,
        val code: String,
        val unitPrice: Long,
        val quantity: Long
    )
}
