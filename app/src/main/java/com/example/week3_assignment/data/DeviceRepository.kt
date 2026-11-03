
package com.example.week3_assignment.data

import com.example.week3_assignment.model.DeviceCategory
import com.example.week3_assignment.model.SmartDevice

object DeviceRepository {

    fun getInitialDevices(): List<SmartDevice> {
        return listOf(
            SmartDevice(
                id = 1,
                name = "Köşe Lambası",
                category = DeviceCategory.LIGHT,
                room = "Salon",
                isOnline = true,
                isOn = true,
                powerWatts = 15.0
            ),
            SmartDevice(
                id = 2,
                name = "Tavan Avizesi",
                category = DeviceCategory.LIGHT,
                room = "Salon",
                isOnline = true,
                isOn = false,
                powerWatts = 60.0
            ),
            SmartDevice(
                id = 3,
                name = "Bahçe Kamerası",
                category = DeviceCategory.CAMERA,
                room = "Bahçe",
                isOnline = true,
                isOn = true,
                powerWatts = 8.5
            ),
            SmartDevice(
                id = 4,
                name = "Giriş Kamerası",
                category = DeviceCategory.CAMERA,
                room = "Antre",
                isOnline = true,
                isOn = true,
                powerWatts = 8.5
            ),
            SmartDevice(
                id = 5,
                name = "Merkezi Termostat",
                category = DeviceCategory.THERMOSTAT,
                room = "Salon",
                isOnline = true,
                isOn = true,
                powerWatts = 12.0
            ),
            SmartDevice(
                id = 6,
                name = "Yatak Odası Kliması",
                category = DeviceCategory.THERMOSTAT,
                room = "Yatak Odası",
                isOnline = true,
                isOn = false,
                powerWatts = 1200.0
            ),
            SmartDevice(
                id = 7,
                name = "Mutfak Spotları",
                category = DeviceCategory.LIGHT,
                room = "Mutfak",
                isOnline = true,
                isOn = false,
                powerWatts = 45.0
            ),
            SmartDevice(
                id = 8,
                name = "Roborock S7",
                category = DeviceCategory.VACUUM,
                room = "Koridor",
                isOnline = true,
                isOn = true,
                powerWatts = 50.0
            ),
            SmartDevice(
                id = 9,
                name = "Balkon Aydınlatması",
                category = DeviceCategory.LIGHT,
                room = "Balkon",
                isOnline = false,
                isOn = false,
                powerWatts = 25.0
            ),
            SmartDevice(
                id = 10,
                name = "Çalışma Odası Lambası",
                category = DeviceCategory.LIGHT,
                room = "Ofis",
                isOnline = true,
                isOn = true,
                powerWatts = 12.0
            )
        )
    }
}
