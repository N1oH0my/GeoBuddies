package com.surf2024.geobuddies.domain.registration.repositoryimpl

import android.content.Context
import android.util.Log
import com.google.android.material.textfield.TextInputLayout
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentRegistrationBinding
import com.surf2024.geobuddies.domain.registration.repository.FieldType
import com.surf2024.geobuddies.domain.registration.repository.IInputLayoutFactoryRepository
import com.surf2024.geobuddies.domain.registration.repository.IRegistrationInputReadHelperRepository
import com.surf2024.geobuddies.domain.registration.repository.ITextInputLayoutRepository


class RegistrationInputReadHelperRepositoryImpl(
    private val context: Context,
    private val binding: FragmentRegistrationBinding,
): IRegistrationInputReadHelperRepository {

    private val inputLayoutFactory: IInputLayoutFactoryRepository = InputLayoutFactoryRepositoryImpl()

    override fun getName(): String? {
        return getValueFromField(context, inputLayoutFactory.createRepository(binding, FieldType.NAME))
    }

    override fun getEmail(): String? {
        return getValueFromField(context, inputLayoutFactory.createRepository(binding, FieldType.EMAIL))
    }

    override fun getPassword(): String? {
        return getValueFromField(context, inputLayoutFactory.createRepository(binding, FieldType.PASSWORD))
    }

    override fun getConfirmedPassword(): String? {
        return getValueFromField(context, inputLayoutFactory.createRepository(binding, FieldType.CONFIRMEDPASSWORD))
    }

    private fun getValueFromField(context: Context, field: ITextInputLayoutRepository): String?{

        val textInputLayout: TextInputLayout = field.getInputLayout()

        val textInputEditText = textInputLayout.editText
        val value = textInputEditText?.text.toString().trim()

        Log.d("FieldData", "value: $value")

        if (value.isEmpty()) {
            textInputLayout.error = context.getString(R.string.the_field_must_not_be_empty)
            return null
        }

        return value

    }
}


