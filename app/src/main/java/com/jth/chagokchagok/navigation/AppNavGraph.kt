// 📄 com.jth.chagokchagok.navigation.AppNavGraph.kt
package com.jth.chagokchagok.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.*
import com.jth.chagokchagok.ui.MainScreen
import com.jth.chagokchagok.ui.addview.AddViewScreen
import com.jth.chagokchagok.ui.album.AlbumScreen
import com.jth.chagokchagok.ui.login.LoginScreen
import com.jth.chagokchagok.ui.login.LoginViewModel
import com.jth.chagokchagok.ui.planbudget.planBudgetScreen
import com.jth.chagokchagok.ui.signup.SignUpScreen
import com.jth.chagokchagok.ui.planstart.PlanStartScreen
import com.jth.chagokchagok.ui.home.*
import com.jth.chagokchagok.ui.mypage.MyPageScreen
import com.jth.chagokchagok.ui.editbudget.EditBudgetScreen

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
        startDestination = Screen.MainShell.route
    ) {
        composable(Screen.Login.route) {
            val id by loginViewModel.id.collectAsState("")
            val pw by loginViewModel.password.collectAsState("")

            LoginScreen(
                id = id,
                password = pw,
                onIdChanged = loginViewModel::onIdChanged,
                onPasswordChanged = loginViewModel::onPasswordChanged,
                onLoginClick = {
                    navController.navigate(Screen.MainShell.create()) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
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
            planBudgetScreen(
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

        composable(
            route = "home?budget={budget}&spent={spent}",
            arguments = listOf(
                navArgument("budget") { type = NavType.IntType },
                navArgument("spent") { type = NavType.IntType } // 추가됨
            )
        ) { entry ->
            val budget = entry.arguments?.getInt("budget") ?: 0
            val spent = entry.arguments?.getInt("spent") ?: 0 // 추가됨

            val vm: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

            LaunchedEffect(Unit) {
                if (budget > 0 && vm.uiState.value.budget == 0) {
                    vm.loadInitialData(budget, spent)
                }
            }

            HomeScreen(
                navController = navController,
                viewModel = vm,
            )
        }

        composable(Screen.AddView.route) { AddViewScreen(navController) }
        composable(Screen.EditBudget.route) { EditBudgetScreen(navController) }
    }
}
