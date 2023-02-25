package herbaccara.toss.payments.model.cardpromotion

data class CardPromotion(
    /***
     * 카드사의 즉시 할인 정보입니다.
     */
    val discountCards: List<DiscountCard> = emptyList(),

    /***
     * 카드사의 무이자 할부 정보입니다.
     */
    val interestFreeCards: List<InterestFreeCard> = emptyList()
)
