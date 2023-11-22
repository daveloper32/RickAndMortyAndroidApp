package com.daveloper.rickandmortyapp.feature_main.presentation.components.navigation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.ui.components.models.NavigationDrawerItem
import com.daveloper.rickandmortyapp.core.utils.config.ProjectConfigUtils.getCurrentAppVersion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigationDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    currentIndexItemSelected: Int,
    onItemClicked: (
        (
            index: Int,
            navigationDrawerItem: NavigationDrawerItem
        ) -> Unit
    )? = null,
    onClose: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    item {
                        Column {
                            MainNavigationDrawerHeader(
                                onClose = {
                                    onClose?.invoke()
                                }
                            )
                            NavigationDrawerItems().forEachIndexed { index, navigationDrawerItem ->
                                MainNavigationDrawerItem(
                                    isSelected = index == currentIndexItemSelected,
                                    label = navigationDrawerItem.label,
                                    icon = navigationDrawerItem.icon,
                                    onClick = {
                                        onItemClicked?.invoke(index, navigationDrawerItem)
                                    }
                                )
                            }
                        }
                    }
                    item {
                        MainNavigationDrawerFooter()
                    }
                }
            }
        }
    ) {
        content()
    }
}

@Composable
private fun MainNavigationDrawerHeader(
    onClose: (() -> Unit)? = null,
) {
    Column {
        Box {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                onClick = {
                    onClose?.invoke()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = stringResource(id = R.string.app_name)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                Image(
                    painter = painterResource(
                        id = R.drawable.ic_app_logo
                    ),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .clip(
                            CircleShape // Circle the image corners
                        )
                        .size(120.dp)

                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = stringResource(id = R.string.app_name),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }
        }
        Divider(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp
                )
                .padding(
                    bottom = 8.dp
                )
        )
    }
}

@Composable
private fun MainNavigationDrawerFooter() {
    val context = LocalContext.current
    Column {
        Divider(
            modifier = Modifier
                .padding(
                    8.dp
                )
                .padding(
                    bottom = 8.dp
                )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 16.dp
                )
                .padding(
                    horizontal = 8.dp
                ),
            text = context.getCurrentAppVersion(),
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            fontSize = 12.sp
        )
    }
}