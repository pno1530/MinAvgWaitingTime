package app.com.domain

import org.specs2.mutable.Specification

class OrdersTest extends Specification {
  "Orders " should {
    "return valid orders" in {
      val ordersList = List(Order(1,2, Pizza(1), Customer(1)), Order( 2, 3, Pizza(2), Customer(2)))
      val expected = Right(List(Order(1,2,Pizza(1),Customer(1)), Order(2,3,Pizza(2),Customer(2))))

      val actual = Orders(ordersList)

      actual mustEqual expected
    }

    "return `InvalidOrderIds` for invalid order-ids" in {
      val ordersList = List(Order(1,2, Pizza(1), Customer(1)), Order( 1, 3, Pizza(2), Customer(2)))
      val expected = Left(InvalidOrderIds)

      val actual = Orders(ordersList)

      actual mustEqual expected
    }

    "return `InvalidInvalidTimes` for invalid order-time" in {
      val ordersList = List(Order(1,-2, Pizza(1), Customer(1)), Order( 2, 3, Pizza(2), Customer(2)))
      val expected = Left(InvalidTimes)

      val actual = Orders(ordersList)

      actual mustEqual expected
    }

    "return `InvalidInvalidTimes` for invalid pizza cooking-time" in {
      val ordersList = List(Order(1,2, Pizza(1), Customer(1)), Order( 2, 3, Pizza(-2), Customer(2)))
      val expected = Left(InvalidTimes)

      val actual = Orders(ordersList)

      actual mustEqual expected
    }

    "return `InvalidInvalidCustomerId` for invalid pizza cooking-time" in {
      val ordersList = List(Order(1,2, Pizza(1), Customer(-1)), Order( 2, 3, Pizza(2), Customer(2)))
      val expected = Left(InvalidCustomerId)

      val actual = Orders(ordersList)

      actual mustEqual expected
    }
  }
}
