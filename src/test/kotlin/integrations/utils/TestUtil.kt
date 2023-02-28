package integrations.utils

import com.springkotlin.vehicles.entity.FreeNow
import com.springkotlin.vehicles.entity.ShareNow

fun freeNowEntityList () = listOf(
    FreeNow(
        id = null,
        latitude = 53.5532316,
        longitude = 10.0087783,
        engineType = "ELECTRIC",
        state = "ACTIVE",
        licencePlate = "HHZ 23 H8006",
        condition = "GOOD"
    ),
    FreeNow(
        id = null,
        latitude = 54.5532657,
        longitude = 10.0174783,
        engineType = "ELECTRIC",
        state = "INACTIVE",
        licencePlate = "HHZ 22 H8018",
        condition = "GOOD"
    ),
    FreeNow(
        id = null,
        latitude = 52.5532316,
        longitude = 9.0087783,
        engineType = "PETROL",
        state = "INACTIVE",
        licencePlate = "HHZ 23 H8206",
        condition = "BAD"
    )
)

fun shareNowEntityList () = listOf(
    ShareNow(
        id = null,
        address = "Grosse Reichenstraße 7, 20457 Hamburg",
        longitude = 9.99622,
        latitude = 53.54847,
        engineType = "PETROL",
        condition = "BAD",
        fuel = 45,
        licencePlate = "HHZ 23 H8480"
    ),
    ShareNow(
        id = null,
        address = "Ring 2, 22043 Hamburg",
        longitude = 8.92298,
        latitude = 54.54347,
        engineType = "PETROL",
        condition = "GOOD",
        fuel = 45,
        licencePlate = "HHZ 23 H8480"
    ),
    ShareNow(
        id = null,
        address = "Spreenende 1 - 11, 22453 Hamburg (Umkreis 100m)",
        longitude = 12.99622,
        latitude = 57.34847,
        engineType = "ELECTRIC",
        condition = "GOOD",
        fuel = 95,
        licencePlate = "HHZ 23 H0290"
    ),
)