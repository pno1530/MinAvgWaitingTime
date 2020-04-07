package app.com.service

import app.com.main.App.common.FilePath
import app.com.domain.Error

trait InputReader[A] {
  def read(filePath: FilePath): Either[Error, A]
}


