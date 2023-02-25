package herbaccara.toss.payments

import herbaccara.toss.payments.form.cashreceipt.CashReceiptListForm
import herbaccara.toss.payments.form.cashreceipt.CashReceiptRequestForm
import herbaccara.toss.payments.model.cashreceipt.CashReceipt
import herbaccara.toss.payments.model.cashreceipt.CashReceiptList
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForObject
import org.springframework.web.util.UriComponentsBuilder
import java.time.format.DateTimeFormatter

class CashReceiptService(
    private val restTemplate: RestTemplate
) {

    /***
     * 현금영수증 발급 요청
     * [...](https://docs.tosspayments.com/reference#%ED%98%84%EA%B8%88%EC%98%81%EC%88%98%EC%A6%9D-%EB%B0%9C%EA%B8%89-%EC%9A%94%EC%B2%AD)
     *
     * @param form
     * @return
     */
    fun request(form: CashReceiptRequestForm): CashReceipt {
        val uri = "/v1/cash-receipt"
        return restTemplate.postForObject(uri, form)
    }

    /**
     * 현금영수증 발급 취소 요청
     * [...](https://docs.tosspayments.com/reference#%ED%98%84%EA%B8%88%EC%98%81%EC%88%98%EC%A6%9D-%EB%B0%9C%EA%B8%89-%EC%B7%A8%EC%86%8C-%EC%9A%94%EC%B2%AD)
     *
     * @param receiptKey
     * @return
     */
    fun cancel(receiptKey: String): CashReceipt {
        val uri = "/v1/cash-receipts/$receiptKey/cancel"
        return restTemplate.postForObject(uri, null)
    }

    /***
     * 현금영수증 조회
     * [...](https://docs.tosspayments.com/reference#%ED%98%84%EA%B8%88%EC%98%81%EC%88%98%EC%A6%9D-%EC%A1%B0%ED%9A%8C)
     *
     * @param form
     * @return
     */
    fun list(form: CashReceiptListForm): CashReceiptList {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val params = LinkedMultiValueMap<String, String>().apply {
            add("requestDate", dateFormatter.format(form.requestDate))
            if (form.cursor != null) {
                add("cursor", form.cursor.toString())
            }
            if (form.limit != null) {
                add("limit", form.limit.toString())
            }
        }

        val uri = UriComponentsBuilder
            .fromUriString("/v1/cash-receipts")
            .queryParams(params)
            .toUriString()

        return restTemplate.getForObject(uri)
    }
}
