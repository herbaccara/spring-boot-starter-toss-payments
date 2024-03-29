package herbaccara.boot.autoconfigure.toss.payments

import herbaccara.toss.payments.TossPaymentsAuthController
import herbaccara.toss.payments.TossPaymentsService
import herbaccara.toss.payments.TossPaymentsWebhookController
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConfigurationProperties(prefix = "toss.payments")
@ConstructorBinding
data class TossPaymentsProperties(
    val enabled: Boolean = true,
    val clientKey: String,
    val clientSecret: String,
    val rootUri: String = TossPaymentsService.BASE_URL,
    val readTimeout: Duration = TossPaymentsService.DEFAULT_TIMEOUT,
    val failOnUnknownProperties: Boolean = false,
    val allowIps: List<String> = TossPaymentsWebhookController.DEFAULT_ALLOW_IPS,
    val webhook: Webhook = Webhook(),
    val auth: Auth = Auth()
) {
    data class Webhook(
        val enabled: Boolean = true,
        val path: String = TossPaymentsWebhookController.DEFAULT_WEBHOOK_PATH
    )

    data class Auth(
        val enabled: Boolean = true,
        val path: String = TossPaymentsAuthController.DEFAULT_WEBHOOK_PATH
    )
}
