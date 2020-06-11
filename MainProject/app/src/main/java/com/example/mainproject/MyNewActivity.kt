package com.example.mainproject

import android.app.usage.UsageEvents
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_my_new.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import java.io.File
import java.io.FileInputStream

class MyNewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_new)
        val path = this.getFilesDir()// находим путь до внутренней папки приложения
        val Event_description_1 = File(path, "Event_description_1.txt")
        val Event_description_2 = File(path, "Event_description_2.txt")
        var str: String

        if(Event_description_1.exists()) {
            Log.d("SUKA", "777777")
            str = FileInputStream(Event_description_1).bufferedReader().use { it.readText() }
            event_description_1.setText(str)
        }
        if(Event_description_2.exists())  {
            str = FileInputStream(Event_description_2).bufferedReader().use { it.readText() }
            event_description_2.setText(str)
        }
        save_created_event.setOnClickListener(){

            update_text_files(listOf("Event_description_1.txt","Event_description_2.txt"),
                listOf(event_description_1.text.toString(),event_description_2.text.toString()), this)
        }

        cancel_created_event.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}