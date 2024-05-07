package com.surf2024.geobuddies.presentation.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.ActivityMainBinding
import com.surf2024.geobuddies.presentation.viewmodels.MainVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainVM by viewModels()
    private val secondFragment = SplashScreenFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSubmitButton()

        setRegisterButton()

        setVMObserve()

    }

    private fun setSubmitButton(){
        val buttonSubmit = binding.submitButton
        buttonSubmit.setOnClickListener {
            val editTextName = binding.editText
            val name = editTextName.text.toString()

            viewModel.updateName(name)
        }
    }

    private fun setRegisterButton(){
        val registerButton = binding.buttonRegister
        registerButton.setOnClickListener{
            //viewModel.registration()
            showToast("click")
            supportFragmentManager.beginTransaction()
                .add(R.id.main, secondFragment)
                .commit()
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