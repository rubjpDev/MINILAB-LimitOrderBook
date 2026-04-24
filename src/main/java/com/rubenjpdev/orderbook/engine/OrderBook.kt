package main.java.com.rubenjpdev.orderbook.engine

import main.java.com.rubenjpdev.orderbook.model.Order
import main.java.com.rubenjpdev.orderbook.model.Side
import java.util.TreeMap

class OrderBook(val asset: String) {
    internal val asks = TreeMap<Long, PriceLevel>();
    internal val bids = TreeMap<Long, PriceLevel>(compareByDescending { it });
    private val orderMap = HashMap<Long, Order>();

    fun getBestBid(): Long? = bids.firstEntry()?.key;
    fun getBestAsk(): Long? = asks.firstEntry()?.key;

    fun addOrder(order: Order){
        val targetTree = when(order.side){
            Side.BID -> bids
            Side.ASK -> asks
        }
        val priceKey = order.priceCents.value;
        val level = targetTree.getOrPut(priceKey){PriceLevel()}

        level.addOrder(order);
        orderMap[order.id] = order;
    }

    fun cancelOrder(orderId: Long): Boolean{
        val order = orderMap.remove(orderId) ?: return false;

        val targetTree = when(order.side){
            Side.BID -> bids
            Side.ASK -> asks
        }

        val priceKey = order.priceCents.value;

        val level = targetTree[priceKey]?: return false;

        val removed = level.removeOrder(orderId);

        if(level.isEmpty) targetTree.remove(priceKey);
        return removed;
    }
}