package herbaccara.boot.autoconfigure.toss.payments

import org.springframework.context.annotation.Import
import java.lang.annotation.*

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(TossPaymentsAutoConfiguration::class)
annotation class EnableTossPayments
