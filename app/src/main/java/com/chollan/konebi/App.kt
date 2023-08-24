package com.chollan.konebi

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chollan.konebi.ui.navigation.NavMain
import com.chollan.konebi.ui.theme.KONEBITheme


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KONEBITheme {
        Greeting("Android")
    }
}

@Composable
fun App(navController: NavHostController = rememberNavController()) {
    KONEBITheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavMain(navController = navController, modifier = Modifier.fillMaxSize())

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    App()
}