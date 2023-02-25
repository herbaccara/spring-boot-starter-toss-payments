package herbaccara.boot.autoconfigure.toss.payments

import herbaccara.toss.payments.WebhookController
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@AutoConfiguration
@ConditionalOnClass(value = [WebMvcConfigurer::class])
@ConditionalOnProperty(prefix = "toss.payments.webhook", value = ["enabled"], havingValue = "true")
class TossPaymentsWebMvcConfigurer : WebMvcConfigurer {

    @Bean
    @ConditionalOnMissingBean(WebhookController::class)
    fun webhookController(applicationEventPublisher: ApplicationEventPublisher): WebhookController {
        return WebhookController(applicationEventPublisher)
    }
}
