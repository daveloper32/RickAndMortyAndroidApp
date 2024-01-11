package com.daveloper.rickandmortyapp.core.ui.components.custom.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.daveloper.rickandmortyapp.R

@Composable
fun TextNotEmptyValidation(
    value: String
): String {
    return if (value.isNotEmpty()) {
        value
    } else {
        stringResource(id = R.string.lab_unknown)
    }
}