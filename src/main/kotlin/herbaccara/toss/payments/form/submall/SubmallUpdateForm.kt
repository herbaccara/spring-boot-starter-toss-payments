package herbaccara.toss.payments.form.submall

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class SubmallUpdateForm(
    val account: Account,
    val companyName: String? = null,
    val representativeName: String? = null,
    val businessNumber: String? = null,
    val metadata: Map<String, Any> = mapOf()
)
