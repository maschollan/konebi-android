package com.chollan.konebi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chollan.konebi.R
import com.chollan.konebi.ui.navigation.Screen

@Composable
fun BottomBar(
    navController: NavHostController,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .height(64.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Home.route) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
            }
        }) {
            Icon(
                painter = painterResource(R.drawable.home_24),
                contentDescription = "home",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        IconButton(onClick = {
            navController.navigate(Screen.Report.route) {
                popUpTo(Screen.Home.route) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
            }
        }) {
            Icon(
                painter = painterResource(R.drawable.report_24),
                contentDescription = "home",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        IconButton(onClick = {
            navController.navigate(Screen.Control.route) {
                popUpTo(Screen.Home.route) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
            }
        }) {
            Icon(
                painter = painterResource(R.drawable.settings_24),
                contentDescription = "home",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        IconButton(onClick = { onSaveClick() }) {
            Icon(
                painter = painterResource(R.drawable.round_save_24),
                contentDescription = "save",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}