package br.com.usinasantafe.pci.utils

import java.util.Calendar
import java.util.Date

fun dateToDeleteMonth(): Date {
    val c: Calendar = Calendar.getInstance()
    c.time = Date()
    c.add(Calendar.MONTH, -1)
    return c.time
}

fun dateToDeleteYear(): Date {
    val c: Calendar = Calendar.getInstance()
    c.time = Date()
    c.add(Calendar.YEAR, -1)
    return c.time
}