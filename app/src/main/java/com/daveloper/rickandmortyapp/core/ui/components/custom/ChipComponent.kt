package com.daveloper.rickandmortyapp.core.ui.components.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chip(
    modifier: Modifier = Modifier,
    name: String,
    isChecked: Boolean = false,
    subTitle: String? = null,
    onChipClicked: ((name: String) -> Unit)? = null
) {
    Column(
        modifier = modifier
            .padding(
                end = 8.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!subTitle.isNullOrEmpty()) {
            Text(
                text = subTitle,
                fontSize = 10.sp
            )
        }
        Card(
            onClick = {
                onChipClicked?.invoke(name)
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isChecked) {
                    Icon(
                        modifier = Modifier
                            .size(22.dp)
                            .padding(
                                end = 4.dp
                            )
                        ,
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Chip selected"
                    )
                }
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = if (isChecked) { FontWeight.Bold } else { FontWeight.Normal }
                )
            }
        }
    }
}