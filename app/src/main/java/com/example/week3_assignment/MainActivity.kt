
package com.example.week3_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.week3_assignment.ui.screen.SmartHomeScreen
import com.example.week3_assignment.viewmodel.SmartHomeViewModel

class MainActivity : ComponentActivity() {

    private val smartHomeViewModel:
            SmartHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val uiState by smartHomeViewModel
                .uiState
                .collectAsState()

            MaterialTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {

                        SmartHomeScreen(
                            uiState = uiState,
                            onSearchChanged =
                                smartHomeViewModel::onSearchQueryChanged,
                            onRoomSelected =
                                smartHomeViewModel::onRoomSelected,
                            onDeviceToggle =
                                smartHomeViewModel::onDeviceToggle
                        )
                    }
                }
            }
        }
    }
}
