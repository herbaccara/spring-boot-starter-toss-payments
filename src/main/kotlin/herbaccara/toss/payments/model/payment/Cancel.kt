package herbaccara.toss.payments.model.payment

import java.time.OffsetDateTime

data class Cancel(
    val cancelAmount: Long? = null,
    val cancelReason: String? = null,
    val taxFreeAmount: Long? = null,
    val taxExemptionAmount: Long? = null,
    val refundableAmount: Long? = null,
    val easyPayDiscountAmount: Long? = null,
    val canceledAt: OffsetDateTime? = null,
    val transactionKey: String? = null
)
