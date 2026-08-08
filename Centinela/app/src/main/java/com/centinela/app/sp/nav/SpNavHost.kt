package com.centinela.app.sp.nav

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.centinela.app.sp.modules.antiidentidad.AntiidentidadScreen
import com.centinela.app.sp.modules.constitucion.ConstitucionScreen
import com.centinela.app.sp.modules.direccion.DireccionScreen
import com.centinela.app.sp.modules.identidad.IdentidadScreen
import com.centinela.app.sp.modules.objetivos.ObjetivosScreen
import com.centinela.app.sp.modules.placeholder.PlaceholderScreen
import com.centinela.app.sp.ui.theme.SpBackground
import com.centinela.app.sp.ui.theme.SpModuleTheme

@Composable
fun SistemaPersonalApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: SP_START_ROUTE
    val currentDest = SP_ROUTES.find { it.route == currentRoute } ?: SP_ROUTES.first()

    SpModuleTheme(currentDest.theme) {
        SpBackground {
            Row(modifier = Modifier.fillMaxSize()) {
                SidebarRail(
                    currentRoute = currentRoute,
                    onSelect = { route ->
                        if (route != currentRoute) {
                            navController.navigate(route) {
                                popUpTo(SP_START_ROUTE) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                )
                NavHost(
                    navController = navController,
                    startDestination = SP_START_ROUTE,
                    modifier = Modifier.weight(1f).fillMaxSize(),
                ) {
                    SP_ROUTES.forEach { dest ->
                        composable(dest.route) {
                            SpModuleTheme(dest.theme) {
                                if (dest.implemented) {
                                    when (dest.route) {
                                        "constitucion" -> ConstitucionScreen()
                                        "identidad" -> IdentidadScreen()
                                        "antiidentidad" -> AntiidentidadScreen()
                                        "direccion" -> DireccionScreen()
                                        "objetivos" -> ObjetivosScreen()
                                    }
                                } else {
                                    PlaceholderScreen(dest)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
