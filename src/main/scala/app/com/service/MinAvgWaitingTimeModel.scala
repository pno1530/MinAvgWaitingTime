package app.com.service

import app.com.domain.{ClientOrder, Order, Result, Error}
import app.com.main.App.common.FilePath

trait MinAvgWaitingTimeModel {

  def clientOrders: FilePath => Either[Error, ClientOrder]

  def orders: ClientOrder => Either[Error, List[Order]]

  def execute: List[Order] => Result

  def process(filePath: FilePath) = for{
    clientOrder <- clientOrders(filePath)
    orders <- orders(clientOrder)
  } yield execute(orders)

}
