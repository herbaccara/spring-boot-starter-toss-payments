package herbaccara.toss.payments.model.payment

/**
 * 무이자 할부가 적용된 결제에서 할부 수수료를 부담하는 주체입니다. BUYER, CARD_COMPANY, MERCHANT 중 하나입니다.
 * - BUYER: 상품을 구매한 고객이 할부 수수료를 부담합니다.
 * - CARD_COMPANY: 카드사에서 할부 수수료를 부담합니다.
 * - MERCHANT: 상점에서 할부 수수료를 부담합니다.
 */
enum class InterestPayer {
    BUYER, CARD_COMPANY, MERCHANT
}
