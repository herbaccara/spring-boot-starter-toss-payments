package herbaccara.toss.payments

import com.fasterxml.jackson.databind.JsonNode
import herbaccara.toss.payments.form.brandpay.BrandPayAuthorizationAccessTokenForm
import herbaccara.toss.payments.form.brandpay.BrandPayTermAgreeForm
import herbaccara.toss.payments.model.brandpay.*
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForObject

class BrandPayService(
    private val restTemplate: RestTemplate
) {
    fun terms(customerKey: String, scopes: List<Scope>): List<Term> {
        val uri = "/v1/brandpay/terms?customerKey=$customerKey&${scopes.joinToString("&") { "&scope=$it" }}"
        return restTemplate.getForObject<Array<Term>>(uri).toList()
    }

    fun termAgree(form: BrandPayTermAgreeForm): String {
        val uri = "/v1/brandpay/terms/agree"
        val json = restTemplate.postForObject<JsonNode>(uri, form)
        return json["code"].asText()
    }

    fun authorizationAccessToken(form: BrandPayAuthorizationAccessTokenForm): Token {
        val uri = "/v1/brandpay/authorizations/access-token"
        return restTemplate.postForObject(uri, form)
    }

    // FIXME : JWT 용 restTemplate 를 주입 받아오도록 수정 하자...
    fun paymentMethodsByAccessToken(accessToken: String): BrandPayMethod {
        val headers = HttpHeaders().apply {
            setBearerAuth(accessToken)
        }
        val httpEntity = HttpEntity<Any>(headers)

        val uri = "/v1/brandpay/payments/methods"
        return restTemplate.exchange<BrandPayMethod>(uri, HttpMethod.GET, httpEntity).body!!
    }

    fun paymentMethodsBySecretKey(customerKey: String): BrandPayMethod {
        val uri = "/v1/brandpay/payments/methods/$customerKey"
        return restTemplate.getForObject(uri)
    }

    fun cardPromotion(): BrandPayCardPromotion {
        val uri = "/v1/brandpay/promotions/card"
        return restTemplate.getForObject(uri)
    }
}
