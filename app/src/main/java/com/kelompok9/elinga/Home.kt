package com.kelompok9.elinga

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class Home : Fragment(), CalendarAdapter.OnItemListener {
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var selectedDate: LocalDate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        monthYearText = view.findViewById(R.id.monthYearTV)

        selectedDate = LocalDate.now()
        setMonthView()

        var _btnPrev : Button = view.findViewById(R.id.btnPrev)
        var _btnNext : Button = view.findViewById(R.id.btnNext)

        _btnPrev.setOnClickListener {
            previousMonthAction(view)
        }
        _btnNext.setOnClickListener {
            nextMonthAction(view)
        }

    }
    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }


    private fun daysInMonthArray(date: LocalDate?): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date!!.format(formatter)
    }

    fun previousMonthAction(view: View?) {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction(view: View?) {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }

    override fun onItemClick(position: Int, dayText: String?) {
        if (dayText != "") {
            val message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }


}