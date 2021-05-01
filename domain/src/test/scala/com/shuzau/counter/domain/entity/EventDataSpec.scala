package com.shuzau.counter.domain.entity

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

class EventDataSpec extends AnyFlatSpec
  with Matchers
  with TableDrivenPropertyChecks {

  behavior of "EventData"

  forAll(Table("value",
    "",
    " ",
    "   ")) { value =>
    it should s"0 for '$value'" in {
      EventData(value).countWords() shouldBe 0
    }
  }

  forAll(Table("value",
    "hello",
    "  hello",
    "hello  ",
    "   hello  ")) { value =>
    it should s"1 for '$value'" in {
      EventData(value).countWords() shouldBe 1
    }
  }

  forAll(Table("value",
    "hello world",
    "  hello world ",
    "  hello  world  ")) { value =>
    it should s"2 for '$value'" in {
      EventData(value).countWords() shouldBe 2
    }
  }
}
