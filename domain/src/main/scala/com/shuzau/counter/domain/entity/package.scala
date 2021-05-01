package com.shuzau.counter.domain

import io.estatico.newtype.macros.newtype
import scala.language.implicitConversions

package object entity {
  @newtype final case class EventType(value: String)
  @newtype final case class EventData(value: String)
}
