package app.com.service.interpreter

import app.com.domain.{ClientOrder, _}
import app.com.main.App.common.{FilePath, Time}
import app.com.service.MinAvgWaitingTimeModel

trait MinAvgWaitingTimeModelInterpreter extends MinAvgWaitingTimeModel with InputReaderInterpreter with ClientOrderParserInterpreter {

  override def clientOrders: FilePath => Either[Error, ClientOrder] = (filePath: FilePath) =>
    for{
      lines <- InputReaderInterpreter.read(filePath)
      clientOrder <- ClientOrderParserInterpreter.getClientOrders(lines)
    } yield clientOrder

  override def orders: ClientOrder => Either[Error, List[Order]] = (clientOrder: ClientOrder) => {
    Orders(List.tabulate(clientOrder.customers)(v =>
      Order(v+1,
        clientOrder.orderDetails(v).orderTime,
        Pizza(clientOrder.orderDetails(v).cookingTime),
        Customer(v+1))))
  }

  override def execute: List[Order] => Result = (orders: List[Order]) => {
    val orderList = orders.sortBy(_.pizza.cookingTime)
    def iterate(list: List[Order], beginingList: List[Order], result: List[Time]): List[Time] = {
      list match {
        case Nil => result
        case h :: t =>  {
          val waitingTimeSum = beginingList.foldLeft(0){(a,e) => a + e.pizza.cookingTime} + h.pizza.cookingTime
          val waitingTime = waitingTimeSum - h.orderingTime
          iterate(t, beginingList :+ h, result :+ waitingTime)
        }
      }
    }
    val waitingTimeList = iterate(orderList, Nil, Nil)
    Result(waitingTimeList.sum / waitingTimeList.size, orderList)
  }
}

object MinAvgWaitingTimeModelInterpreter extends MinAvgWaitingTimeModelInterpreter