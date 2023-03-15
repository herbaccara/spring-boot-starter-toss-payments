package herbaccara.toss.payments.model.callback

/***
 * https://docs.tosspayments.com/guides/billing/integration#%EB%A6%AC%EB%8B%A4%EC%9D%B4%EB%A0%89%ED%8A%B8-url-%ED%99%95%EC%9D%B8%ED%95%98%EA%B8%B0
 */
data class OnBillingSuccess(
    val customerKey: String,
    val authKey: String
)
