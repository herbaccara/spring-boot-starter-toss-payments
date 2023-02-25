package herbaccara.boot.autoconfigure.toss.payments

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConfigurationProperties(prefix = "toss.payments")
@ConstructorBinding
data class TossPaymentsProperties(
    val clientKey: String,
    val clientSecret: String,
    val rootUri: String = "https://api.tosspayments.com",
    val failOnUnknownProperties: Boolean = false,
    val readTimeout: Duration = Duration.ofSeconds(60),
    val allowIps: List<String> = mutableListOf("13.124.18.147", "13.124.108.35", "3.36.173.151", "3.38.81.32")
)
