package herbaccara.toss.payments.model.submall

/**
 * 지급대행 상태입니다. 아래와 같은 상태 값을 가질 수 있습니다.
 * - REQUESTED: 지급이 요청된 상태입니다.
 * - COMPLETED: 서브몰에 지급이 완료된 상태입니다.
 * - FAILED: 지급 요청이 실패한 상태입니다.
 */
enum class Status {
    REQUESTED,
    COMPLETED,
    FAILED,

    // 문서에 없는 프로퍼티다.
    IN_PROGRESS
}
