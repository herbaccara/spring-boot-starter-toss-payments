package herbaccara.toss.payments.model.settlement

/***
 * 수수료의 세부 타입입니다.
 * BASE: 기본 수수료
 * INSTALLMENT_DISCOUNT: 카드사 혹은 토스페이먼츠가 부담하는 무이자 할부 수수료
 * INSTALLMENT: 상점이 부담하는 무이자 할부 수수료
 * POINT_SAVING: 카드사 포인트 적립 수수료
 * ETC: 기타 수수료
 */
enum class Type {
    BASE, INSTALLMENT_DISCOUNT, INSTALLMENT, POINT_SAVING, ETC
}
