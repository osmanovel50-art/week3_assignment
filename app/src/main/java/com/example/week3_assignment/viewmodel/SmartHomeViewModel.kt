
package com.example.week3_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week3_assignment.data.DeviceRepository
import com.example.week3_assignment.model.SmartDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SmartHomeUiState(
    val devices: List<SmartDevice> = emptyList(),
    val searchQuery: String = "",
    val selectedRoom: String = "Tümü",
    val rooms: List<String> = emptyList(),
    val totalPowerWatts: Double = 0.0
)

class SmartHomeViewModel : ViewModel() {

    private val initialDevices =
        DeviceRepository.getInitialDevices()

    private val _devices =
        MutableStateFlow(initialDevices)

    private val _searchQuery =
        MutableStateFlow("")

    private val _selectedRoom =
        MutableStateFlow("Tümü")

    private fun buildUiState(
        devices: List<SmartDevice>,
        query: String,
        room: String
    ): SmartHomeUiState {

        val filteredDevices = devices.filter { device ->
            device.name.contains(
                query.trim(),
                ignoreCase = true
            ) && (room == "Tümü" || device.room == room)
        }

        val totalPower = devices
            .filter { it.isOnline && it.isOn }
            .sumOf { it.powerWatts }

        val availableRooms =
            listOf("Tümü") +
                    devices.map { it.room }.distinct().sorted()

        return SmartHomeUiState(
            devices = filteredDevices,
            searchQuery = query,
            selectedRoom = room,
            rooms = availableRooms,
            totalPowerWatts = totalPower
        )
    }

    val uiState: StateFlow<SmartHomeUiState> =
        combine(
            _devices,
            _searchQuery,
            _selectedRoom
        ) { devices, query, room ->

            buildUiState(devices, query, room)

        }
            .flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = buildUiState(
                    initialDevices,
                    "",
                    "Tümü"
                )
            )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onRoomSelected(room: String) {
        _selectedRoom.value = room
    }

    fun onDeviceToggle(deviceId: Int) {
        viewModelScope.launch(Dispatchers.Default) {

            _devices.update { devices ->
                devices.map { device ->

                    if (
                        device.id == deviceId &&
                        device.isOnline
                    ) {
                        device.copy(isOn = !device.isOn)
                    } else {
                        device
                    }
                }
            }
        }
    }
}
