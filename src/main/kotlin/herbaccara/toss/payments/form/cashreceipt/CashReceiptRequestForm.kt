package herbaccara.toss.payments.form.cashreceipt

data class CashReceiptRequestForm(
    val amount: Long,
    val orderId: String,
    val orderName: String,
    val customerIdentityNumber: String,
    /***
     * 현금영수증의 종류입니다. "소득공제", "지출증빙" 중 하나의 값입니다.
     */
    val type: String,
    val taxFreeAmount: Long? = null
)
