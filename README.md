# spring-boot-starter-toss-payments
spring boot starter toss payments

토스페이먼츠 API 버전 2022-11-16 으로 작성되었습니다.

## How to
To get a Git project into your build:

*Step 1*. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

*Step 2*. Add the dependency

```groovy
dependencies {
    implementation 'com.github.herbaccara:spring-boot-starter-toss-payments:Tag'
}
```

## application.yml 설정
```yaml
toss:
  payments:
    enabled: true
    client-key: 클라이언트 키
    client-secret: 클라이언트 시크릿
    webhook:
      enabled: true
      path: /toss/payments/webhook
```

## 사용하기
```kotlin
class SpringComponent (
    private val tossPaymentsService: TossPaymentsService
)

```

## RestTemplate 커스텀 설정
아래 2개의 인터페이스를 상속받은 class 를 만들고 bean 으로 등록

```kotlin
interface TossPaymentsRestTemplateBuilderCustomizer {
    fun customize(restTemplateBuilder: RestTemplateBuilder)
}

interface TossPaymentsClientHttpRequestInterceptor : ClientHttpRequestInterceptor
```


## Webhook controller
toss.webhook.enabled 값이 true 인 경우 자동으로
[TossPaymentsWebhookController](src%2Fmain%2Fkotlin%2Fherbaccara%2Ftoss%2Fpayments%2FTossPaymentsWebhookController.kt) 
가 bean 으로 등록됩니다.

webhook event 는 applicationEventPublisher 에 즉시 publishEvent 됩니다.

직접 TossPaymentsWebhookController 를 상속받은 component 를 등록해서 사용 하실 수 있습니다.

```kotlin
@RestController
@RequestMapping("\${toss.payments.webhook.path:${TossPaymentsProperties.DEFAULT_WEBHOOK_PATH}}")
class TossPaymentsWebhookController(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    /***
     * 가상계좌 결제 상태를 알려주는 웹훅
     */
    @RequestMapping("/deposit-callback")
    fun onDepositCallback(depositCallback: DepositCallback) {
        applicationEventPublisher.publishEvent(depositCallback)
    }

    /***
     * 카드, 계좌이체, 휴대폰, 상품권 결제 상태를 알려주는 웹훅입니다.
     */
    @RequestMapping("/payment-status-changed")
    fun onPaymentStatusChanged(paymentStatusChangedEvent: Event<PaymentStatusChanged>) {
        applicationEventPublisher.publishEvent(paymentStatusChangedEvent)
    }

    /***
     * 지급대행 요청 상태가 COMPLETED, FAILED로 변경되면 웹훅이 전송됩니다. 자세한 상태 설명은 status에서 확인하세요.
     */
    @RequestMapping("/payout-status-changed")
    fun onPayoutStatusChanged(payoutStatusChangedEvent: Event<PayoutStatusChanged>) {
        applicationEventPublisher.publishEvent(payoutStatusChangedEvent)
    }

    /***
     * 브랜드페이 고객의 결제 수단이 변경되면 웹훅이 전송됩니다.
     */
    @RequestMapping("/method-updated")
    fun onMethodUpdated(methodUpdatedEvent: Event<MethodUpdated>) {
        applicationEventPublisher.publishEvent(methodUpdatedEvent)
    }

    /***
     * 브랜드페이 고객의 상태가 변경되면 웹훅이 전송됩니다.
     */
    @RequestMapping("/customer-status-changed")
    fun onCustomerStatusChanged(customerStatusChangedEvent: Event<CustomerStatusChanged>) {
        applicationEventPublisher.publishEvent(customerStatusChangedEvent)
    }
}

```

## Webhook event

[AbstractTossPaymentsEventListener.kt](src%2Fmain%2Fkotlin%2Fherbaccara%2Ftoss%2Fpayments%2FAbstractTossPaymentsEventListener.kt)
를 상속받은 component 를 bean 으로 등록해서 webhook event 를 처리 할 수 있습니다.

```kotlin
abstract class AbstractTossPaymentsEventListener {

    @EventListener
    abstract fun onDepositCallback(depositCallback: DepositCallback)

    @EventListener
    abstract fun onPaymentStatusChanged(paymentStatusChangedEvent: Event<PaymentStatusChanged>)

    @EventListener
    abstract fun onPayoutStatusChanged(payoutStatusChangedEvent: Event<PayoutStatusChanged>)

    @EventListener
    abstract fun onMethodUpdated(methodUpdatedEvent: Event<MethodUpdated>)

    @EventListener
    abstract fun onCustomerStatusChanged(customerStatusChangedEvent: Event<CustomerStatusChanged>)
}

```
