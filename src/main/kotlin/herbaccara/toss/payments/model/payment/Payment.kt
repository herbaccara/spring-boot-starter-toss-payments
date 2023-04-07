package herbaccara.toss.payments.model.payment

import com.fasterxml.jackson.annotation.JsonProperty
import herbaccara.toss.payments.model.Failure
import java.time.OffsetDateTime

data class Payment(
    val version: String,
    val paymentKey: String,
    val type: Type,
    val orderId: String,
    val orderName: String,
    @field:JsonProperty("mId")
    val mId: String,
    val currency: String,
    val method: String? = null,
    val totalAmount: Long,
    val balanceAmount: Long,
    val status: Status,
    val requestedAt: OffsetDateTime,
    val approvedAt: OffsetDateTime? = null,
    val useEscrow: Boolean,
    val lastTransactionKey: String? = null,
    val suppliedAmount: Long,
    val vat: Long,
    val cultureExpense: Boolean,
    val taxFreeAmount: Long,
    val taxExemptionAmount: Long,
    val cancels: List<Cancel>? = null,
    val isPartialCancelable: Boolean,
    val card: Card? = null,
    val virtualAccount: VirtualAccount? = null,
    val secret: String? = null,
    val mobilePhone: MobilePhone? = null,
    val giftCertificate: GiftCertificate? = null,
    val transfer: Transfer? = null,
    val receipt: Receipt? = null,

    /**
     * <pre>
     * 결제창 정보입니다.
     *
     * 백엔드에서 결제를 생성 하고 나면 checkout.url 주소를 유저에게 전달한 다음 결제창을 띄울 수 있도록 유도 한다.
     </pre> *
     */
    val checkout: Checkout,
    val easyPay: EasyPay? = null,
    val country: String,
    val failure: Failure? = null,
    val cashReceipt: CashReceipt? = null,
    val discount: Discount? = null
)
