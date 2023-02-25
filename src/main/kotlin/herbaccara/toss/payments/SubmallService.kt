package herbaccara.toss.payments

import com.fasterxml.jackson.databind.JsonNode
import herbaccara.toss.payments.form.submall.SubMallSettlementCreateForm
import herbaccara.toss.payments.form.submall.SubmallCreateForm
import herbaccara.toss.payments.form.submall.SubmallUpdateForm
import herbaccara.toss.payments.model.submall.Payout
import herbaccara.toss.payments.model.submall.Submall
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForObject
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SubmallService(
    private val restTemplate: RestTemplate
) {
    /***
     * 서브몰 등록
     * [...](https://docs.tosspayments.com/reference#%EC%84%9C%EB%B8%8C%EB%AA%B0-%EB%93%B1%EB%A1%9D)
     *
     * @param form
     * @return
     */
    fun create(form: SubmallCreateForm): Submall {
        val uri = "/v1/payouts/sub-malls"
        return restTemplate.postForObject(uri, form)
    }

    /***
     * 서브몰 조회
     * [...](https://docs.tosspayments.com/reference#%EC%84%9C%EB%B8%8C%EB%AA%B0-%EC%A1%B0%ED%9A%8C)
     *
     * @return
     */
    fun list(): List<Submall> {
        val uri = "/v1/payouts/sub-malls"
        return restTemplate.getForObject<Array<Submall>>(uri).toList()
    }

    /***
     * 서브몰 수정
     * [...](https://docs.tosspayments.com/reference#%EC%84%9C%EB%B8%8C%EB%AA%B0-%EC%88%98%EC%A0%95)
     *
     * @param subMallId
     * @param form
     * @return
     */
    fun update(subMallId: String, form: SubmallUpdateForm): Submall {
        val uri = "/v1/payouts/sub-malls/$subMallId"
        return restTemplate.postForObject(uri, form)
    }

    /***
     * 서브몰 삭제
     * [...](https://docs.tosspayments.com/reference#%EC%84%9C%EB%B8%8C%EB%AA%B0-%EC%82%AD%EC%A0%9C)
     *
     * @param subMallId
     * @return
     */
    fun delete(subMallId: String): String {
        val uri = "/v1/payouts/sub-malls/$subMallId/delete"
        return restTemplate.postForObject(uri, null)
    }

    /***
     * 지급 가능한 잔액 조회
     * [...](https://docs.tosspayments.com/reference#%EC%A7%80%EA%B8%89-%EA%B0%80%EB%8A%A5%ED%95%9C-%EC%9E%94%EC%95%A1-%EC%A1%B0%ED%9A%8C)
     *
     * @return
     */
    fun settlementBalance(): Long {
        val uri = "/v1/payouts/sub-malls/settlements/balance"
        val json: JsonNode = restTemplate.getForObject(uri)
        return json["balance"].asLong()
    }

    /***
     * 지급대행 요청
     * [...](https://docs.tosspayments.com/reference#%EC%A7%80%EA%B8%89%EB%8C%80%ED%96%89-%EC%9A%94%EC%B2%AD)
     *
     * @param form
     * @return
     */
    fun settlementCreate(vararg form: SubMallSettlementCreateForm): List<Payout> {
        val uri = "/v1/payouts/sub-malls/settlements"
        return restTemplate.postForObject(uri, form)
    }

    /***
     * 지급대행 단건 조회
     * [...](https://docs.tosspayments.com/reference#%EC%A7%80%EA%B8%89%EB%8C%80%ED%96%89-%EB%8B%A8%EA%B1%B4-%EC%A1%B0%ED%9A%8C)
     *
     * @param payoutKey
     * @return
     */
    fun settlement(payoutKey: String): Payout {
        val uri = "/v1/payouts/sub-malls/settlements/$payoutKey"
        return restTemplate.getForObject(uri)
    }

    /***
     * 지급대행 조회
     * [...](https://docs.tosspayments.com/reference#%EC%A7%80%EA%B8%89%EB%8C%80%ED%96%89-%EC%9A%94%EC%B2%AD)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    fun settlements(startDate: LocalDate, endDate: LocalDate): List<Payout> {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val params = LinkedMultiValueMap<String, String>().apply {
            add("startDate", dateFormatter.format(startDate))
            add("endDate", dateFormatter.format(endDate))
        }

        val uri = UriComponentsBuilder
            .fromUriString("/v1/payouts/sub-malls/settlements")
            .queryParams(params)
            .toUriString()

        return restTemplate.getForObject<Array<Payout>>(uri).toList()
    }
}
