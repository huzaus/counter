package com.shuzau.counter.domain.entity

final case class State(map: Map[EventType, Long])

object State {
  val empty: State = State(Map.empty)
}
