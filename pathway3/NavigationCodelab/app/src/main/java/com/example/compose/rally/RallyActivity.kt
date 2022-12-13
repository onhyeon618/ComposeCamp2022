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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.rally.ui.components.RallyTabRow
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
        var currentScreen: RallyDestination by remember { mutableStateOf(Overview) }
        // compose에서 navigation작업을 할 때는 NavController가 주축이 된다. rememberNavController()로 생성 가능.
        // 모든 곳에서 접근 가능해야 하므로 composable 계층의 최상단에 위치해야 한다.
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = rallyTabRowScreens,
                    onTabSelected = { screen -> currentScreen = screen },
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
                    Overview.screen()                  // define the actual UI to be displayed
                }
                composable(route = Accounts.route) {
                    Accounts.screen()
                }
                composable(route = Bills.route) {
                    Bills.screen()
                }
            }
        }
    }
}
