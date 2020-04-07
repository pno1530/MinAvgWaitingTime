package app.com.main

import app.com.service.interpreter.MinAvgWaitingTimeModelInterpreter._

object App {
  object common{
    type Time = Int
    type CustomerId = Int
    type OrderId = Int
    type MinAvgWaitingTime = Int
    type FilePath = String
  }

  def main(args: Array[String]): Unit = {
    val inputPath = "test/resources/Input.txt"
    println("hi---"+ process(inputPath))
  }
}