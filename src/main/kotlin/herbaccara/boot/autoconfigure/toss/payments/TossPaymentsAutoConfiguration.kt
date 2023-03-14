package herbaccara.boot.autoconfigure.toss.payments

import com.fasterxml.jackson.databind.ObjectMapper
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
@ConditionalOnProperty(prefix = "toss.payments", value = ["enabled"], havingValue = "true")
class TossPaymentsAutoConfiguration {

    @AutoConfiguration
    @ConditionalOnProperty(prefix = "toss.payments.webhook", value = ["enabled"], havingValue = "true")
    class TossPaymentsWebMvcConfigurer {

        @Bean
        @ConditionalOnMissingBean
        fun tossPaymentsWebhookController(applicationEventPublisher: ApplicationEventPublisher): TossPaymentsWebhookController {
            return TossPaymentsWebhookController(applicationEventPublisher)
        }
    }

    @Bean
    @ConditionalOnMissingBean
    fun tossPaymentsService(
        objectMapper: ObjectMapper,
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
