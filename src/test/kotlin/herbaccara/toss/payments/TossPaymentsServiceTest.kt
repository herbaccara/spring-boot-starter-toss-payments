package herbaccara.toss.payments

import herbaccara.boot.autoconfigure.toss.payments.TossPaymentsAutoConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import java.time.LocalDateTime

@SpringBootTest(classes = [TossPaymentsAutoConfiguration::class])
@TestPropertySource(locations = ["classpath:application.yml"])
class TossPaymentsServiceTest {

    @Autowired
    lateinit var tossPaymentsService: TossPaymentsService

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
