package herbaccara.toss.payments

import herbaccara.toss.payments.form.billing.BillingApproveForm
import herbaccara.toss.payments.form.billing.BillingAuthorizationCardForm
import herbaccara.toss.payments.form.billing.BillingAuthorizationIssueForm
import herbaccara.toss.payments.form.cashreceipt.CashReceiptListForm
import herbaccara.toss.payments.form.cashreceipt.CashReceiptRequestForm
import herbaccara.toss.payments.form.payment.*
import herbaccara.toss.payments.form.settlement.SettlementListForm
import herbaccara.toss.payments.form.submall.SubMallSettlementCreateForm
import herbaccara.toss.payments.form.submall.SubmallCreateForm
import herbaccara.toss.payments.form.submall.SubmallUpdateForm
import herbaccara.toss.payments.form.transaction.TransactionListForm
import herbaccara.toss.payments.model.billing.Billing
import herbaccara.toss.payments.model.cardpromotion.CardPromotion
import herbaccara.toss.payments.model.cashreceipt.CashReceipt
import herbaccara.toss.payments.model.cashreceipt.CashReceiptList
import herbaccara.toss.payments.model.payment.Payment
import herbaccara.toss.payments.model.settlement.Settlement
import herbaccara.toss.payments.model.submall.Payout
import herbaccara.toss.payments.model.submall.Submall
import herbaccara.toss.payments.model.transaction.Transaction
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.time.LocalDate
import java.time.LocalDateTime

