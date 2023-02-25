package herbaccara.toss.payments

import herbaccara.boot.autoconfigure.toss.payments.TossPaymentsAutoConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest(classes = [TossPaymentsAutoConfiguration::class])
@TestPropertySource(locations = ["classpath:application.yml"])
class TossPaymentsServiceTest {

    @Autowired
    lateinit var tossPaymentsService: TossPaymentsService

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
