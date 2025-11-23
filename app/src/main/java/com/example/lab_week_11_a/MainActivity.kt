package com.example.lab_week_11_a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // inisialisasi SettingsStore dari Application
        val settingsStore = (application as SettingsApplication).settingsStore

        // inisialisasi ViewModel
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(settingsStore) as T
            }
        })[SettingsViewModel::class.java]

        val tvResult = findViewById<TextView>(R.id.activity_main_text_view)
        val etInput = findViewById<EditText>(R.id.activity_main_edit_text)
        val btnSave = findViewById<Button>(R.id.activity_main_button)

        // update UI ketika datastore berubah
        viewModel.textLiveData.observe(this) { text ->
            tvResult.text = text
        }

        // saat tombol diklik → save ke datastore
        btnSave.setOnClickListener {
            val input = etInput.text.toString()
            viewModel.saveText(input)
        }
    }
}
