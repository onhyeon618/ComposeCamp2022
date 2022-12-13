package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest() {
        // 우리가 컨트롤할 수 있는 더미데이터
        val allScreens = RallyScreen.values().toList()

        // setContent를 이용하여 어떤 compose를 테스트할 건지 설정
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        // composeTestRule{.finder}{.assertion}{.action}
        // 테스트는 다음의 규칙에 따라서 진행된다.
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        // Semantics tree를 출력해 볼 수 있다.
        composeTestRule.onRoot().printToLog("currentLabelExists")

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertExists()
    }
}