package herbaccara.toss.payments

import herbaccara.boot.autoconfigure.toss.payments.TossPaymentsAuthInterceptor
import herbaccara.toss.payments.TossPaymentsWebhookController.Companion.DEFAULT_WEBHOOK_PATH
import herbaccara.toss.payments.form.brandpay.GrantType
import herbaccara.toss.payments.model.webhook.Event.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException

@RestController
@RequestMapping("\${toss.payments.auth.path:$DEFAULT_WEBHOOK_PATH}")
class TossPaymentsAuthController(
    private val tossPaymentsService: TossPaymentsService,
    private val tossPaymentsAuthInterceptor: TossPaymentsAuthInterceptor
) {
    companion object {
        const val DEFAULT_WEBHOOK_PATH = "/toss/payments/auth"
    }

    @GetMapping
    fun auth(
        @RequestParam("code") code: String,
        @RequestParam("customerKey") customerKey: String
    ): Map<String, Any> {
        // customerKey 가 신규 일 때는 GrantType 을 AuthorizationCode 로, 아니면 RefreshToken 로 호출을 해야 한다.
        // customerKey 기준으로 customer 정보 관련 API 가 없기 때문에 신규 고객인지 아닌지를 직접 관리 해야 한다.
        // GrantType 를 RefreshToken 로 해서 호출 후 403 에러가 떨어지면 AuthorizationCode 로 재호출 처리...

        tossPaymentsAuthInterceptor.preHandle(code, customerKey)

        val result = runCatching {
            try {
                tossPaymentsService.brandPayAuthorizationAccessToken(
                    customerKey,
                    GrantType.RefreshToken,
                    code
                )
            } catch (e: HttpClientErrorException.Forbidden) {
                tossPaymentsService.brandPayAuthorizationAccessToken(
                    customerKey,
                    GrantType.AuthorizationCode,
                    code
                )
            }
        }

        if (result.isSuccess) {
            val token = result.getOrNull()!!

            tossPaymentsAuthInterceptor.postHandle(token)
            tossPaymentsAuthInterceptor.afterCompletion(null)

            val (accessToken, refreshToken, tokenType, expiresIn) = token

            return mapOf(
                "accessToken" to accessToken,
                "tokenType" to tokenType,
                "expiresIn" to expiresIn
            )
        } else {
            val throwable = result.exceptionOrNull()!!
            tossPaymentsAuthInterceptor.afterCompletion(throwable)
            throw throwable
        }
    }
}
