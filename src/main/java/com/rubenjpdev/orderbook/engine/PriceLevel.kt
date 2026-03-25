package main.java.com.rubenjpdev.orderbook.engine

import main.java.com.rubenjpdev.orderbook.model.Order
import main.java.com.rubenjpdev.orderbook.model.Quantity

class PriceLevel {
    private val queue = ArrayDeque<Order>()
    private var currentVolume: Long = 0L

    val totalVolume: Quantity
        get() = Quantity(currentVolume)

    val isEmpty: Boolean
        get() = queue.isEmpty()

    fun addOrder(order: Order) {
        queue.addLast(order)
        currentVolume += order.quantity.value
    }

    fun popNextOrder(): Order? {
        val order = queue.removeFirstOrNull() ?: return null
        currentVolume -= order.quantity.value
        return order
    }

    fun peekNextOrder(): Order? {
        return queue.firstOrNull()
    }

    fun removeOrder(orderId: Long): Boolean{
        val iterator = queue.iterator()
        while (iterator.hasNext()) {
            val order = iterator.next()
            if (order.id == orderId) {
                iterator.remove()
                currentVolume -= order.quantity.value
                return true
            }
        }
        return false
    }
}