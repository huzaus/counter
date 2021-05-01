package com.shuzau.counter.domain.entity

import org.scalacheck.Gen

trait EntityGen {

  val eventType: Gen[EventType] = Gen.identifier.map(value => EventType(value))

  val count: Gen[Long] = Gen.choose[Long](1, 1000)

  val eventTypeCount: Gen[(EventType, Long)] = for {
    eventType <- eventType
    count <- count
  } yield (eventType, count)

  val state: Gen[State] =
    Gen.listOf(eventTypeCount)
      .map(_.toMap)
      .map(State(_))
}

object EntityGen extends EntityGen
