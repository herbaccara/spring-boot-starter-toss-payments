package herbaccara.boot.autoconfigure.toss.payments

import herbaccara.toss.payments.model.brandpay.Token

open class TossPaymentsAuthInterceptor {

    fun preHandle(code: String, customerKey: String) {
        // nothing
    }

    fun postHandle(code: String, customerKey: String, token: Token) {
        // nothing
    }

    fun afterCompletion(code: String, customerKey: String, token: Token?, e: Throwable?) {
        // nothing
    }
}
