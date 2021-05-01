package com.shuzau.counter.domain.out

import com.shuzau.counter.domain.entity.{EntityGen, State}
import com.shuzau.counter.domain.out.Storage.StorageService
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import zio.Runtime.default
import zio.{ULayer, ZEnv, ZIO}

class StorageSpec extends AnyFlatSpec
  with Matchers
  with ScalaCheckPropertyChecks {

  behavior of "Storage"

  val layer: ULayer[StorageService] = Storage.layer

  it should "return empty state" in {
    unsafeRun(Storage.get()) shouldBe State.empty
  }

  it should "return save and get state" in {
    forAll(EntityGen.state) { state =>
      val scenario = for {
        _ <- Storage.save(state)
        saved <- Storage.get()
      } yield saved

      unsafeRun(scenario) shouldBe state
    }
  }

  it should "return overwrite state" in {
    forAll(EntityGen.state, EntityGen.state) { case (init, update) =>
      val scenario = for {
        _ <- Storage.save(init)
        _ <- Storage.save(update)
        state <- Storage.get()
      } yield state

      unsafeRun(scenario) shouldBe update
    }
  }

  def unsafeRun[T](scenario: ZIO[ZEnv with StorageService, Nothing, T]): T = {
    default.unsafeRun(scenario.provideCustomLayer(layer))
  }
}
