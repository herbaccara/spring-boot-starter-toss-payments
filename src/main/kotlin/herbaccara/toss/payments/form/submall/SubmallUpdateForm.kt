package herbaccara.toss.payments.form.submall

import com.fasterxml.jackson.annotation.JsonInclude
import herbaccara.toss.payments.model.submall.Type

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SubmallUpdateForm(
    val account: Account,
    val type: Type,
    val companyName: String? = null,
    val representativeName: String? = null,
    val businessNumber: String? = null,
    val metadata: Map<String, Any>? = null
)
