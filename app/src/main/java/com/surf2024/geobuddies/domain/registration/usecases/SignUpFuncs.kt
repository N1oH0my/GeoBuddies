package com.surf2024.geobuddies.domain.registration.usecases

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentRegistrationBinding
import com.surf2024.geobuddies.domain.registration.entity.RegistrationModel
import com.surf2024.geobuddies.domain.registration.repository.FieldType
import com.surf2024.geobuddies.domain.registration.repository.IInputLayoutFactory
import com.surf2024.geobuddies.domain.registration.repository.ITextInputLayoutRepository
import com.surf2024.geobuddies.domain.registration.repositoryimpl.InputLayoutRepositoryFactoryImpl

fun getRegistrationModel(context: Context, binding: FragmentRegistrationBinding): RegistrationModel?{

    val inputLayoutFactory: IInputLayoutFactory = InputLayoutRepositoryFactoryImpl()

    val name: String? = getValueFromField(
        context,
        inputLayoutFactory.createRepository(binding, FieldType.NAME)
    )
    val email: String? = getValueFromField(
        context,
        inputLayoutFactory.createRepository(binding, FieldType.EMAIL)
    )
    val password: String? = getValueFromField(
        context,
        inputLayoutFactory.createRepository(binding, FieldType.PASSWORD)
    )
    val confirmedPassword: String? = getValueFromField(
        context,
        inputLayoutFactory.createRepository(binding, FieldType.CONFIRMEDPASSWORD)
    )

    val flag = checkIsValidRegistrationFields(
        name, email, password, confirmedPassword
    )

    if(flag){
        return RegistrationModel(name!!, email!!, password!!)
    }

    return null

}

fun getValueFromField(context: Context, field: ITextInputLayoutRepository): String?{

    val textInputLayout: TextInputLayout = field.getInputLayout()

    val textInputEditText = textInputLayout.editText
    val value = textInputEditText?.text.toString().trim()

    if (value.isEmpty()) {
        textInputLayout.error = context.getString(R.string.the_field_must_not_be_empty)
        return null
    }

    return value

}

fun checkIsValidRegistrationFields(
    name: String?,
    email: String?,
    password: String?,
    confirmedPassword: String?,
): Boolean{
    if (name != null && email != null && password != null && confirmedPassword != null) {
        if (password == confirmedPassword) {
            return true
        }
    }

    return false
}