package com.shuzau.counter.domain.in

import com.shuzau.counter.domain.entity.{EntityGen, State}
import com.shuzau.counter.domain.in.Counter.{CounterService, layer}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import zio.Runtime.default
import zio.{ZEnv, ZIO}

class CounterSpec extends AnyFlatSpec
  with Matchers
  with ScalaCheckPropertyChecks {

  behavior of "Storage"

  it should "return empty state" in {
    unsafeRun(Counter.state()) shouldBe State.empty
  }

  it should "process one event and save counter state" in {
    forAll(EntityGen.event) { event =>
      val scenario = for {
        _ <- Counter.process(List(event))
        state <- Counter.state()
      } yield state

      unsafeRun(scenario) shouldBe State(Map(event.eventType -> event.data.countWords()))
    }
  }

  def unsafeRun[T](scenario: ZIO[ZEnv with CounterService, Nothing, T]): T = {
    default.unsafeRun(scenario.provideCustomLayer(layer))
  }
}
