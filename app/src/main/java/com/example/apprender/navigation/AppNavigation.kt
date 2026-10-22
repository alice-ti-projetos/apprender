package com.example.apprender.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apprender.ui.screens.CategoryListScreen
import com.example.apprender.ui.screens.HomeScreen
import com.example.apprender.ui.screens.LessonCompleteScreen
import com.example.apprender.ui.screens.LessonScreen
import com.example.apprender.ui.screens.PrivacyScreen
import com.example.apprender.ui.screens.QuizScreen
import com.example.apprender.ui.screens.SettingsScreen

object ScreenRoutes {
    const val HOME = "home"
    const val CATEGORY_LIST = "category_list"
    const val LESSON = "lesson/{lessonId}"
    const val QUIZ = "quiz/{lessonId}"
    const val LESSON_COMPLETE = "lesson_complete"
    const val SETTINGS = "settings"
    const val PRIVACY = "privacy"

    fun createLessonRoute(lessonId: String) = "lesson/$lessonId"
    fun createQuizRoute(lessonId: String) = "quiz/$lessonId"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.HOME
    ) {
        composable(ScreenRoutes.HOME) {
            HomeScreen(
                onNavigateToCategories = {
                    navController.navigate(ScreenRoutes.CATEGORY_LIST)
                },
                onNavigateToSettings = {
                    navController.navigate(ScreenRoutes.SETTINGS)
                },
                onNavigateToPrivacy = {
                    navController.navigate(ScreenRoutes.PRIVACY)
                }
            )
        }

        composable(ScreenRoutes.CATEGORY_LIST) {
            CategoryListScreen(
                onNavigateToLesson = { lessonId ->
                    navController.navigate(ScreenRoutes.createLessonRoute(lessonId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = ScreenRoutes.LESSON,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: "default"
            LessonScreen(
                lessonId = lessonId,
                onNavigateToQuiz = {
                    navController.navigate(ScreenRoutes.createQuizRoute(lessonId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = ScreenRoutes.QUIZ,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: "default"
            QuizScreen(
                lessonId = lessonId,
                onNavigateToComplete = {
                    navController.navigate(ScreenRoutes.LESSON_COMPLETE) {
                        popUpTo(ScreenRoutes.HOME) { inclusive = false }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(ScreenRoutes.LESSON_COMPLETE) {
            LessonCompleteScreen(
                onNavigateToHome = {
                    navController.navigate(ScreenRoutes.HOME) {
                        popUpTo(ScreenRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(ScreenRoutes.SETTINGS) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(ScreenRoutes.PRIVACY) {
            PrivacyScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
