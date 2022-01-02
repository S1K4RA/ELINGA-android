package com.kelompok9.elinga

import java.time.LocalTime
import java.util.*


data class Event(
    var name: String? = "",
    var time: LocalTime = LocalTime.now(),
    var link: String? = "",
    var type: String? = "",
)


