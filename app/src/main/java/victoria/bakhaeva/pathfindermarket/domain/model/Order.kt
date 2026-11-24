package victoria.bakhaeva.pathfindermarket.domain.model

enum class Order {
    PRICE_ASC,
    PRICE_DESC,
    NAME_ASC,
    NAME_DESC,
    ;

    companion object {
        val DEFAULT = PRICE_DESC
    }
}
