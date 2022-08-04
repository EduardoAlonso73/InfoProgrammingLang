package com.example.applanguagepgm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applanguagepgm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        mainBinding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
    }
}