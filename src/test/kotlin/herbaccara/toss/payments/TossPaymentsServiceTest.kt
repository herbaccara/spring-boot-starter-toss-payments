package herbaccara.toss.payments

import herbaccara.boot.autoconfigure.toss.payments.TossPaymentsAutoConfiguration
import herbaccara.boot.autoconfigure.toss.payments.TossPaymentsClientHttpRequestInterceptor
import herbaccara.boot.autoconfigure.toss.payments.TossPaymentsRestTemplateBuilderCustomizer
import herbaccara.toss.payments.TossPaymentsServiceTest.TestTossPaymentsClientHttpRequestInterceptor
import herbaccara.toss.payments.TossPaymentsServiceTest.TestTossPaymentsRestTemplateBuilderCustomizer
import herbaccara.toss.payments.form.billing.BillingApproveForm
import herbaccara.toss.payments.form.billing.BillingAuthorizationCardForm
import herbaccara.toss.payments.form.payment.Method
import herbaccara.toss.payments.form.payment.PaymentCreateForm
import herbaccara.toss.payments.form.payment.PaymentKeyInForm
import herbaccara.toss.payments.form.submall.Account
import herbaccara.toss.payments.form.submall.SubmallCreateForm
import herbaccara.toss.payments.form.submall.SubmallUpdateForm
import herbaccara.toss.payments.model.payment.Payment
import herbaccara.toss.payments.model.submall.Type
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.test.context.TestPropertySource
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@SpringBootTest(
    classes = [
        TossPaymentsAutoConfiguration::class,
        TestTossPaymentsRestTemplateBuilderCustomizer::class,
        TestTossPaymentsClientHttpRequestInterceptor::class
    ]
)
@TestPropertySource(locations = ["classpath:application.yml"])
class TossPaymentsServiceTest {

    @Component
    class TestTossPaymentsRestTemplateBuilderCustomizer : TossPaymentsRestTemplateBuilderCustomizer {
        override fun customize(restTemplateBuilder: RestTemplateBuilder) {
            println()
        }
    }

    @Component
    class TestTossPaymentsClientHttpRequestInterceptor : TossPaymentsClientHttpRequestInterceptor {

        override fun intercept(
            request: HttpRequest,
            body: ByteArray,
            execution: ClientHttpRequestExecution
        ): ClientHttpResponse {
            return execution.execute(request, body)
        }
    }

    @Autowired
    lateinit var tossPaymentsService: TossPaymentsService

    final val host = "https://27b2-14-63-84-86.jp.ngrok.io"
    val successUrl = "$host/toss/payments/success"
    val failUrl = "$host/toss/payments/fail"

    fun orderId(): String {
        return "order-${UUID.randomUUID()}"
    }

    fun submallId(): String {
        return "submall-${UUID.randomUUID()}".take(20)
    }

    @Test
    fun billing() {
        val customerKey = "RJtKd3c9QnPfboS_2kTIM"

        val billing = tossPaymentsService.billingAuthorizationCard(
            BillingAuthorizationCardForm(
                customerKey,
                "422155" + "1234123412",
                "20",
                "11",
                "831020"
            )
        )
        println()

        val payment = tossPaymentsService.billingApprove(
            billing.billingKey,
            BillingApproveForm(
                customerKey,
                15000L,
                orderId(),
                "2022년 2월 정기구독"
            )
        )
        println()
    }

    @Test
    fun submall() {
        val submall1 = tossPaymentsService.submallCreate(
            SubmallCreateForm(
                submallId(),
                Account(
                    "06",
                    "00990204333123",
                    "포애도"
                ),
                Type.CORPORATE,
                companyName = "콩콩이",
                representativeName = "손재영",
                businessNumber = "1234567891"
            )
        )

        val subMallId = submall1.subMallId

        val submall2 = tossPaymentsService.submallUpdate(
            subMallId,
            SubmallUpdateForm(
                Account(
                    "06",
                    "00990204333123",
                    "포애도"
                ),
                "콩콩이",
                "손재영",
                "1234567891"
            )
        )
        println()

        val s = tossPaymentsService.submallDelete(subMallId)

        assertEquals(subMallId, s)
    }

    @Test
    fun paymentCreate() {
        val payments = Method.values()
            .map { method ->
                val form = PaymentCreateForm(
                    method,
                    100L,
                    orderId(),
                    "토스 티셔츠 외 100건",
                    successUrl,
                    failUrl
                )

                tossPaymentsService.paymentCreate(form).also {payment->
                    println("paymentKey : ${payment.paymentKey}, method : ${method}, checkoutUrl : ${payment.checkout.url}")
                }
            }

        println()
    }

    @Test
    fun paymentKeyIn() {
        val payment1 = tossPaymentsService.paymentCreate(
            PaymentCreateForm(
                Method.CARD,
                15000L,
                orderId(),
                "토스 티셔츠 외 100건",
                successUrl,
                failUrl
            )
        )
        println()

        val paymentKey: String = payment1.paymentKey

        val payment2 = tossPaymentsService.paymentKeyIn(
            PaymentKeyInForm(
                payment1.totalAmount,
                payment1.orderId,
                payment1.orderName,
                "4330123412341234",
                "24",
                "07",
                "881212"
            )
        )
        println()

        val payment3 = tossPaymentsService.paymentByPaymentKey(paymentKey)
        println()
    }

    @Test
    fun paymentByPaymentKey() {
        val transactions = tossPaymentsService.transactions(LocalDateTime.now().minusDays(7))
        val payments = transactions.map {
            tossPaymentsService.paymentByPaymentKey(it.paymentKey)
        }
        println()
    }

    @Test
    fun paymentByOrderId() {
        val transactions = tossPaymentsService.transactions(LocalDateTime.now().minusDays(7))
        val payments = transactions.map {
            tossPaymentsService.paymentByOrderId(it.orderId)
        }
        println()
    }

    @Test
    fun cardPromotion() {
        val cardPromotion = tossPaymentsService.cardPromotion()
        println()
    }

    @Test
    fun settlements() {
        val settlements = tossPaymentsService.settlements(LocalDate.now().minusDays(7))
        println()
    }

    @Test
    fun submallSettlements() {
        val submallSettlements = tossPaymentsService.submallSettlements(LocalDate.now().minusDays(7))
        println()
    }

    @Test
    fun submalls() {
        val submalls = tossPaymentsService.submalls()
        println()
    }

    @Test
    fun transactions() {
        val transactions = tossPaymentsService.transactions(LocalDateTime.now().minusDays(7))
        println()
    }
}
