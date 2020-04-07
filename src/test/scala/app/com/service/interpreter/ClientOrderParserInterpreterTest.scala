package app.com.service.interpreter

import app.com.domain.{ClientOrder, InvalidCustomersCount, InvalidCustomersCountSyntax, InvalidFile, InvalidInputSplit, InvalidOrder, OrderDetail}
import org.specs2.mutable.Specification

class ClientOrderParserInterpreterTest extends Specification {
  "ClientOrderParserInterpreter" should {
    "return client orders" in {
      val lines = List("3", "0 3", "1 9", "2 5")
      val expected = ClientOrder(3,List(OrderDetail(0,3), OrderDetail(1,9), OrderDetail(2,5)))

      val actual = ClientOrderParserInterpreter.getClientOrders(lines)

      actual mustEqual expected
    }

    "return `InvalidFile` for empty data" in {
      val lines = List()
      val expected = Left(InvalidFile)

      val actual = ClientOrderParserInterpreter.getClientOrders(lines)

      actual mustEqual expected
    }

    "return `InvalidCustomersCountSyntax` for invalid syntax" in {
      val lines = List("a")
      val expected = Left(InvalidCustomersCountSyntax)

      val actual = ClientOrderParserInterpreter.getClientOrders(lines)

      actual mustEqual expected
    }

    "return `InvalidCustomersCount` for invalid customers count" in {
      val lines = List("3", "0 3", "1 9")
      val expected = Left(InvalidCustomersCount)

      val actual = ClientOrderParserInterpreter.getClientOrders(lines)

      actual mustEqual expected
    }

    "return `InvalidInputSplit` for invalid time synatx" in {
      val lines = List("3", "'1' 3", "1 9", "1 9")
      val expected = Left(InvalidInputSplit)

      val actual = ClientOrderParserInterpreter.getClientOrders(lines)

      actual mustEqual expected
    }

    "return `InvalidOrder` for invalid time" in {
      val lines = List("3", "-1 3", "1 9", "1 9")
      val expected = Left(InvalidOrder)

      val actual = ClientOrderParserInterpreter.getClientOrders(lines)

      actual mustEqual expected
    }
  }
}
