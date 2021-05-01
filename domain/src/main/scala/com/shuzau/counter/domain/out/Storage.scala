package com.shuzau.counter.domain.out

import com.shuzau.counter.domain.entity.State
import zio.{Has, Ref, UIO, ULayer, ZIO, ZLayer}

object Storage {
  type InMemoryState = Has[Ref[State]]
  type StorageService = Has[Service]

  trait Service {
    def save(state: State): UIO[Unit]

    def get(): UIO[State]
  }

  def save(state: State): ZIO[StorageService, Nothing, Unit] =
    ZIO.accessM[StorageService](_.get[Service].save(state))

  def get(): ZIO[StorageService, Nothing, State] =
    ZIO.accessM[StorageService](_.get[Service].get())

  val layer: ULayer[StorageService] =
    Ref.make(State.empty).toLayer >>> ZLayer.fromFunction(ref => new Service {
      override def save(state: State): UIO[Unit] =
        ref.get.set(state)

      override def get(): UIO[State] =
        ref.get.get
    })
}
