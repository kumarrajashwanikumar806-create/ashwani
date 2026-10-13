package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.KishanBottomNav
import com.example.ui.components.KishanTopBar
import com.example.ui.components.LanguageSelectionDialog
import com.example.ui.screens.AiAssistantScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MandiAndCropScreen
import com.example.ui.screens.TasksScreen
import com.example.ui.screens.ToolsAndLedgerScreen
import com.example.ui.theme.KishanBandhuTheme
import com.example.viewmodel.KishanBandhuViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: KishanBandhuViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            val language by viewModel.language.collectAsState()
            val currentTab by viewModel.selectedTab.collectAsState()
            val isLoggedIn by viewModel.isLoggedIn.collectAsState()
            val showLanguagePicker by viewModel.showLanguagePicker.collectAsState()

            // Welcome greeting on launch
            LaunchedEffect(Unit) {
                viewModel.speakGreeting()
            }

            // Back handler: return to Dashboard if on other tab
            BackHandler(enabled = isLoggedIn && currentTab != 0) {
                viewModel.setTab(0)
            }

            KishanBandhuTheme(darkTheme = isDarkMode) {
                if (!isLoggedIn) {
                    LoginScreen(viewModel = viewModel)
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            KishanTopBar(
                                language = language,
                                isDarkMode = isDarkMode,
                                onToggleLanguage = { viewModel.openLanguagePicker() },
                                onToggleDarkMode = { viewModel.toggleDarkMode() },
                                onSpeakGreeting = { viewModel.speakGreeting() },
                                onProfileClick = { viewModel.logout() }
                            )
                        },
                        bottomBar = {
                            KishanBottomNav(
                                selectedIndex = currentTab,
                                language = language,
                                onTabSelected = { viewModel.setTab(it) }
                            )
                        },
                        containerColor = MaterialTheme.colorScheme.background
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            when (currentTab) {
                                0 -> DashboardScreen(viewModel = viewModel)
                                1 -> TasksScreen(viewModel = viewModel)
                                2 -> AiAssistantScreen(viewModel = viewModel)
                                3 -> MandiAndCropScreen(viewModel = viewModel)
                                4 -> ToolsAndLedgerScreen(viewModel = viewModel)
                            }
                        }
                    }
                }

                if (showLanguagePicker) {
                    LanguageSelectionDialog(
                        currentLanguage = language,
                        onDismiss = { viewModel.closeLanguagePicker() },
                        onSelectLanguage = { viewModel.selectLanguage(it) }
                    )
                }
            }
        }
    }
}
