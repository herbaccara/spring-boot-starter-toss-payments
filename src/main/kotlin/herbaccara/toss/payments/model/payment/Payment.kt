package herbaccara.toss.payments.model.payment

import com.fasterxml.jackson.annotation.JsonProperty
import herbaccara.toss.payments.model.Failure
import java.time.OffsetDateTime

data class Payment(
    val version: String? = null,
    val paymentKey: String? = null,
    val type: Type? = null,
    val orderId: String? = null,
    val orderName: String? = null,
    @field:JsonProperty("mId")
    val mId: String? = null,
    val currency: String? = null,
    val method: String? = null,
    val totalAmount: Long? = null,
    val balanceAmount: Long? = null,
    val status: Status? = null,
    val requestedAt: OffsetDateTime? = null,
    val approvedAt: OffsetDateTime? = null,
    val useEscrow: Boolean? = null,
    val lastTransactionKey: String? = null,
    val suppliedAmount: Long? = null,
    val vat: Long? = null,
    val cultureExpense: Boolean? = null,
    val taxFreeAmount: Long? = null,
    val taxExemptionAmount: Long? = null,
    val cancels: List<Cancel> = emptyList(),
    val isPartialCancelable: Boolean? = null,
    val card: Card? = null,
    val virtualAccount: VirtualAccount? = null,
    val secret: String? = null,
    val mobilePhone: MobilePhone? = null,
    val giftCertificate: GiftCertificate? = null,
    val transfer: Transfer? = null,
    val receipt: Receipt? = null,

    /***
     * <pre>
     * 결제창 정보입니다.
     *
     * 백엔드에서 결제를 생성 하고 나면 checkout.url 주소를 유저에게 전달한 다음 결제창을 띄울 수 있도록 유도 한다.
     </pre> *
     */
    val checkout: Checkout? = null,
    val easyPay: EasyPay? = null,
    val country: String? = null,
    val failure: Failure? = null,
    val cashReceipt: CashReceipt? = null,
    val discount: Discount? = null
)
