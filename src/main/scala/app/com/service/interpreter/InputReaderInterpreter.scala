package app.com.service.interpreter

import app.com.domain._
import app.com.main.App.common.FilePath
import app.com.service.InputReader

import scala.io.Source
import scala.util.Try

trait InputReaderInterpreter extends InputReader[List[String]]{
  override def read(filePath: FilePath): Either[Error, List[String]] = {
    Try(Source.fromFile(filePath).getLines().toList)
      .fold(_ => Left(InvalidFilePath), lines => Right(lines))
  }
}

object InputReaderInterpreter extends InputReaderInterpreter

