package com.example.mainproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        watch_event_list.setOnClickListener{
            val intent = Intent(this, EventListActivity::class.java)
            startActivity(intent)
        }

        exit.setOnClickListener{
            val intent = Intent(this, StartPointActivity::class.java)
            startActivity(intent)
        }

        my_profile.setOnClickListener{
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }

        create_event.setOnClickListener{
            val intent = Intent(this, MyNewActivity::class.java)
            startActivity(intent)
        }

    }
}