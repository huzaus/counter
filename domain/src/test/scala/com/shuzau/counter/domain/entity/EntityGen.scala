package com.shuzau.counter.domain.entity

import org.scalacheck.Gen

import java.time.Instant

trait EntityGen {

  val eventType: Gen[EventType] = Gen.identifier.map(value => EventType(value))

  val eventData: Gen[EventData] =
    Gen.listOf(Gen.oneOf(Gen.alphaChar, Gen.const("")))
      .map(_.mkString)
      .map(value => EventData(value))

  val event: Gen[Event] = for {
    eventType <- eventType
    eventData <- eventData
  } yield Event(eventType, eventData, Instant.now())

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
