package herbaccara.toss.payments

import herbaccara.toss.payments.form.transaction.TransactionListForm
import herbaccara.toss.payments.model.transaction.Transaction
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder
import java.time.format.DateTimeFormatter

class TransactionService(
    private val restTemplate: RestTemplate
) {

    /***
     * 거래 내역 조회
     * [...](https://docs.tosspayments.com/reference#%EA%B1%B0%EB%9E%98-%EC%A1%B0%ED%9A%8C)
     *
     * @param form
     * @return
     */
    fun list(form: TransactionListForm): List<Transaction> {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        val params = LinkedMultiValueMap<String, String>().apply {
            add("startDate", dateTimeFormatter.format(form.startDate))
            add("endDate", dateTimeFormatter.format(form.endDate))
            if (form.startingAfter != null) {
                add("startingAfter", form.startingAfter)
            }
            if (form.limit != null) {
                add("limit", form.limit.toString())
            }
        }

        val uri = UriComponentsBuilder
            .fromUriString("/v1/transactions")
            .queryParams(params)
            .toUriString()

        return restTemplate.getForObject<Array<Transaction>>(uri).toList()
    }
}
