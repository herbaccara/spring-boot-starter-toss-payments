package herbaccara.boot.autoconfigure.toss.payments

import herbaccara.toss.payments.TossPaymentsAuthController
import herbaccara.toss.payments.TossPaymentsService
import herbaccara.toss.payments.TossPaymentsWebhookController
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import java.util.*

@AutoConfiguration
@EnableConfigurationProperties(TossPaymentsProperties::class)
@ConditionalOnProperty(prefix = "toss.payments", value = ["enabled"], havingValue = "true", matchIfMissing = true)
class TossPaymentsAutoConfiguration {

    @AutoConfiguration
    class TossPaymentsWebMvcConfigurer {

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "toss.payments.webhook", value = ["enabled"], havingValue = "true")
        fun tossPaymentsWebhookController(applicationEventPublisher: ApplicationEventPublisher): TossPaymentsWebhookController {
            return TossPaymentsWebhookController(applicationEventPublisher)
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "toss.payments.auth", value = ["enabled"], havingValue = "true")
        fun tossPaymentsAuthInterceptor(): TossPaymentsAuthInterceptor {
            return TossPaymentsAuthInterceptor()
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "toss.payments.auth", value = ["enabled"], havingValue = "true")
        fun tossPaymentsAuthController(
            tossPaymentsService: TossPaymentsService,
            tossPaymentsAuthInterceptor: TossPaymentsAuthInterceptor
        ): TossPaymentsAuthController {
            return TossPaymentsAuthController(tossPaymentsService, tossPaymentsAuthInterceptor)
        }
    }

    @Bean
    @ConditionalOnMissingBean
    fun tossPaymentsService(
        properties: TossPaymentsProperties,
        customizers: List<TossPaymentsRestTemplateBuilderCustomizer>,
        interceptors: List<TossPaymentsClientHttpRequestInterceptor>
    ): TossPaymentsService {
        if (properties.clientSecret.isEmpty()) throw NullPointerException()

        return TossPaymentsService(
            properties.clientSecret,
            properties.rootUri,
            properties.readTimeout,
            properties.failOnUnknownProperties,
            customizers,
            interceptors
        )
    }
}
