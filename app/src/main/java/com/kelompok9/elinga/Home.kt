package com.kelompok9.elinga

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import com.kelompok9.elinga.CalendarUtils.selectedDate
import org.w3c.dom.Text

@SuppressLint("ObsoleteSdkInt")
class Home : Fragment(), CalendarAdapter.OnItemListener {
    companion object {
        lateinit var monthYearText : TextView
    }

    private lateinit var calendarRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Init
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        monthYearText= view.findViewById(R.id.monthYearTV)

        selectedDate = LocalDate.now()
        monthYearText.text = null

        Log.e(TAG,monthYearText.text.toString() + " aaaaaaaaa")
        setMonthView(view)


        val _dndON : Button = view.findViewById(R.id.dndON)
        val _dndOFF : Button = view.findViewById(R.id.dndOFF)
        val _btnPrev : ImageButton = view.findViewById(R.id.btnPrev)
        val _btnNext : ImageButton = view.findViewById(R.id.btnNext)


        _btnPrev.setOnClickListener {
            previousMonthAction(view)
        }
        _btnNext.setOnClickListener {
            nextMonthAction(view)
        }

        _dndON.setOnClickListener {
            turnon_DND()
        }
        _dndOFF.setOnClickListener {
            turnoff_DND()
        }

    }
    private fun setMonthView(view: View) {
        monthYearText.text = ""
        monthYearText.text = monthYearFromDate(selectedDate)
        Log.e(TAG,monthYearText.text.toString())
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
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
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

    fun previousMonthAction(view: View) {
        selectedDate = selectedDate!!.minusMonths(1)
        setMonthView(view)
    }

    fun nextMonthAction(view: View) {
        selectedDate = selectedDate!!.plusMonths(1)
        setMonthView(view)
    }

    override fun onItemClick(position: Int, dayText: String?,view: View) {
        if (dayText != "") {
            var bundle = Bundle()

            //var bulan = CalendarUtils.monthYearFromDate(selectedDate!!)
            // var hari = selectedDate!!.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())

            // gabisa
            /*if (dayText != null) {
                selectedDate.dayOfMonth = dayText.toInt()
            }*/
            Log.e(TAG,selectedDate!!.dayOfMonth.toString())

            var pickedDate : LocalDate = LocalDate.of(selectedDate!!.year, selectedDate!!.month,dayText!!.toInt())

            var bulan = pickedDate.month.toString()
            var hari = pickedDate.dayOfWeek.getDisplayName(TextStyle.FULL,Locale.getDefault())
            var tanggal = pickedDate.dayOfMonth.toString()
            var tahun = pickedDate.year.toString()

            Log.e(TAG,pickedDate.month.toString())
            Log.e(TAG,pickedDate.dayOfWeek.getDisplayName(TextStyle.FULL,Locale.getDefault()))
            Log.e(TAG,pickedDate.dayOfMonth.toString())
            Log.e(TAG,pickedDate.year.toString())


            bundle.putString("bulan", bulan)
            bundle.putString("hari", hari)
            bundle.putString("tanggal",tanggal)
            bundle.putString("tahun",tahun)

            val dailyInteraction_Frag = dailyInteraction()
            dailyInteraction_Frag.arguments = bundle
            //Navigation.findNavController(view).navigate(R.id.dailyInteraction)
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, dailyInteraction_Frag, dailyInteraction::class.java.simpleName)
                addToBackStack(null)
                commit()
            }

            val message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun turnon_DND() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (MainActivity.notificationManager.isNotificationPolicyAccessGranted) {
                //turn on dnd
                MainActivity.notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
            } else {
                //request permission
                Toast.makeText(context, "Please give us access", Toast.LENGTH_SHORT).show()
                val dndIntent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                startActivity(dndIntent)
            }

        } else {
            Toast.makeText(context, "Your device does not support DND", Toast.LENGTH_SHORT).show()
        }
    }

    private fun turnoff_DND() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (MainActivity.notificationManager.isNotificationPolicyAccessGranted) {
                //turn on dnd
                MainActivity.notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
            } else {
                //request permission
                Toast.makeText(context, "Please give us access", Toast.LENGTH_SHORT).show()
                val dndIntent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                startActivity(dndIntent)
            }

        } else {
            Toast.makeText(context, "Your device does not support DND", Toast.LENGTH_SHORT).show()
        }
    }
}