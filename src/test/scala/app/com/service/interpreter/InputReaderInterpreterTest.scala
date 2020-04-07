package app.com.service.interpreter

import app.com.domain.InvalidFilePath
import org.specs2.mutable.Specification

class InputReaderInterpreterTest extends Specification {
  "InputReaderInterpreter" should {
    "return lines" in {
      val filePath = "src/test/resources/Input.txt"
      val expected = Right(List("3", "0 3", "1 9", "2 5"))

      val actual = InputReaderInterpreter.read(filePath)

      actual mustEqual expected
    }

    "return `InvalidFilePath` for invalid path" in {
      val filePath = "src/test/resources"
      val expected = Left(InvalidFilePath)

      val actual = InputReaderInterpreter.read(filePath)

      actual mustEqual expected
    }
  }
}
