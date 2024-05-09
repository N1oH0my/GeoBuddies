package com.surf2024.geobuddies.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentRegistrationBinding
import com.surf2024.geobuddies.domain.registration.entity.RegistrationModel
import com.surf2024.geobuddies.domain.registration.usecases.getRegistrationModel
import com.surf2024.geobuddies.presentation.viewmodels.RegistrationViewModel


class RegistrationFragment : Fragment() {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    private lateinit var registrationViewModel: RegistrationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registrationViewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        initObserversRegistrationViewModel()
        initListenerSignUpButton()

    }

    private fun initListenerSignUpButton(){
        binding.signUpRegistrationButton.setOnClickListener {
            registrationViewModel.setLoading(true)
        }
    }

    private  fun initObserversRegistrationViewModel(){
        registrationViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                registerUser()
            }
        }

        registrationViewModel.isRegistrationSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                /**
                 * TODO:
                 *  уведомление что регистрация не успешна
                 *  перенаправить на фрагмент логина
                 */
            }
            else{
                /**
                 * TODO:
                 *  уведомление что регистрация не успешна
                 */
            }
        }
    }


    private fun registerUser() {
        val user: RegistrationModel? = getRegistrationModel(requireContext(), binding)
        if(user != null){
            registrationViewModel.register(user)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(){}
    }
}