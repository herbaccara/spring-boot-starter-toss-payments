package herbaccara.toss.payments

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import herbaccara.boot.autoconfigure.toss.payments.TossPaymentsClientHttpRequestInterceptor
import herbaccara.boot.autoconfigure.toss.payments.TossPaymentsRestTemplateBuilderCustomizer
import herbaccara.toss.payments.form.billing.BillingApproveForm
import herbaccara.toss.payments.form.billing.BillingAuthorizationCardForm
import herbaccara.toss.payments.form.billing.BillingAuthorizationIssueForm
import herbaccara.toss.payments.form.brandpay.BrandPayAuthorizationAccessTokenForm
import herbaccara.toss.payments.form.brandpay.BrandPayPaymentConfirmForm
import herbaccara.toss.payments.form.brandpay.BrandPayTermAgreeForm
import herbaccara.toss.payments.form.brandpay.GrantType
import herbaccara.toss.payments.form.cashreceipt.CashReceiptListForm
import herbaccara.toss.payments.form.cashreceipt.CashReceiptRequestForm
import herbaccara.toss.payments.form.payment.*
import herbaccara.toss.payments.form.settlement.SettlementListForm
import herbaccara.toss.payments.form.submall.SubMallSettlementCreateForm
import herbaccara.toss.payments.form.submall.SubmallCreateForm
import herbaccara.toss.payments.form.submall.SubmallUpdateForm
import herbaccara.toss.payments.form.transaction.TransactionListForm
import herbaccara.toss.payments.model.billing.Billing
import herbaccara.toss.payments.model.brandpay.*
import herbaccara.toss.payments.model.cardpromotion.CardPromotion
import herbaccara.toss.payments.model.cashreceipt.CashReceipt
import herbaccara.toss.payments.model.cashreceipt.CashReceiptList
import herbaccara.toss.payments.model.payment.Payment
import herbaccara.toss.payments.model.settlement.Settlement
import herbaccara.toss.payments.model.submall.Payout
import herbaccara.toss.payments.model.submall.Submall
import herbaccara.toss.payments.model.transaction.Transaction
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.util.UriComponentsBuilder
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TossPaymentsService @JvmOverloads constructor(
    private val clientSecret: String,
    baseUrl: String = BASE_URL,
    timeout: Duration = DEFAULT_TIMEOUT,
    customizers: List<TossPaymentsRestTemplateBuilderCustomizer> = emptyList(),
    interceptors: List<TossPaymentsClientHttpRequestInterceptor> = emptyList()
) {
    companion object {
        /***
         * [2022-11-16](https://docs.tosspayments.com/reference/release-note#2022-11-16)
         */
        const val API_VERSION = "2022-11-16"
        const val BASE_URL: String = "https://api.tosspayments.com"
        val DEFAULT_TIMEOUT: Duration = Duration.ofSeconds(60)
    }

    private val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        findAndRegisterModules()
    }

    private val restTemplate: RestTemplate = RestTemplateBuilder()
        .rootUri(baseUrl)
        .setReadTimeout(timeout)
        .additionalInterceptors(
            ClientHttpRequestInterceptor { request, body, execution ->
                request.headers.apply {
                    contentType = MediaType.APPLICATION_JSON
                }
                execution.execute(request, body)
            }
        )
        .additionalInterceptors(*interceptors.toTypedArray())
        .messageConverters(
            StringHttpMessageConverter(StandardCharsets.UTF_8),
            MappingJackson2HttpMessageConverter(objectMapper)
        )
        .also { builder ->
            for (customizer in customizers) {
                customizer.customize(builder)
            }
        }
        .build()

    // ?????? : https://docs.tosspayments.com/guides/using-api/authorization#%EC%9D%B8%EC%A6%9D
    private val authorization: String by lazy { Base64.getEncoder().encodeToString("$clientSecret:".toByteArray()) }

    private fun <T> getForObject(uri: String, clazz: Class<T>): T {
        val headers = HttpHeaders().apply {
            setBasicAuth(authorization)
        }
        val httpEntity = HttpEntity<Any>(headers)
        val exchange = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, clazz)
        return exchange.body!!
    }

    private fun <R, T> postForObject(uri: String, form: R, clazz: Class<T>): T {
        val headers = HttpHeaders().apply {
            setBasicAuth(authorization)
        }
        val httpEntity = HttpEntity<R>(form, headers)
        val exchange = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, clazz)
        return exchange.body!!
    }

    // ??????

    /***
     * <pre>
     * ?????? ??????
     * successUrl, failUrl ??? ????????? ?????? ???????????? ?????? ????????? url ????????? ??????.
     </pre> *
     *
     * @param form
     * @return
     */
    fun paymentCreate(form: PaymentCreateForm): Payment {
        val uri = "/v1/payments"
        return postForObject(uri, form, Payment::class.java)
    }

    /***
     * ?????? ??????
     * @param form
     * @return
     */
    fun paymentConfirm(form: PaymentConfirmForm): Payment {
        val uri = "/v1/payments/confirm"
        return postForObject(uri, form, Payment::class.java)
    }

    /***
     * ?????? ??????
     * @param paymentKey
     * @return
     */
    fun paymentByPaymentKey(paymentKey: String): Payment {
        val uri = "/v1/payments/$paymentKey"
        return getForObject(uri, Payment::class.java)
    }

    /***
     * ?????? ??????
     * @param orderId
     * @return
     */
    fun paymentByOrderId(orderId: String): Payment {
        val uri = "/v1/payments/orders/$orderId"
        return getForObject(uri, Payment::class.java)
    }

    /***
     * ?????? ??????
     * @param paymentKey
     * @param form
     * @return
     */
    fun paymentCancel(paymentKey: String, form: PaymentCancelForm): Payment {
        val uri = "/v1/payments/$paymentKey/cancel"
        return postForObject(uri, form, Payment::class.java)
    }

    fun paymentCancel(paymentKey: String, cancelReason: String): Payment {
        return paymentCancel(paymentKey, PaymentCancelForm(cancelReason))
    }

    /***
     * ?????? ?????? ??????
     * @param form
     * @return
     */
    fun paymentKeyIn(form: PaymentKeyInForm): Payment {
        val uri = "/v1/payments/key-in"
        return postForObject(uri, form, Payment::class.java)
    }

    /***
     * ???????????? ?????? ??????
     * @param form
     * @return
     */
    fun paymentVirtualAccount(form: PaymentVirtualAccountForm): Payment {
        val uri = "/v1/virtual-accounts"
        return postForObject(uri, form, Payment::class.java)
    }

    // ??????

    /***
     * customerKey??? ?????? ?????? ?????? ????????? ?????? ??????
     * @param form
     * @return
     */
    fun billingAuthorizationCard(form: BillingAuthorizationCardForm): Billing {
        val uri = "/v1/billing/authorizations/card"
        return postForObject(uri, form, Billing::class.java)
    }

    /***
     * authKey??? ?????? ?????? ?????? ????????? ?????? ??????
     * @param form
     * @return
     */
    fun billingAuthorizationIssue(form: BillingAuthorizationIssueForm): Billing {
        val uri = "/v1/billing/authorizations/issue"
        return postForObject(uri, form, Billing::class.java)
    }

    /***
     * ?????? ?????? ?????? ??????
     * @param billingKey
     * @param form
     * @return
     */
    fun billingApprove(billingKey: String, form: BillingApproveForm): Payment {
        val uri = "/v1/billing/$billingKey"
        return postForObject(uri, form, Payment::class.java)
    }

    // ?????? ??????

    /***
     * ?????? ?????? ??????
     * @param form
     * @return
     */
    fun transactions(form: TransactionListForm): List<Transaction> {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        val params = LinkedMultiValueMap<String, String>().apply {
            add("startDate", dateTimeFormatter.format(form.startDate))
            add("endDate", dateTimeFormatter.format(form.endDate))
            if (form.startingAfter != null) {
                add("startingAfter", form.startingAfter)
            }
            if (form.limit != null) {
                add("limit", form.limit.toString())
            }
        }

        val uri = UriComponentsBuilder
            .fromUriString("/v1/transactions")
            .queryParams(params)
            .toUriString()

        return getForObject(uri, Array<Transaction>::class.java).toList()
    }

    @JvmOverloads
    fun transactions(startDate: LocalDateTime, endDate: LocalDateTime = LocalDateTime.now()): List<Transaction> {
        return transactions(TransactionListForm(startDate, endDate))
    }

    // ??????

    /***
     * ?????? ??????
     * @param form
     * @return
     */
    fun settlements(form: SettlementListForm): List<Settlement> {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val params = LinkedMultiValueMap<String, String>().apply {
            add("startDate", dateFormatter.format(form.startDate))
            add("endDate", dateFormatter.format(form.endDate))
            if (form.dateType != null) {
                add("dateType", form.dateType.toString())
            }
            if (form.page != null) {
                add("page", form.page.toString())
            }
            if (form.size != null) {
                add("size", form.size.toString())
            }
        }

        val uri = UriComponentsBuilder
            .fromUriString("/v1/settlements")
            .queryParams(params)
            .toUriString()

        return getForObject(uri, Array<Settlement>::class.java).toList()
    }

    @JvmOverloads
    fun settlements(startDate: LocalDate, endDate: LocalDate = LocalDate.now()): List<Settlement> {
        return settlements(SettlementListForm(startDate, endDate))
    }

    /***
     * ?????? ?????? ??????
     * @param paymentKey
     * @return
     */
    fun settlementRequest(paymentKey: String): Boolean {
        val uri = "/v1/settlements"
        val form = mapOf("paymentKey" to paymentKey)
        val jsonNode: JsonNode = postForObject(uri, form, JsonNode::class.java)
        return jsonNode["result"].asBoolean()
    }

    // ???????????????

    /***
     * ??????????????? ?????? ??????
     * @param form
     * @return
     */
    fun cashReceiptRequest(form: CashReceiptRequestForm): CashReceipt {
        val uri = "/v1/cash-receipt"
        return postForObject(uri, form, CashReceipt::class.java)
    }

    /***
     * ??????????????? ?????? ?????? ??????
     * @param receiptKey
     * @return
     */
    fun cashReceiptCancel(receiptKey: String): CashReceipt {
        val uri = "/v1/cash-receipts/$receiptKey/cancel"
        return postForObject(uri, null, CashReceipt::class.java)
    }

    /***
     * ??????????????? ??????
     * @param form
     * @return
     */
    fun cashReceipts(form: CashReceiptListForm): CashReceiptList {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val params = LinkedMultiValueMap<String, String>().apply {
            add("requestDate", dateFormatter.format(form.requestDate))
            if (form.cursor != null) {
                add("cursor", form.cursor.toString())
            }
            if (form.limit != null) {
                add("limit", form.limit.toString())
            }
        }

        val uri = UriComponentsBuilder
            .fromUriString("/v1/cash-receipts")
            .queryParams(params)
            .toUriString()

        return getForObject(uri, CashReceiptList::class.java)
    }

    @JvmOverloads
    fun cashReceipts(requestDate: LocalDate = LocalDate.now()): CashReceiptList {
        return cashReceipts(CashReceiptListForm(requestDate))
    }

    // ?????????

    /***
     * ????????? ??????
     * @param form
     * @return
     */
    fun submallCreate(form: SubmallCreateForm): Submall {
        val uri = "/v1/payouts/sub-malls"
        return postForObject(uri, form, Submall::class.java)
    }

    /***
     * ????????? ??????
     * @return
     */
    fun submalls(): List<Submall> {
        val uri = "/v1/payouts/sub-malls"
        return getForObject(uri, Array<Submall>::class.java).toList()
    }

    /***
     * ????????? ??????
     * @param form
     * @return
     */
    fun submallUpdate(subMallId: String, form: SubmallUpdateForm): Submall {
        val uri = "/v1/payouts/sub-malls/$subMallId"
        return postForObject(uri, form, Submall::class.java)
    }

    /***
     * ????????? ??????
     * @param subMallId
     * @return
     */
    fun submallDelete(subMallId: String): String {
        val uri = "/v1/payouts/sub-malls/$subMallId/delete"
        return postForObject(uri, null, String::class.java)
    }

    // ????????? ????????????

    /***
     * ?????? ????????? ?????? ??????
     * @return
     */
    fun submallSettlementBalance(): Long {
        val uri = "/v1/payouts/sub-malls/settlements/balance"
        val json: JsonNode = getForObject(uri, JsonNode::class.java)
        return json["balance"].asLong()
    }

    /***
     * ???????????? ??????
     * @param form
     * @return
     */
    fun submallSettlementCreate(vararg form: SubMallSettlementCreateForm): List<Payout> {
        val uri = "/v1/payouts/sub-malls/settlements"
        return postForObject(uri, form, Array<Payout>::class.java).toList()
    }

    /***
     * ???????????? ?????? ??????
     * @param payoutKey
     * @return
     */
    fun submallSettlement(payoutKey: String): Payout {
        val uri = "/v1/payouts/sub-malls/settlements/$payoutKey"
        return getForObject(uri, Payout::class.java)
    }

    /***
     * ???????????? ??????
     * @param startDate
     * @param endDate
     * @return
     */
    @JvmOverloads
    fun submallSettlements(startDate: LocalDate, endDate: LocalDate = LocalDate.now()): List<Payout> {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val params = LinkedMultiValueMap<String, String>().apply {
            add("startDate", dateFormatter.format(startDate))
            add("endDate", dateFormatter.format(endDate))
        }

        val uri = UriComponentsBuilder
            .fromUriString("/v1/payouts/sub-malls/settlements")
            .queryParams(params)
            .toUriString()

        return getForObject(uri, Array<Payout>::class.java).toList()
    }

    // ?????? ?????? ??????

    /***
     * ?????? ?????? ??????
     * [...](https://docs.tosspayments.com/reference#%EC%B9%B4%EB%93%9C-%ED%98%9C%ED%83%9D-%EC%A1%B0%ED%9A%8C-1)
     * @return
     */
    fun cardPromotion(): CardPromotion {
        val uri = "/v1/promotions/card"
        return getForObject(uri, CardPromotion::class.java)
    }

    // ????????? ??????

    fun brandPayTerms(customerKey: String, vararg scopes: Scope): List<Term> {
        val uri = "/v1/brandpay/terms?customerKey=$customerKey&${scopes.joinToString("&") { "&scope=$it" }}"
        return getForObject(uri, Array<Term>::class.java).toList()
    }

    fun brandPayTermAgree(customerKey: String, scope: List<Scope>, termsId: List<Int>): String {
        return brandPayTermAgree(BrandPayTermAgreeForm(customerKey, scope, termsId))
    }

    fun brandPayTermAgree(form: BrandPayTermAgreeForm): String {
        val uri = "/v1/brandpay/terms/agree"
        val json = postForObject(uri, form, JsonNode::class.java)
        return json["code"].asText()
    }

    fun brandPayAuthorizationAccessToken(customerKey: String, grantType: GrantType, code: String): Token {
        return brandPayAuthorizationAccessToken(
            BrandPayAuthorizationAccessTokenForm(
                customerKey,
                grantType,
                code
            )
        )
    }

    fun brandPayAuthorizationAccessToken(form: BrandPayAuthorizationAccessTokenForm): Token {
        val uri = "/v1/brandpay/authorizations/access-token"
        return postForObject(uri, form, Token::class.java)
    }

    fun brandPayPaymentMethodsUsingAccessToken(accessToken: String): BrandPayMethod {
        val headers = HttpHeaders().apply {
            setBearerAuth(accessToken)
        }
        val httpEntity = HttpEntity<Any>(headers)

        val uri = "/v1/brandpay/payments/methods"
        return restTemplate.exchange<BrandPayMethod>(uri, HttpMethod.GET, httpEntity).body!!
    }

    fun brandPayPaymentMethodsUsingSecretKey(customerKey: String): BrandPayMethod {
        val uri = "/v1/brandpay/payments/methods/$customerKey"
        return getForObject(uri, BrandPayMethod::class.java)
    }

    fun brandPayCardRemove(accessToken: String, methodKey: String): BrandPayCard {
        val form = mapOf("methodKey" to methodKey)

        val headers = HttpHeaders().apply {
            setBearerAuth(accessToken)
        }
        val httpEntity = HttpEntity<Map<String, String>>(form, headers)

        val uri = "/v1/brandpay/payments/methods/card/remove"
        return restTemplate.exchange<BrandPayCard>(uri, HttpMethod.POST, httpEntity).body!!
    }

    fun brandPayAccountRemove(accessToken: String, methodKey: String): BrandPayBankAccount {
        val form = mapOf("methodKey" to methodKey)

        val headers = HttpHeaders().apply {
            setBearerAuth(accessToken)
        }
        val httpEntity = HttpEntity<Map<String, String>>(form, headers)

        val uri = "/v1/brandpay/payments/methods/account/remove"
        return restTemplate.exchange<BrandPayBankAccount>(uri, HttpMethod.POST, httpEntity).body!!
    }

    fun brandPayPaymentConfirm(form: BrandPayPaymentConfirmForm): Payment {
        val uri = "/v1/brandpay/payments/confirm"
        return postForObject(uri, form, Payment::class.java)
    }

    // TODO: ?????? ?????? ??????

    fun brandPayCustomerRemove(accessToken: String): Boolean {
        val headers = HttpHeaders().apply {
            setBearerAuth(accessToken)
        }
        val httpEntity = HttpEntity<Any>(headers)

        val uri = "/v1/brandpay/customers/remove"
        val json = restTemplate.exchange<JsonNode>(uri, HttpMethod.POST, httpEntity).body!!
        return json["success"].asBoolean()
    }

    fun brandPayCardPromotion(): BrandPayCardPromotion {
        val uri = "/v1/brandpay/promotions/card"
        return getForObject(uri, BrandPayCardPromotion::class.java)
    }
}
