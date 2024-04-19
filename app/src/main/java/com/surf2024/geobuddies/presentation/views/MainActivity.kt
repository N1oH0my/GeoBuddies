package com.surf2024.geobuddies.presentation.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.ActivityMainBinding
import com.surf2024.geobuddies.presentation.viewmodels.MainVM

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var viewModel: MainVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getVM()

        setSubmitButton()

        setVMObserve()

    }

    private fun getVM(){
        viewModel = ViewModelProvider(this).get(MainVM::class.java)
    }

    private fun setSubmitButton(){
        val buttonSubmit = binding.submitButton
        buttonSubmit.setOnClickListener {
            val editTextName = binding.editText
            val name = editTextName.text.toString()

            viewModel.updateName(name)
        }
    }

    private fun setVMObserve(){
        viewModel.userNameLiveData.observe(this, Observer { name ->
            showToast("Hello, $name!")
            showToast("Welcome to the GeoBuddies!")
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}