package app.com.service.interpreter

import app.com.domain._
import app.com.service.ClientOrderParser

import scala.util.Try

trait ClientOrderParserInterpreter extends ClientOrderParser[List[String], ClientOrder]{

  override def getClientOrders(lines: List[String]) = {
    for{
      _ <- validateCustomersFile(lines)
      _ <- validateCustomersCount(lines)
      orders <- createOrders(lines)
    } yield orders
  }

  private def validateCustomersFile = (lines: List[String]) =>
    if(lines.length > 0) Right(lines)
    else Left(InvalidFile)

  private def validateCustomersCount: List[String] => Either[Error, List[String]] = (lines: List[String]) =>
      Try(lines.head.toInt).fold(_ => Left(InvalidCustomersCountSyntax),
        count => {
          if(lines.tail.size == count) Right(lines)
          else Left(InvalidCustomersCount)
        } )

  private def createOrders: List[String] => Either[Error, ClientOrder] = (lines: List[String]) => {
    for{
      orderDetails <- sequence(lines.tail.map(createOrder))
      clientOrder <- ClientOrder(lines.head.toInt, orderDetails)
    } yield clientOrder
  }

  private def createOrder(string: String): Either[Error, OrderDetail] = {
    Try(string.split(" ").toList.map(_.toInt)).fold(
      _ => Left(InvalidInputSplit),
      t => {
        if(t.size == 2) Right(OrderDetail(t(0), t(1)))
        else Left(InvalidOrderDetailValues)
      })
  }

  private def sequence[A, B](l: List[Either[A, B]]): Either[A, List[B]] = {
    l.foldLeft(Right(Nil): Either[A, List[B]] ){(a,e) => for(es <- e; as <- a) yield as :+ es}
  }
}

object ClientOrderParserInterpreter extends ClientOrderParserInterpreter