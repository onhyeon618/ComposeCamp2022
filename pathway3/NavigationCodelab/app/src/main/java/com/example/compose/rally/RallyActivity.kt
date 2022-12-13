/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.rally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.components.RallyTabRow
import com.example.compose.rally.ui.overview.OverviewScreen
import com.example.compose.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */
class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
        // compose에서 navigation작업을 할 때는 NavController가 주축이 된다. rememberNavController()로 생성 가능.
        // 모든 곳에서 접근 가능해야 하므로 composable 계층의 최상단에 위치해야 한다.
        val navController = rememberNavController()

        // NavController로부터 현재 위치 가져오기...
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =             rallyTabRowScreens.find { it.route == currentDestination?.route } ?: Overview

        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = rallyTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            // NavHost: acts as a container, responsible for displaying the current destination
            // 현재 화면(navigate된 목적지)를 보여주는 역할. 이동할 때마다 자동으로 recompose 된다.
            NavHost(
                navController = navController,
                startDestination = Overview.route,   // 앱 기동 시 시작하는 위치
                modifier = Modifier.padding(innerPadding)
            ) {
                // 여기에서 navigation graph를 "그린"다.
                composable(route = Overview.route) {   // adds the destination to your nav graph
                    OverviewScreen(
                        onClickSeeAllAccounts = {
                            navController.navigateSingleTopTo(Accounts.route)
                        },
                        onClickSeeAllBills = {
                            navController.navigateSingleTopTo(Bills.route)
                        },
                        onAccountClick = { accountType ->
                            navController.navigateToSingleAccount(accountType)
                        }
                    )   // define the actual UI to be displayed
                }
                composable(route = Accounts.route) {
                    AccountsScreen(
                        onAccountClick = { accountType ->
                            navController.navigateToSingleAccount(accountType)
                        }
                    )
                }
                composable(route = Bills.route) {
                    BillsScreen()
                }
                composable(
                    // navigate 할 때 argument를 담아서 주기
                    // 1. 주소는 route/{argument} 패턴 -> 이렇게 하면 해당 화면으로 이동할 때는 argument가 필수
                    // (안전하게 처리하려면 default 값 제공 가능.)
                    route = SingleAccount.routeWithArgs,
                    // 2. "make this composable aware that it should accept arguments" -> 파라미터로 추가하여 처리
                    arguments = SingleAccount.arguments
                ) { navBackStackEntry ->
                    // NavBackStackEntry에서 필요한 인자값 받아오기
                    val accountType = navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)

                    // SingleAccountScreen 화면 생성에 사용
                    SingleAccountScreen(accountType)
                }
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true     // popUpTo(startDestination): pop하면 바로 startDestination로 감. 스택 마구 쌓이지 X
        }
        launchSingleTop = true   // 동일 화면이 여러 번 생성되는 것 방지
        restoreState = true      // 다른 화면으로 이동했을 때도 현재 화면의 상태 유지 -> 돌아오면 새로 로딩하는 게 아니라 기존 상태 보여줌
    }

private fun NavHostController.navigateToSingleAccount(accountType: String) {
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}