package com.kelompok9.elinga

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Calendar;
import com.google.firebase.firestore.FirebaseFirestore


lateinit var chosenDateDay : Int
lateinit var chosenDateMonth : Int
lateinit var chosenDateYear : Int

class MainActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var db : FirebaseFirestore
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()

        // calendar get element
        var weekOneLayout : LinearLayout = findViewById(R.id.calendar_week_1)
        var weekTwoLayout : LinearLayout = findViewById(R.id.calendar_week_2)
        var weekThreeLayout : LinearLayout = findViewById(R.id.calendar_week_3)
        var weekFourLayout : LinearLayout = findViewById(R.id.calendar_week_4)
        var weekFiveLayout : LinearLayout = findViewById(R.id.calendar_week_5)
        var weekSixLayout : LinearLayout = findViewById(R.id.calendar_week_6)

        // create array
        var weeks = arrayOfNulls<LinearLayout>(6)
        var days = arrayOfNulls<Button>(6 * 7)

        // init array
        weeks[0] = weekOneLayout
        weeks[1] = weekTwoLayout
        weeks[2] = weekThreeLayout
        weeks[3] = weekFourLayout
        weeks[4] = weekFiveLayout
        weeks[5] = weekSixLayout


        // button params
        val buttonParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        buttonParams.weight = 1f

        //Create each days and add days in weeks
        var daysArrayCount = 0

        for (weekNumber in 0..5) {
            for (dayInWeek in 0..6) {
                val day = Button(this@MainActivity)
                // how dis supposed to work idk
                //noinspection ResourceType
                day.setTextColor(Color.parseColor(resources.getString(R.color.grey)))
                day.setBackgroundColor(Color.TRANSPARENT)
                day.layoutParams = buttonParams
                day.textSize = (this@MainActivity.resources.displayMetrics.density as Int * 5).toFloat()
                day.setSingleLine()
                days[daysArrayCount] = day
                weeks[weekNumber]!!.addView(day)
                ++daysArrayCount
            }
        }

        var calendar = Calendar.getInstance()
        val currentDateDay: Int = calendar.get(Calendar.DAY_OF_MONTH).also {
            chosenDateDay = it
        }
        val currentDateMonth: Int = calendar.get(Calendar.MONTH).also {
            chosenDateMonth = it
        }
        val currentDateYear: Int = calendar.get(Calendar.YEAR).also {
            chosenDateYear = it
        }
        val daysInCurrentMonth: Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfCurrentMonth: Int = calendar.get(Calendar.DAY_OF_WEEK)



        var dayNumber = 1
        var daysLeftInFirstWeek = 0
        var indexOfDayAfterLastDayOfMonth = 0

        if (firstDayOfCurrentMonth != 1) {
            daysLeftInFirstWeek = firstDayOfCurrentMonth
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth
            for (i in firstDayOfCurrentMonth until firstDayOfCurrentMonth + daysInCurrentMonth) {
            // To differentiate current days
                if (currentDateMonth === chosenDateMonth && currentDateYear === chosenDateYear && dayNumber === currentDateDay) {
                    days[i]!!.setBackgroundColor(getColor(R.color.green))
                    days[i]!!.setTextColor(Color.WHITE)
                } else {
                    days[i]!!.setTextColor(Color.BLACK)
                    days[i]!!.setBackgroundColor(Color.TRANSPARENT)
                }
                //Create Day Tags
                val dateArr = IntArray(3)
                dateArr[0] = dayNumber
                dateArr[1] = chosenDateMonth
                dateArr[2] = chosenDateYear
                days[i]!!.tag = dateArr
                days[i]!!.text = dayNumber.toString()
                days[i]!!.setOnClickListener { v -> onDayClick(v) }
                ++dayNumber
            }
        }


        daysLeftInFirstWeek = 8
        indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth
        for (i in 8 until 8 + daysInCurrentMonth) {
            if (currentDateMonth === chosenDateMonth && currentDateYear === chosenDateYear && dayNumber === currentDateDay) {
            // To differentiate current days
                days[i]!!.setBackgroundColor(getColor(R.color.green))
                days[i]!!.setTextColor(Color.WHITE)
            } else {
                days[i]!!.setTextColor(Color.BLACK)
                days[i]!!.setBackgroundColor(Color.TRANSPARENT)
            }
            //Create Day Tags
            val dateArr = IntArray(3)
            dateArr[0] = dayNumber
            dateArr[1] = chosenDateMonth
            dateArr[2] = chosenDateYear
            days[i]!!.tag = dateArr
            days[i]!!.text = dayNumber.toString()
            days[i]!!.setOnClickListener { v -> onDayClick(v) }
            ++dayNumber
        }



        if (chosenDateMonth > 0) calendar[chosenDateYear, chosenDateMonth - 1] = 1 else calendar[chosenDateYear - 1, 11] = 1
        var daysInPreviousMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in daysLeftInFirstWeek - 1 downTo 0) {
            val dateArr = IntArray(3)
            if (chosenDateMonth > 0) {
                if (currentDateMonth == chosenDateMonth - 1 && currentDateYear === chosenDateYear && daysInPreviousMonth == currentDateDay) {
                } else {
                    days[i]!!.setBackgroundColor(Color.TRANSPARENT)
                }
                dateArr[0] = daysInPreviousMonth
                dateArr[1] = chosenDateMonth - 1
                dateArr[2] = chosenDateYear
            } else {
                if (currentDateMonth == 11 && currentDateYear == chosenDateYear - 1 && daysInPreviousMonth == currentDateDay) {
                } else {
                    days[i]!!.setBackgroundColor(Color.TRANSPARENT)
                }
                dateArr[0] = daysInPreviousMonth
                dateArr[1] = 11
                dateArr[2] = chosenDateYear - 1
            }
            days[i]!!.tag = dateArr
            days[i]!!.text = daysInPreviousMonth--.toString()
            days[i]!!.setOnClickListener { v -> onDayClick(v) }
        }

    }

    private fun onDayClick(v: View?) {

    }
}