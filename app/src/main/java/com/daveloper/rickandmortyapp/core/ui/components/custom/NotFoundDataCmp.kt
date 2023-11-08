package com.daveloper.rickandmortyapp.core.ui.components.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daveloper.rickandmortyapp.R

@Composable
fun NotFoundDataCmp(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .padding(
                    bottom = 8.dp
                ).size(120.dp),
            painter = painterResource(id = R.drawable.ic_not_found),
            contentDescription = stringResource(id = R.string.lab_not_found_data_error),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = stringResource(id = R.string.lab_not_found_data_error),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
        Text(
            text = stringResource(id = R.string.lab_please_try_another_search),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
    }
}