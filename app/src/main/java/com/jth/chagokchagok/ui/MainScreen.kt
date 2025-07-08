package com.jth.chagokchagok.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jth.chagokchagok.data.preferences.UserPreferences
import com.jth.chagokchagok.ui.album.AlbumScreen
import com.jth.chagokchagok.ui.home.HomeScreen
import com.jth.chagokchagok.ui.mypage.MyPageScreen
import com.jth.chagokchagok.navigation.BottomNavItem
import com.jth.chagokchagok.ui.mypage.MyPageViewModel

@Composable
fun MainScreen(
    outerNavController: NavHostController,
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
        ?.substringBefore("?") ?: BottomNavItem.Home.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onTabSelected = { route ->
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                val context = LocalContext.current
                val prefs = UserPreferences(context)
                val userId by prefs.userIdFlow.collectAsState(initial = "")

                HomeScreen(
                    navController = outerNavController,
                    userId = userId!!,
                )

            }
            composable(BottomNavItem.Album.route) {
                AlbumScreen(navController = outerNavController)
            }
            composable(BottomNavItem.MyPage.route) {
                val vm: MyPageViewModel = viewModel()
                MyPageScreen(navController = outerNavController, viewModel = vm)
            }
        }
    }
}

@Composable
fun BottomNavBar(
    currentRoute: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar {
        BottomNavItem.items.forEach { item ->
            val selected = currentRoute.startsWith(item.route) == true

            val route = item.route
            val isSelected = currentRoute.startsWith(route)
            var icon = item.icon
            var label = item.label

            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(route) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (selected) Color(0xFFFF9800) else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selected) Color(0xFFFF9800) else Color.Gray,
                        fontSize = 12.sp
                    )
                }
            )
        }
    }
}
