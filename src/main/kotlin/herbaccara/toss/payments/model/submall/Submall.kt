package herbaccara.toss.payments.model.submall

data class Submall(
    val subMallId: String,
    val type: Type,
    val account: Account,
    val companyName: String? = null,
    val representativeName: String? = null,
    val businessNumber: String? = null,
    val metadata: Map<String, Any>? = null
) {
    init {
        if (type == Type.CORPORATE) {
            if (companyName == null) throw IllegalArgumentException()
            if (representativeName == null) throw IllegalArgumentException()
            if (businessNumber == null) throw IllegalArgumentException()
        }
    }
}
