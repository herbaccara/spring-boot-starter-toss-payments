package herbaccara.toss.payments.model.brandpay

import herbaccara.toss.payments.model.cardpromotion.DiscountCard
import herbaccara.toss.payments.model.cardpromotion.InterestFreeCard

data class BrandPayCardPromotion(
    /***
     * 카드사의 즉시 할인 정보입니다.
     */
    val discountCards: List<DiscountCard>,

    /***
     * 카드사의 무이자 할부 정보입니다.
     */
    val interestFreeCards: List<InterestFreeCard>,

    /***
     * 카드 포인트 정보입니다.
     */
    val pointCards: List<PointCard>
)
