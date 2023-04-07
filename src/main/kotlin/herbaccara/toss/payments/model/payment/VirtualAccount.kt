package herbaccara.toss.payments.model.payment

import java.time.OffsetDateTime

data class VirtualAccount(
    /**
     * 가상계좌 타입을 나타냅니다. "일반", "고정" 중 하나입니다.
     */
    val accountType: String,

    /**
     * 발급된 계좌 번호입니다. 최대 길이는 20자입니다.
     */
    val accountNumber: String,

    /**
     * 가상계좌 은행 숫자 코드입니다. [은행 코드](https://docs.tosspayments.com/reference/codes#%EC%9D%80%ED%96%89-%EC%BD%94%EB%93%9C)를 참고하세요.
     *
     */
    val bankCode: String,

    /**
     * 가상계좌를 발급한 고객 이름입니다. 최대 길이는 100자입니다.
     */
    val customerName: String,

    /**
     * 입금 기한입니다.
     */
    val dueDate: OffsetDateTime,

    /**
     * [RefundStatus]
     */
    val refundStatus: RefundStatus,

    /**
     * 가상계좌가 만료되었는지 여부입니다.
     */
    val expired: Boolean,

    /**
     * [SettlementStatus]
     */
    val settlementStatus: SettlementStatus
)
