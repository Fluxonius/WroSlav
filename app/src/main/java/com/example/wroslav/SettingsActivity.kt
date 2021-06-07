package com.example.wroslav

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var langEnBtn: Button
    private lateinit var langPlBtn: Button
    private lateinit var langRuBtn: Button
    private lateinit var langByBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        langEnBtn = findViewById(R.id.lang_en)
        langEnBtn.setOnClickListener {
            setLocate("en")
            restartApp()

        }

        langPlBtn = findViewById(R.id.lang_pl)
        langPlBtn.setOnClickListener {
            setLocate("pl")
            restartApp()
        }

        langRuBtn = findViewById(R.id.lang_ru)
        langRuBtn.setOnClickListener {
            setLocate("ru")
            restartApp()
        }

        langByBtn = findViewById(R.id.lang_by)
        langByBtn.setOnClickListener {
            setLocate("be")
            restartApp()
        }
    }

    private fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setLocate(Lang: String) { // call this to change language String example "ru" hhhh

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }
}
