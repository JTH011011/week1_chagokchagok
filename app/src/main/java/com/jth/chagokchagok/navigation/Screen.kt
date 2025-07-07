package com.jth.chagokchagok.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoAlbum
/* ────────────────────────────
   상위(로그인·온보딩 → 메인쉘, BottomBar 없는 풀스크린)
   ──────────────────────────── */
sealed class Screen(val route: String) {

    object Login      : Screen("login")
    object SignUp     : Screen("signup")
    object PlanStart  : Screen("planstart")

    object PlanBudget : Screen("planbudget")

    /** BottomBar를 품은 메인 셸(= MainScreen) */
    object MainShell  : Screen("main") {
        fun create() = route
    }

    /** BottomBar가 사라지는 세부 화면들 */
    object AddView    : Screen("addview")
    object EditBudget : Screen("editbudget")
}

/* ────────────────────────────
   BottomBar 탭 전용 route
   ──────────────────────────── */
sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem("home", Icons.Default.Home, "홈") {
        fun create(budget: Int) = "home?budget=$budget"
    }

    object Album : BottomNavItem("album", Icons.Default.PhotoAlbum, "사진첩")
    object MyPage : BottomNavItem("mypage", Icons.Default.Person, "마이페이지")

    companion object {
        val items: List<BottomNavItem>
            get() = listOf(Home, Album, MyPage)
    }
}
