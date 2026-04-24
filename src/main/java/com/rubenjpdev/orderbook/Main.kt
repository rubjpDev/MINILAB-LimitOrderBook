package main.java.com.rubenjpdev.orderbook

import main.java.com.rubenjpdev.orderbook.engine.OrderBook
import main.java.com.rubenjpdev.orderbook.model.Order
import main.java.com.rubenjpdev.orderbook.model.Price
import main.java.com.rubenjpdev.orderbook.model.Quantity
import main.java.com.rubenjpdev.orderbook.model.Side

fun main() {
    println("=== INICIANDO MOTOR DE EMPAREJAMIENTO (Activo: AAPL) ===")
    val orderBook = OrderBook("AAPL")

    // 1. INYECTANDO LIQUIDEZ (Market Makers)
    println("\n[1] Inyectando liquidez pasiva...")

    // Vendedores (Asks)
    orderBook.addOrder(Order(id = 1, asset = "AAPL", side = Side.ASK, priceCents = Price(102), quantity = Quantity(100)))
    orderBook.addOrder(Order(id = 2, asset = "AAPL", side = Side.ASK, priceCents = Price(101), quantity = Quantity(50)))

    // Compradores (Bids)
    orderBook.addOrder(Order(id = 3, asset = "AAPL", side = Side.BID, priceCents = Price(98), quantity = Quantity(200)))
    orderBook.addOrder(Order(id = 4, asset = "AAPL", side = Side.BID, priceCents = Price(99), quantity = Quantity(75)))

    println("Spread actual -> BID: ${orderBook.getBestBid()} | ASK: ${orderBook.getBestAsk()}")

    // 2. ORDEN AGRESIVA (cross)
    println("\n[2] Entra orden agresiva: COMPRAR 60 @ 101")
    val aggressiveOrder = Order(id = 5, asset = "AAPL", side = Side.BID, priceCents = Price(101), quantity = Quantity(60))

    val trades = orderBook.process(aggressiveOrder)

    // 3. TELEMETRÍA (Resultados)
    println("\n[3] Resultados del Cruce:")
    println("Trades generados: ${trades.size}")
    trades.forEach { trade ->
        println(" -> Ejecutado: ${trade.executedQuantity.value} acciones a ${trade.executionPrice.value}$")
    }

    println("\n[4] Nuevo estado del libro tras el cruce:")
    println("Spread actual -> BID: ${orderBook.getBestBid()} | ASK: ${orderBook.getBestAsk()}")

    // Validar el cruce parcial (Partial Fill)
    val remainingVol = orderBook.bids[101L]?.totalVolume?.value
    println("Volumen restante en el nivel BID 101: $remainingVol")
}