package com.kelompok9.elinga

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class dayView : AppCompatActivity() {
    companion object {
        var log_arr = arrayListOf<Item>()
        lateinit var db: FirebaseFirestore

    }
    var sorter = ""
    var logArray = arrayListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_view)

        db = FirebaseFirestore.getInstance()
        var rv: RecyclerView = findViewById(R.id.LogRV)

        showData(rv)

        findViewById<FloatingActionButton>(R.id.btnAdd).setOnClickListener {
            // ganti ke fragment

            /* var intent = Intent(this,addDialog::class.java)
             startActivity(intent)*/
        }


        // Gae Credits
        /*findViewById<FloatingActionButton>(R.id.btnInfo).setOnClickListener {
            val mDialogView = LayoutInflater.from(this@MainActivity).inflate(R.layout.activity_info_dialog,null)
            val mBuilder = AlertDialog.Builder(this@MainActivity)
                .setView(mDialogView)
            val  mAlertDialog = mBuilder.show()

            mDialogView.findViewById<Button>(R.id.btnBacktomain).setOnClickListener {
                mAlertDialog.dismiss()
            }

        }*/
    }
    fun showData(rv : RecyclerView) {
        logArray.clear()

        val arr = db.collection("Log")
            .get()
            .addOnSuccessListener { result ->
                Toast.makeText(this, "Data Loaded", Toast.LENGTH_SHORT).show()
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    var data = Item(
                        document.data["value"].toString(),
                        document.data["category"].toString().toInt(),
                        document.data["date"].toString().toLong()
                    )
                    logArray.add(data)
                }
                logArray.sortedByDescending { it.date }

                var lArr = arrayListOf<Item>()
                lArr.clear()
                if (sorter != "") {
                    for (item in logArray.indices) {
                        if (logArray[item].date.toString() == sorter) {
                            println(logArray[item].date.toString())
                            println(sorter)

                            lArr.add(logArray[item])
                        }
                    }
                } else {
                    lArr = logArray
                }

                rv.layoutManager = LinearLayoutManager(this)

                val adapterP = adapterLog(lArr)
                rv.adapter = adapterP
            }
            .addOnFailureListener {
                Toast.makeText(this, "Data Fetch Failed", Toast.LENGTH_SHORT).show()
            }
    }
}