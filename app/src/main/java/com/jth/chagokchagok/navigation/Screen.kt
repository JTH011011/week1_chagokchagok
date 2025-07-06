package com.jth.chagokchagok.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object PlanStart : Screen("planstart")
    object PlanBudget : Screen("planbudget/{userName}") {
        fun create(userName: String) = "planbudget/$userName"
    }
    object Home : Screen("home?budget={budget}") {
        fun create(budget: Int) = "home?budget=$budget"
    }
    object Album : Screen("album")
    object MyPage : Screen("mypage")
    object AddView : Screen("addview")
    object EditBudget : Screen("editbudget")

}

