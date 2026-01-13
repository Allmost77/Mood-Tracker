package com.example.moodtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moodtracker.ui.theme.MoodTrackerTheme
import androidx.navigation.compose.rememberNavController
import com.example.moodtracker.Focus.FocusScreen
import com.example.moodtracker.Home.HomeScreen
import com.example.moodtracker.Stats.StatsScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoodTrackerTheme {
                NavigationBar(modifier = Modifier)
            }
        }
    }
}
@Composable
fun NavigationBar(modifier: Modifier){
    val navController = rememberNavController()
    val startPage = Destination.HOME
    var selectedDestination by rememberSaveable {
        mutableStateOf(startPage.ordinal)
    }

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets
            ){
                Destination.entries.forEachIndexed {
                    index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = stringResource(destination.label),
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label =  {
                            Text(stringResource(destination.label))
                        }

                    )
                }
            }
        }
    ) {
        contentPadding ->
            AppNavHost(navController, modifier = Modifier.padding(contentPadding))
    }

}
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destination.HOME.route,
        modifier = modifier
    ) {
        composable(Destination.HOME.route) { HomeScreen() }
        composable(Destination.FOCUS.route) { FocusScreen() }
        composable(Destination.STATS.route) { StatsScreen() }
    }
}

