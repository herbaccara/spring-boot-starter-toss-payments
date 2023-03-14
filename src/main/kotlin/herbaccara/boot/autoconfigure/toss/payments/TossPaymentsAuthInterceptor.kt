package herbaccara.boot.autoconfigure.toss.payments

import herbaccara.toss.payments.model.brandpay.Token

open class TossPaymentsAuthInterceptor {

    fun preHandle(code: String, customerKey: String) {
        // nothing
    }

    fun postHandle(token: Token) {
        // nothing
    }

    fun afterCompletion(e: Throwable?) {
        // nothing
    }
}
