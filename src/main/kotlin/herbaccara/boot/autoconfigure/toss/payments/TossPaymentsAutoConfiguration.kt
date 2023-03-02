package herbaccara.boot.autoconfigure.toss.payments

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import herbaccara.toss.payments.TossPaymentsService
import herbaccara.toss.payments.TossPaymentsWebhookController
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import java.nio.charset.StandardCharsets
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
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            findAndRegisterModules()
        }
    }

    @Bean
    fun tossPaymentsService(
        objectMapper: ObjectMapper,
        properties: TossPaymentsProperties,
        customizers: List<TossPaymentsRestTemplateBuilderCustomizer>,
        interceptors: List<TossPaymentsClientHttpRequestInterceptor>
    ): TossPaymentsService {
        if (properties.clientSecret.isEmpty()) throw NullPointerException()

        // 인증 : https://docs.tosspayments.com/guides/using-api/authorization#%EC%9D%B8%EC%A6%9D
        val authorization = Base64.getEncoder().encodeToString("${properties.clientSecret}:".toByteArray())

        val restTemplate = RestTemplateBuilder()
            .rootUri(properties.rootUri)
            .setReadTimeout(properties.readTimeout)
            .additionalInterceptors(
                ClientHttpRequestInterceptor { request, body, execution ->
                    val headers = request.headers
                    headers.contentType = MediaType.APPLICATION_JSON
                    headers.setBasicAuth(authorization)
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

        return TossPaymentsService(restTemplate)
    }
}
