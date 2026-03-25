package main.java.com.rubenjpdev.orderbook.model

enum class Side {BID,ASK}

@JvmInline
value class Price(val value: Long) //done to bypass boxing ang get primitive performance
@JvmInline
value class Quantity(val value: Long)//idem

data class Order(
    val id: Long,
    val asset: String,
    val side: Side,
    val priceCents: Price,
    val quantity: Quantity,
    val timestamp: Long = System.nanoTime()
)

data class Trade(
    val tradeId: Long,
    val buyOrderId: Long,
    val sellOrderId: Long,
    val executionPrice: Price,
    val executedQuantity: Quantity,
    val timestamp: Long = System.nanoTime()
)
