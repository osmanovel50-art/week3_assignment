
package com.example.week3_assignment.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.week3_assignment.model.SmartDevice
import com.example.week3_assignment.viewmodel.SmartHomeUiState

@Composable
fun SmartHomeScreen(
    uiState: SmartHomeUiState,
    onSearchChanged: (String) -> Unit,
    onRoomSelected: (String) -> Unit,
    onDeviceToggle: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Akıllı Ev Kontrol Merkezi",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        EnergyCard(
            totalPower = uiState.totalPowerWatts
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchChanged,
            label = { Text("Cihaz ara") },
            placeholder = { Text("Cihaz adını yazın...") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.rooms) { room ->
                FilterChip(
                    selected = uiState.selectedRoom == room,
                    onClick = { onRoomSelected(room) },
                    label = { Text(room) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cihazlar",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.devices.isEmpty()) {
            Text(
                text = "Aramanıza uygun cihaz bulunamadı.",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(
                    items = uiState.devices,
                    key = { device -> device.id }
                ) { device ->
                    DeviceCard(
                        device = device,
                        onToggle = {
                            onDeviceToggle(device.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EnergyCard(totalPower: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Toplam Anlık Güç Tüketimi",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$totalPower W",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Aktif ve çevrimiçi cihazlar",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun DeviceCard(
    device: SmartDevice,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Oda: ${device.room}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Kategori: ${device.category}",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Tüketim: ${device.powerWatts} W",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = if (device.isOnline)
                        "Çevrimiçi"
                    else
                        "Çevrimdışı",
                    color = if (device.isOnline)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error
                )
            }

            Switch(
                checked = device.isOn,
                onCheckedChange = { onToggle() },
                enabled = device.isOnline
            )
        }
    }
}
