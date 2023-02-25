package herbaccara.toss.payments

import herbaccara.boot.autoconfigure.toss.payments.TossPaymentsAutoConfiguration
import herbaccara.toss.payments.form.payment.Method
import herbaccara.toss.payments.form.payment.PaymentCreateForm
import herbaccara.toss.payments.form.payment.PaymentKeyInForm
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@SpringBootTest(classes = [TossPaymentsAutoConfiguration::class])
@TestPropertySource(locations = ["classpath:application.yml"])
class TossPaymentsServiceTest {

    @Autowired
    lateinit var tossPaymentsService: TossPaymentsService

    final val host = "https://27b2-14-63-84-86.jp.ngrok.io"
    val successUrl = "$host/toss/payments/success"
    val failUrl = "$host/toss/payments/fail"

    fun orderId(): String {
        return "order-${UUID.randomUUID()}"
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
