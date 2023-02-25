package herbaccara.toss.payments

import herbaccara.toss.payments.form.billing.BillingApproveForm
import herbaccara.toss.payments.form.billing.BillingAuthorizationCardForm
import herbaccara.toss.payments.form.billing.BillingAuthorizationIssueForm
import herbaccara.toss.payments.model.billing.Billing
import herbaccara.toss.payments.model.payment.Payment
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject

class BillingService(
    private val restTemplate: RestTemplate
) {
    /***
     * customerKey로 카드 자동 결제 빌링키 발급 요청
     * [...](https://docs.tosspayments.com/reference#customerkey%EB%A1%9C-%EC%B9%B4%EB%93%9C-%EC%9E%90%EB%8F%99-%EA%B2%B0%EC%A0%9C-%EB%B9%8C%EB%A7%81%ED%82%A4-%EB%B0%9C%EA%B8%89-%EC%9A%94%EC%B2%AD)
     *
     * @param form
     * @return
     */
    fun authorizationCard(form: BillingAuthorizationCardForm): Billing {
        val uri = "/v1/billing/authorizations/card"
        return restTemplate.postForObject(uri, form)
    }

    /***
     * authKey로 카드 자동 결제 빌링키 발급 요청
     * [...](https://docs.tosspayments.com/reference#authkey%EB%A1%9C-%EC%B9%B4%EB%93%9C-%EC%9E%90%EB%8F%99-%EA%B2%B0%EC%A0%9C-%EB%B9%8C%EB%A7%81%ED%82%A4-%EB%B0%9C%EA%B8%89-%EC%9A%94%EC%B2%AD)
     *
     * @param form
     * @return
     */
    fun authorizationIssue(form: BillingAuthorizationIssueForm): Billing {
        val uri = "/v1/billing/authorizations/issue"
        return restTemplate.postForObject(uri, form)
    }

    /***
     * 카드 자동 결제 승인
     * [...](https://docs.tosspayments.com/reference#%EC%B9%B4%EB%93%9C-%EC%9E%90%EB%8F%99-%EA%B2%B0%EC%A0%9C-%EC%8A%B9%EC%9D%B8)
     *
     * @param billingKey
     * @param form
     * @return
     */
    fun approve(billingKey: String, form: BillingApproveForm): Payment {
        val uri = "/v1/billing/$billingKey"
        return restTemplate.postForObject(uri, form)
    }
}
