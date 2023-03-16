package herbaccara.toss.payments.model.brandpay

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test

class BrandPayCardTest {

    @Test
    fun test() {
        val json = """
            {"selectedMethodId":"c_pdA8d1Avq5XBA3o1","cards":[{"id":"c_pdA8d1Avq5XBA3o1","methodKey":"ced9ojO5qEvKma60RZblrqQljNdGg6rwzYWBn14MXAPGg7pDJ","cardName":"현대백화점카드","alias":"","cardNumber":"11111111****111*","acquirerCode":"61","issuerCode":"33","ownerType":"개인","cardType":"신용","installmentMinimumAmount":10000,"registeredAt":"2022-09-28T14:42:31+09:00","status":"ENABLED","icon":"icn-bank-square-hyundaicard","iconUrl":"https://static.toss.im/icons/png/4x/icn-bank-square-hyundaicard.png","color":{"background":"#333D4B","text":"#FFFFFF"},"cardImgUrl":"","cardProductCode":""}],"accounts":[],"isIdentified":true}
        """.trimIndent()

        val mapper = jacksonObjectMapper().apply {
            findAndRegisterModules()
        }

        val readValue = mapper.readValue<BrandPayMethod>(json)
        println(readValue)
    }
}
