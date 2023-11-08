package com.daveloper.rickandmortyapp.core.ui.components.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.daveloper.rickandmortyapp.core.domain.model.ItemDataFilter
import com.daveloper.rickandmortyapp.core.ui.components.Chip

@Composable
fun FilterSelector(
    modifier: Modifier = Modifier,
    subTitle: String? = null,
    data: List<ItemDataFilter> = listOf(),
    onSomeFilterChipSelected: ((itemSelected: String) -> Unit)? = null
) {
    Column(
        modifier = modifier
    ) {
        if (!subTitle.isNullOrEmpty()) {
            Text(
                text = subTitle,
                fontSize = 10.sp
            )
        }
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Start
        ) {
            items(data) {
                Chip(
                    name = it.label,
                    isChecked = it.isSelected
                ) { chipValue ->
                    onSomeFilterChipSelected?.invoke(chipValue)
                }
            }
        }
    }
}