package herbaccara.toss.payments.form.payment

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaymentCreateForm(
    /***
     * 결제 수단입니다.
     */
    val method: Method,
    val amount: Long,
    val orderId: String,
    val orderName: String,

    /***
     * 반드시 외부 서버에서 접근 가능한 url 이여야 한다.
     */
    val successUrl: String,

    /***
     * 반드시 외부 서버에서 접근 가능한 url 이여야 한다.
     */
    val failUrl: String,
    val flowMode: FlowMode? = null,
    val easyPay: String? = null,
    val cardCompany: String? = null,
    val appScheme: String? = null

) {
    enum class FlowMode {
        DEFAULT, DIRECT
    }
}
