package app.com.service

import app.com.domain.Error
import app.com.main.App.common.FilePath

trait ClientOrderParser[A, B] {
  def getClientOrders(input: A): Either[Error, B]
}