class TossPaymentsService(
    private val restTemplate: RestTemplate
) {
    companion object {
        /***
         * [2022-11-16](https://docs.tosspayments.com/reference/release-note#2022-11-16)
         */
        const val API_VERSION = "2022-11-16"
    }

    private val paymentService = PaymentService(restTemplate)
    private val billingService = BillingService(restTemplate)
    private val transactionService = TransactionService(restTemplate)
    private val settlementService = SettlementService(restTemplate)
    private val cashReceiptService = CashReceiptService(restTemplate)
    private val submallService = SubmallService(restTemplate)

    // 결제

    /***
     * <pre>
     * 결제 생성
     * successUrl, failUrl 은 반드시 외부 서버에서 접근 가능한 url 이여야 한다.
     </pre> *
     *
     * @param form
     * @return
     */
    fun paymentCreate(form: PaymentCreateForm): Payment {
        return paymentService.create(form)
    }

    /***
     * 결제 승인
     * @param form
     * @return
     */
    fun paymentConfirm(form: PaymentConfirmForm): Payment {
        return paymentService.confirm(form)
    }

    /***
     * 결제 조회
     * @param paymentKey
     * @return
     */
    fun paymentByPaymentKey(paymentKey: String): Payment {
        return paymentService.paymentByPaymentKey(paymentKey)
    }

    /***
     * 결제 조회
     * @param orderId
     * @return
     */
    fun paymentByOrderId(orderId: String): Payment {
        return paymentService.paymentByOrderId(orderId)
    }

    /***
     * 결제 취소
     * @param paymentKey
     * @param form
     * @return
     */
    fun paymentCancel(paymentKey: String, form: PaymentCancelForm): Payment {
        return paymentService.cancel(paymentKey, form)
    }

    fun paymentCancel(paymentKey: String, cancelReason: String): Payment {
        return paymentCancel(paymentKey, PaymentCancelForm(cancelReason))
    }

    /***
     * 카드 번호 결제
     * @param form
     * @return
     */
    fun paymentKeyIn(form: PaymentKeyInForm): Payment {
        return paymentService.keyIn(form)
    }

    /***
     * 가상계좌 발급 요청
     * @param form
     * @return
     */
    fun paymentVirtualAccount(form: PaymentVirtualAccountForm): Payment {
        return paymentService.virtualAccount(form)
    }

    // 빌링

    /***
     * customerKey로 카드 자동 결제 빌링키 발급 요청
     * @param form
     * @return
     */
    fun billingAuthorizationCard(form: BillingAuthorizationCardForm): Billing {
        return billingService.authorizationCard(form)
    }

    /***
     * authKey로 카드 자동 결제 빌링키 발급 요청
     * @param form
     * @return
     */
    fun billingAuthorizationIssue(form: BillingAuthorizationIssueForm): Billing {
        return billingService.authorizationIssue(form)
    }

    /***
     * 카드 자동 결제 승인
     * @param billingKey
     * @param form
     * @return
     */
    fun billingApprove(billingKey: String, form: BillingApproveForm): Payment {
        return billingService.approve(billingKey, form)
    }

    // 거래 내역

    /***
     * 거래 내역 조회
     * @param form
     * @return
     */
    fun transactions(form: TransactionListForm): List<Transaction> {
        return transactionService.list(form)
    }

    fun transactions(startDate: LocalDateTime, endDate: LocalDateTime): List<Transaction> {
        return transactions(TransactionListForm(startDate, endDate))
    }

    fun transactions(startDate: LocalDateTime): List<Transaction> {
        return transactions(startDate, LocalDateTime.now())
    }

    // 정산

    /***
     * 정산 조회
     * @param form
     * @return
     */
    fun settlements(form: SettlementListForm): List<Settlement> {
        return settlementService.list(form)
    }

    fun settlements(startDate: LocalDate, endDate: LocalDate): List<Settlement> {
        return settlements(SettlementListForm(startDate, endDate))
    }

    fun settlements(startDate: LocalDate): List<Settlement> {
        return settlements(startDate, LocalDate.now())
    }

    /***
     * 수동 정산 요청
     * @param paymentKey
     * @return
     */
    fun settlementRequest(paymentKey: String): Boolean {
        return settlementService.request(paymentKey)
    }

    // 현금영수증

    /***
     * 현금영수증 발급 요청
     * @param form
     * @return
     */
    fun cashReceiptRequest(form: CashReceiptRequestForm): CashReceipt {
        return cashReceiptService.request(form)
    }

    /***
     * 현금영수증 발급 취소 요청
     * @param receiptKey
     * @return
     */
    fun cashReceiptCancel(receiptKey: String): CashReceipt {
        return cashReceiptService.cancel(receiptKey)
    }

    /***
     * 현금영수증 조회
     * @param form
     * @return
     */
    fun cashReceipts(form: CashReceiptListForm): CashReceiptList {
        return cashReceiptService.list(form)
    }

    fun cashReceipts(requestDate: LocalDate): CashReceiptList {
        return cashReceipts(CashReceiptListForm(requestDate))
    }

    fun cashReceipts(): CashReceiptList {
        return cashReceipts(LocalDate.now())
    }

    // 서브몰

    /***
     * 서브몰 등록
     * @param form
     * @return
     */
    fun submallCreate(form: SubmallCreateForm): Submall {
        return submallService.create(form)
    }

    /***
     * 서브몰 조회
     * @return
     */
    fun submalls(): List<Submall> {
        return submallService.list()
    }

    /***
     * 서브몰 수정
     * @param form
     * @return
     */
    fun submallUpdate(subMallId: String, form: SubmallUpdateForm): Submall {
        return submallService.update(subMallId, form)
    }

    /***
     * 서브몰 삭제
     * @param subMallId
     * @return
     */
    fun submallDelete(subMallId: String): String {
        return submallService.delete(subMallId)
    }

    // 서브몰 지급대행

    /***
     * 지급 가능한 잔액 조회
     * @return
     */
    fun submallSettlementBalance(): Long {
        return submallService.settlementBalance()
    }

    /***
     * 지급대행 요청
     * @param form
     * @return
     */
    fun submallSettlementCreate(vararg form: SubMallSettlementCreateForm): List<Payout> {
        return submallService.settlementCreate(*form)
    }

    /***
     * 지급대행 단건 조회
     * @param payoutKey
     * @return
     */
    fun submallSettlement(payoutKey: String): Payout {
        return submallService.settlement(payoutKey)
    }

    /***
     * 지급대행 조회
     * @param startDate
     * @param endDate
     * @return
     */
    fun submallSettlements(startDate: LocalDate, endDate: LocalDate): List<Payout> {
        return submallService.settlements(startDate, endDate)
    }

    fun submallSettlements(startDate: LocalDate): List<Payout> {
        return submallSettlements(startDate, LocalDate.now())
    }

    // 카드 혜택 조회

    /***
     * 카드 혜택 조회
     * [...](https://docs.tosspayments.com/reference#%EC%B9%B4%EB%93%9C-%ED%98%9C%ED%83%9D-%EC%A1%B0%ED%9A%8C-1)
     * @return
     */
    fun cardPromotion(): CardPromotion {
        val uri = "/v1/promotions/card"
        return restTemplate.getForObject(uri)
    }
}
