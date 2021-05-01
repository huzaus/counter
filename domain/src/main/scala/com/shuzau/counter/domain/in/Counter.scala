package com.shuzau.counter.domain.in

import com.shuzau.counter.domain.entity.{Event, State}
import com.shuzau.counter.domain.out.Storage
import zio.{Has, UIO, ULayer, ZIO, ZLayer}

object Counter {
  type CounterService = Has[Service]

  trait Service {
    def process(events: List[Event]): UIO[Unit]

    def state(): UIO[State]
  }

  def process(events: List[Event]): ZIO[CounterService, Nothing, Unit] =
    ZIO.accessM[CounterService](_.get[Service].process(events: List[Event]))

  def state(): ZIO[CounterService, Nothing, State] =
    ZIO.accessM[CounterService](_.get[Service].state())

  val layer: ULayer[CounterService] =
    Storage.layer >>> ZLayer.fromFunction(storage => new Service {
      override def process(events: List[Event]): UIO[Unit] = {
        storage.get.save(
          State(
            events.groupBy(_.eventType)
              .view
              .mapValues(_.map(_.data.countWords()).sum)
              .toMap))
      }

      override def state(): UIO[State] = storage.get.get()
    })
}
