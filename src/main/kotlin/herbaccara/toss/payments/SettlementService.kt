package herbaccara.toss.payments

import com.fasterxml.jackson.databind.JsonNode
import herbaccara.toss.payments.form.settlement.SettlementListForm
import herbaccara.toss.payments.model.settlement.Settlement
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForObject
import org.springframework.web.util.UriComponentsBuilder
import java.time.format.DateTimeFormatter
import kotlin.collections.List

class SettlementService(
    private val restTemplate: RestTemplate
) {
    /***
     * 정산 조회
     * [...](https://docs.tosspayments.com/reference#%EC%A0%95%EC%82%B0-%EC%A1%B0%ED%9A%8C)
     *
     * @param form
     * @return
     */
    fun list(form: SettlementListForm): List<Settlement> {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val params = LinkedMultiValueMap<String, String>().apply {
            add("startDate", dateFormatter.format(form.startDate))
            add("endDate", dateFormatter.format(form.endDate))
            if (form.dateType != null) {
                add("dateType", form.dateType.toString())
            }
            if (form.page != null) {
                add("page", form.page.toString())
            }
            if (form.size != null) {
                add("size", form.size.toString())
            }
        }

        val uri = UriComponentsBuilder
            .fromUriString("/v1/settlements")
            .queryParams(params)
            .toUriString()

        return restTemplate.getForObject<Array<Settlement>>(uri).toList()
    }

    /***
     * 수동 정산 요청
     * [...](https://docs.tosspayments.com/reference#%EC%88%98%EB%8F%99-%EC%A0%95%EC%82%B0-%EC%9A%94%EC%B2%AD)
     *
     * @param paymentKey
     * @return
     */
    fun request(paymentKey: String): Boolean {
        val uri = "/v1/settlements"
        val form = mapOf("paymentKey" to paymentKey)
        val jsonNode: JsonNode = restTemplate.postForObject(uri, form)
        return jsonNode["result"].asBoolean()
    }
}
