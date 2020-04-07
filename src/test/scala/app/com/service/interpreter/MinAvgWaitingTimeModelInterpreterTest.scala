package app.com.service.interpreter

import app.com.domain._
import org.specs2.mutable.Specification

class MinAvgWaitingTimeModelInterpreterTest extends Specification {
  "MinAvgWaitingTimeModelInterpreter" should {
    "return executed result" in {
      val order1 = Order(1, 0, Pizza(3), Customer(1))
      val order2 = Order(2, 4, Pizza(9), Customer(1))
      val order3 = Order(3, 3, Pizza(5), Customer(1))
      val orders = List(order1, order2, order3)
      val expected = Result(7,List(Order(1,0,Pizza(3),Customer(1)), Order(3,3,Pizza(5),Customer(1)), Order(2,4,Pizza(9),Customer(1))))

      val actual = MinAvgWaitingTimeModelInterpreter.execute(orders)

      actual mustEqual expected
    }

    "return Orders for clientOrder " in {
      val clientOrder = ClientOrder(3, List(OrderDetail(0,3), OrderDetail(2,6), OrderDetail(1,9))).right.get
      val expected = Right(List(Order(1,0,Pizza(3),Customer(1)), Order(2,2,Pizza(6),Customer(2)), Order(3,1,Pizza(9),Customer(3))))

      val actual = MinAvgWaitingTimeModelInterpreter.orders(clientOrder)

      actual mustEqual expected
    }

    "return `InvalidTimes` for invalidOrders" in {
      val clientOrder = ClientOrder(3, List(OrderDetail(-1,3), OrderDetail(2,6), OrderDetail(1,9))).right.get
      val expected = Left(InvalidTimes)

      val actual = MinAvgWaitingTimeModelInterpreter.orders(clientOrder)

      actual mustEqual expected
    }

    "return clientOrders for filePath" in {
      val filePath = "src/test/resources/Input.txt"
      val expected = ClientOrder(3,List(OrderDetail(0,3), OrderDetail(1,9), OrderDetail(2,5)))

      val actual = MinAvgWaitingTimeModelInterpreter.clientOrders(filePath)

      actual mustEqual expected
    }

    "return `InvalidFilePath` for Invalid filePath" in {
      val filePath = "src/test/resources"
      val expected = Left(InvalidFilePath)

      val actual = MinAvgWaitingTimeModelInterpreter.clientOrders(filePath)

      actual mustEqual expected
    }

    "return `InvalidCustomersCountSyntax` for Invalid syntax" in {
      val filePath = "src/test/resources/InvalidCustomerCountSyntax.txt"
      val expected = Left(InvalidCustomersCountSyntax)

      val actual = MinAvgWaitingTimeModelInterpreter.clientOrders(filePath)

      actual mustEqual expected
    }

    "return `InvalidCustomersCount` for Invalid Customers Count" in {
      val filePath = "src/test/resources/InvalidCustomerCount.txt"
      val expected = Left(InvalidCustomersCount)

      val actual = MinAvgWaitingTimeModelInterpreter.clientOrders(filePath)

      actual mustEqual expected
    }

    "return `InvalidCustomersCount` for Invalid Order" in {
      val filePath = "src/test/resources/InvalidOrder.txt"
      val expected = Left(InvalidInputSplit)

      val actual = MinAvgWaitingTimeModelInterpreter.clientOrders(filePath)

      actual mustEqual expected
    }
  }
}
