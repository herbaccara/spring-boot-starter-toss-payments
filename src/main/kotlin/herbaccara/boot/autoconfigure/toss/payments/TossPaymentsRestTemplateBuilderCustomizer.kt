package herbaccara.boot.autoconfigure.toss.payments

import org.springframework.boot.web.client.RestTemplateBuilder

interface TossPaymentsRestTemplateBuilderCustomizer {

    fun customize(restTemplateBuilder: RestTemplateBuilder)
}
