package herbaccara.toss.payments.model.cashreceipt

/**
 * 현금영수증 발급 상태입니다. 발급 승인 여부는 요청 후 1-2일 뒤 조회할 수 있습니다.
 * - IN_PROGRESS: 현금영수증 발급이 대기 중인 상태입니다.
 * - SENT: 현금영수증 발급이 요청된 상태입니다.
 * - COMPLETED: 현금영수증이 발급된 상태입니다.
 * - FAILED: 현금영수증 발급에 실패한 상태입니다.
 */
enum class IssueStatus {
    IN_PROGRESS, SENT, COMPLETED, FAILED
}
