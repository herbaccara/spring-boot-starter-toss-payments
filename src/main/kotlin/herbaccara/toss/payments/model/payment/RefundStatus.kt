package herbaccara.toss.payments.model.payment

/***
 * 환불 처리 상태입니다. 아래와 같은 상태 값을 가질 수 있습니다.
 * NONE: 환불 요청이 없는 상태입니다.
 * PENDING: 환불을 처리 중인 상태입니다.
 * FAILED: 환불에 실패한 상태입니다.
 * PARTIAL_FAILED: 부분 환불에 실패한 상태입니다.
 * COMPLETED: 환불이 완료된 상태입니다.
 */
enum class RefundStatus {
    NONE, PENDING, FAILED, PARTIAL_FAILED, COMPLETED
}
