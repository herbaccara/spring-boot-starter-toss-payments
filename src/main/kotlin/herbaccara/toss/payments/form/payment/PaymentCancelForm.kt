package herbaccara.toss.payments.form.payment

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class PaymentCancelForm(
    val cancelReason: String,
    /**
     * 값이 존재하면 부분취소
     */
    val cancelAmount: Long? = null,

    /**
     * 환불 받을 고객의 계좌
     */
    val refundReceiveAccount: RefundReceiveAccount? = null
) {
    data class RefundReceiveAccount(
        val bank: String,
        val accountNumber: String,
        val holderName: String
    )
}
