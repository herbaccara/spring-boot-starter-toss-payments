package herbaccara.toss.payments.model.cashreceipt

/**
 * 현금영수증 발급 종류입니다. 현금영수증 발급·취소 건을 구분합니다.
 * CONFIRM - 현금영수증을 발급했을 때
 * CANCEL - 발급된 현금영수증을 취소했을 때
 */
enum class TransactionType {
    CONFIRM, CANCEL
}
