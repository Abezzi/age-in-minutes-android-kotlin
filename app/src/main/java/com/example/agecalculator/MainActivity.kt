package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var textSelectedDate : TextView? = null
    private var textAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSelectDate = findViewById<Button>(R.id.button_select_date)
        textSelectedDate = findViewById(R.id.text_selected_date_content)
        textAgeInMinutes = findViewById(R.id.text_age_in_minutes_content)

        btnSelectDate.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker () {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "${selectedMonth+1}/$selectedDay/$selectedYear"
                textSelectedDate?.text = selectedDate
                val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                val date = sdf.parse(selectedDate)
                date?.let {
                    val selectedDateInMinutes = date.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        textAgeInMinutes?.text = (currentDateInMinutes - selectedDateInMinutes).toString()
                    }
                }
            },
            year,
            month,
            day
        )

        val dayInMilliseconds = 86400000
        dpd.datePicker.maxDate = System.currentTimeMillis() - dayInMilliseconds
        dpd.show()
    }
}