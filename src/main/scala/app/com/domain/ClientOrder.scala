package app.com.domain
import app.com.main.App.common.Time

case class ClientOrder private(customers: Int, orderDetails: List[OrderDetail])
case class OrderDetail private(orderTime: Time, cookingTime: Time)

object ClientOrder{

  def apply(customers: Int, orderDetails: List[OrderDetail]) = for {
    _ <- validateCustomers(customers, orderDetails.size)
    _ <- validateOrders(orderDetails)
  } yield new ClientOrder(customers, orderDetails)

  def validateCustomers: (Time, Time) => Either[Error, Time] = (customers: Int, orderDetails: Int) => {
    if(customers == orderDetails) Right(customers)
    else Left(InvalidCustomersCount)
  }

  def validateOrders: List[OrderDetail] => Either[Error, List[OrderDetail]] = (orderDetails: List[OrderDetail]) => {
    if(orderDetails.count(o => (o.cookingTime < 0) || (o.orderTime < 0)) <= 0) Right(orderDetails)
    else Left(InvalidOrder)
  }
}