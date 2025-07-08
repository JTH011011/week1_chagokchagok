// 📄 com.jth.chagokchagok.navigation.AppNavGraph.kt
package com.jth.chagokchagok.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.*
import com.jth.chagokchagok.data.preferences.UserPreferences
import com.jth.chagokchagok.ui.MainScreen
import com.jth.chagokchagok.ui.addview.AddViewScreen
import com.jth.chagokchagok.ui.login.LoginScreen
import com.jth.chagokchagok.ui.login.LoginViewModel
import com.jth.chagokchagok.ui.planbudget.planBudgetScreen
import com.jth.chagokchagok.ui.signup.SignUpScreen
import com.jth.chagokchagok.ui.planstart.PlanStartScreen
import com.jth.chagokchagok.ui.home.*
import com.jth.chagokchagok.ui.mypage.MyPageScreen
import com.jth.chagokchagok.ui.editbudget.EditBudgetScreen
import com.jth.chagokchagok.ui.login.LoginUiState
import java.time.YearMonth

@Composable
fun AppNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    innerPadding : PaddingValues
) {
    val modifierWithPadding = Modifier.padding(innerPadding)
    NavHost(
        navController = navController,
        //Todo:디버깅시 여기를 수정
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val id by loginViewModel.id.collectAsState()
            val pw by loginViewModel.password.collectAsState()
            val uiState by loginViewModel.uiState.collectAsState()

            LaunchedEffect(uiState) {
                if (uiState is LoginUiState.Success) {
                    val username = (uiState as LoginUiState.Success).username
                    navController.navigate(Screen.MainShell.create()) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }

            LoginScreen(
                id = id,
                password = pw,
                onIdChanged = loginViewModel::onIdChanged,
                onPasswordChanged = loginViewModel::onPasswordChanged,
                onLoginClick = loginViewModel::onLoginClicked,
                onSignUpClick = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                onSignUpComplete = {
                    navController.navigate(Screen.PlanStart.route)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.PlanStart.route) {
            PlanStartScreen(
                onStartClick = {
                    navController.navigate(Screen.PlanBudget.route)
                }
            )
        }

        composable(Screen.PlanBudget.route) {
            val context = LocalContext.current
            val userPreferences = UserPreferences(context)
            val userId by userPreferences.userIdFlow.collectAsState(initial = "")
            val id = userId ?: return@composable
            planBudgetScreen(
                userId = id,
                onPreviousClick = { navController.popBackStack() },
                onCompleteClick = { budget ->
                    navController.navigate(Screen.MainShell.create()) {
                        popUpTo(Screen.PlanStart.route) { inclusive = true }
                    }
                }
            )
        }


        composable(Screen.MainShell.route) {
            MainScreen(outerNavController = navController)
        }

        composable(BottomNavItem.Home.route) {
            val context = LocalContext.current
            val userPreferences = UserPreferences(context)
            val userId by userPreferences.userIdFlow.collectAsState(initial = "")

            val viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val currentYearMonth = YearMonth.now() // 예: "2025-07"

            LaunchedEffect(userId) {
                    viewModel.loadMonthlyBudget(userId.orEmpty(), currentYearMonth)
            }

            HomeScreen(
                navController = navController,
                userId = userId.orEmpty(),
                viewModel = viewModel
            )
        }


        composable(Screen.AddView.route) { AddViewScreen(navController) }
        composable(Screen.EditBudget.route) { EditBudgetScreen(navController) }
    }
}
