package app.com.domain

import app.com.main.App.common.MinAvgWaitingTime

case class Result(minAvgWaitingTime: MinAvgWaitingTime, orders: List[Order])
