package herbaccara.toss.payments.form.billing

data class BillingAuthorizationIssueForm(
    val authKey: String,
    val customerKey: String
)
