package com.surf2024.geobuddies.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentLoginBinding
import com.surf2024.geobuddies.domain.login.repository.ILoginInputReadHelperRepository
import com.surf2024.geobuddies.domain.login.repositoryimpl.LoginInputReadHelperRepositoryImpl
import com.surf2024.geobuddies.domain.main.usecase.FragmentChangeListener
import com.surf2024.geobuddies.presentation.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var loginCompleteListener: FragmentChangeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginCompleteListener = context as FragmentChangeListener
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("Hilt", "Creating loginViewModel client instance")
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        initObserversLoginViewModel()
        initListenerLoginButton()
        initListenerSingUpButton()

    }

    private fun initObserversLoginViewModel() {

        loginViewModel.isLoginSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                showToast("Hi!")
                showToast("u ve been successfully logged!")
                onLoginComplete()
            } else {
                showToast("smth went wrong...")
            }
        }
    }

    private fun initListenerLoginButton() {
        binding.loginFragmentLoginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun initListenerSingUpButton() {
        binding.loginFragmentSingUpTextView.setOnClickListener {
            onSignUpClicked()
        }
    }

    private fun loginUser() {

        val loginInputReadHelper: ILoginInputReadHelperRepository =
            LoginInputReadHelperRepositoryImpl(requireContext(), binding)

        val email = loginInputReadHelper.getEmail()
        val password = loginInputReadHelper.getPassword()

        Log.d(
            "FieldData", "email: ${email}," +
                    "password: ${password},"
        )

        loginViewModel.login(
            email,
            password
        )

    }

    private fun onLoginComplete() {
        loginCompleteListener.onLoginComplete()
    }
    private fun onSignUpClicked() {
        loginCompleteListener.onSignUpClicked()
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() {
        }
    }
}