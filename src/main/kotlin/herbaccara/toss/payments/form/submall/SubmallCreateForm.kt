package herbaccara.toss.payments.form.submall

import herbaccara.toss.payments.model.submall.Type

data class SubmallCreateForm(
    val subMallId: String,
    val account: Account,
    val type: Type,
    val companyName: String? = null,
    val representativeName: String? = null,
    val businessNumber: String? = null,
    val metadata: Map<String, Any>? = null
)
