package app.com.domain
import app.com.main.App.common.{OrderId, Time}

case class Order(orderId: OrderId, orderingTime: Time, pizza: Pizza, customer: Customer)

object Orders{

  def apply(orders : List[Order]) = for {
    _ <- validateIds(orders.map(_.orderId))
    _ <- validateCustomerIds(orders)
    _ <- validateTimes(orders)
  } yield orders

  private def validateIds: List[OrderId] => Either[Error, List[OrderId]] = (ids: List[OrderId]) => {
    if(ids.count(_ < 0) > 0) Left(InvalidOrderIdsValue)
    else if(ids.distinct.size != ids.size) Left(InvalidOrderIds)
    else Right(ids)
  }

  private def validateTimes = (orders: List[Order]) => {
    if(orders.count(ordr => ordr.orderingTime < 0 || ordr.pizza.cookingTime < 0) > 0) Left(InvalidTimes)
    else Right(orders)
  }

  private def validateCustomerIds = (orders: List[Order]) => {
    if(orders.count(ordr => ordr.customer.id < 0) > 0) Left(InvalidCustomerId)
    else Right(orders)
  }
}