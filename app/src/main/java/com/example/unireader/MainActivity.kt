package com.example.unireader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this)
        textView.text = "¡Hola Lumen! 🚀"
        textView.textSize = 24f
        setContentView(textView)
    }
}
