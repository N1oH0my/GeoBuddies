package com.surf2024.geobuddies.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentRegistrationBinding
import com.surf2024.geobuddies.domain.main.usecase.FragmentChangeListener
import com.surf2024.geobuddies.domain.registration.repository.IRegistrationInputReadHelperRepository
import com.surf2024.geobuddies.domain.registration.repositoryimpl.RegistrationInputReadHelperRepositoryImpl
import com.surf2024.geobuddies.presentation.viewmodels.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    private lateinit var registrationViewModel: RegistrationViewModel

    private lateinit var registrationCompleteListener: FragmentChangeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registrationCompleteListener = context as FragmentChangeListener
    }
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

        overrideOnBackPressed()

        Log.d("Hilt", "Creating registrationViewModel client instance")
        registrationViewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        initObserversRegistrationViewModel()
        initListenerSignUpButton()

    }

    private fun overrideOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backFromRegistration()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initListenerSignUpButton(){
        binding.signUpRegistrationButton.setOnClickListener {
            registerUser()
        }
        binding.backRegistrationButton.setOnClickListener {
            backFromRegistration()
        }
    }

    private  fun initObserversRegistrationViewModel(){

        registrationViewModel.isRegistrationSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                showToast("Hi!")
                showToast("u ve been successfully registered!")
                onRegistrationComplete()
            }
            else{
                showToast("smth went wrong...")
                showToast("try later or check info")
            }
        }
    }


    private fun registerUser() {

        val registrationInputReadHelper: IRegistrationInputReadHelperRepository =
            RegistrationInputReadHelperRepositoryImpl(requireContext(), binding)

        val email = registrationInputReadHelper.getEmail()
        val name = registrationInputReadHelper.getName()
        val password = registrationInputReadHelper.getPassword()
        val confirmedPassword = registrationInputReadHelper.getConfirmedPassword()

        Log.d("FieldData", "email: ${email}," +
                "password: ${password}," +
                "confirmed password: ${confirmedPassword}," +
                "name: ${name}")

        registrationViewModel.register(
            email,
            password,
            confirmedPassword,
            name
            )

    }

    private fun onRegistrationComplete() {
        registrationCompleteListener.onRegistrationComplete()
    }

    private fun backFromRegistration() {
        registrationCompleteListener.onRegistrationBack()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    companion object {
        @JvmStatic
        fun newInstance(){}
    }
}