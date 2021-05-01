package com.shuzau.counter.domain.entity

import java.time.Instant

final case class Event(eventType: EventType, data: EventData, timestamp: Instant)
