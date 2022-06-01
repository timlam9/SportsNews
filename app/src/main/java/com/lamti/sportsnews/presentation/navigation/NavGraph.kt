package com.lamti.sportsnews.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lamti.sportsnews.presentation.screens.DetailsScreen
import com.lamti.sportsnews.presentation.screens.HomeScreen

private const val TEAM_ID = "teamID"

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen { teamID ->
                navController.navigate("${Screen.Details.route}/$teamID")
            }
        }
        composable(
            route = "${Screen.Details.route}/{$TEAM_ID}",
            arguments = listOf(navArgument(TEAM_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            DetailsScreen(teamID = backStackEntry.arguments?.getInt(TEAM_ID) ?: 0)
        }
    }
}
