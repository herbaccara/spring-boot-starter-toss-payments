package herbaccara.toss.payments.form.payment

enum class Method(val text: String) {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    MOBILE_PHONE("휴대폰"),
    TRANSFER("계좌이체"),
    CULTURE_GIFT_CERTIFICATE("문화상품권"),
    BOOK_GIFT_CERTIFICATE("도서문화상품권"),
    GAME_GIFT_CERTIFICATE("게임문화상품권");
}
