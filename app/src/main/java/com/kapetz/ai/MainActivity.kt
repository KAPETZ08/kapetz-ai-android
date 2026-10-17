
package com.kapetz.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkBg = Color(0xFF090C12)
private val CardBg = Color(0xFF101520)
private val ElectricBlue = Color(0xFF00E5FF)
private val MainText = Color(0xFFE2E8F0)

data class Species(
    val name: String,
    val group: String,
    val details: String
)

private val speciesList = listOf(
    Species("Tliltocatl albopilosus", "Tarantula", "New World terrestrial tarantula. Provide a secure enclosure, suitable substrate, a hide, and a water dish."),
    Species("Poecilotheria metallica", "Tarantula", "Arboreal Old World tarantula. Requires secure vertical space and careful handling avoidance."),
    Species("Grammostola pulchra", "Tarantula", "Terrestrial tarantula. Provide a stable hide, appropriate substrate, and access to water."),
    Species("Brachypelma hamorii", "Tarantula", "Terrestrial tarantula. Avoid excessive moisture and provide a secure enclosure."),
    Species("Heterometrus spinifer", "Scorpion", "Asian forest scorpion. Needs a secure enclosure, deep suitable substrate, hides, and access to water."),
    Species("Pandinus imperator", "Scorpion", "Large African scorpion. Provide suitable humidity, deep substrate, hides, and a water dish."),
    Species("Leiurus quinquestriatus", "Scorpion", "Medically significant venomous scorpion. Not suitable for beginners; use expert-only secure housing."),
    Species("Hottentotta gentili", "Scorpion", "Venomous scorpion. Confirm species-specific care and use expert-level safety precautions."),
    Species("Scolopendra subspinipes", "Centipede", "Fast, defensive centipede with a medically significant bite. Secure escape-proof housing is essential."),
    Species("Scolopendra dehaani", "Centipede", "Large defensive centipede. Requires a secure enclosure and species-appropriate humidity."),
    Species("Leopard Gecko", "Reptile", "Provide a temperature gradient, suitable hides, calcium supplementation, and a species-appropriate diet."),
    Species("Ball Python", "Reptile", "Requires a secure enclosure, correct temperature gradient, humidity, hides, and suitable prey.")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KapetzApp()
        }
    }
}

@Composable
fun KapetzApp() {
    var search by remember { mutableStateOf("") }
    var selectedGroup by remember { mutableStateOf("All") }
    var language by remember { mutableStateOf("English") }
    var selectedSpecies by remember { mutableStateOf<Species?>(null) }

    val groups = listOf("All", "Tarantula", "Scorpion", "Centipede", "Reptile")
    val filtered = speciesList.filter {
        (selectedGroup == "All" || it.group == selectedGroup) &&
        (it.name.contains(search, ignoreCase = true) ||
         it.group.contains(search, ignoreCase = true))
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = ElectricBlue,
            background = DarkBg,
            surface = CardBg
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DarkBg
        ) {
            if (selectedSpecies == null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text(
                            "KAPETZ AI",
                            color = ElectricBlue,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            "EXOTIC PET CARE ASSISTANT",
                            color = MainText,
                            fontSize = 12.sp,
                            letterSpacing = 2.sp
                        )
                        Spacer(Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = language == "English",
                                onClick = { language = "English" },
                                label = { Text("English") }
                            )
                            FilterChip(
                                selected = language == "Tagalog",
                                onClick = { language = "Tagalog" },
                                label = { Text("Tagalog") }
                            )
                        }
                    }
                    item {
                        OutlinedTextField(
                            value = search,
                            onValueChange = { search = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(if (language == "Tagalog") "Hanapin ang species" else "Search species") },
                            singleLine = true
                        )
                    }
                    item {
                        Text(
                            if (language == "Tagalog") "MGA KATEGORYA" else "SPECIES CATEGORIES",
                            color = ElectricBlue,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            groups.forEach { group ->
                                FilterChip(
                                    selected = selectedGroup == group,
                                    onClick = { selectedGroup = group },
                                    label = { Text(group) }
                                )
                            }
                        }
                    }
                    item {
                        Text(
                            if (language == "Tagalog") "SPECIES LIBRARY" else "SPECIES LIBRARY",
                            color = ElectricBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (filtered.isEmpty()) {
                        item {
                            Text(
                                if (language == "Tagalog") "Walang nahanap. Subukan ang ibang pangalan." else "No species found. Try another search.",
                                color = MainText
                            )
                        }
                    }
                    items(filtered) { species ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = CardBg),
                            onClick = { selectedSpecies = species }
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(species.name, color = MainText, fontWeight = FontWeight.Bold)
                                Text(species.group, color = ElectricBlue, fontSize = 12.sp)
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    if (language == "Tagalog") "Pindutin para makita ang care notes." else "Tap to view care notes.",
                                    color = MainText,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                    item {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Care information is a starting point. Verify species-specific requirements with reliable sources.",
                            color = MainText,
                            fontSize = 12.sp
                        )
                    }
                }
            } else {
                val species = selectedSpecies!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextButton(onClick = { selectedSpecies = null }) {
                        Text("← Back to library", color = ElectricBlue)
                    }
                    Text(species.name, color = ElectricBlue, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    Text(species.group.uppercase(), color = MainText, letterSpacing = 2.sp)
                    Text(species.details, color = MainText, fontSize = 16.sp)
                    Text(
                        "Important: Care needs vary by species, age, and environment. Confirm details before making husbandry changes.",
                        color = MainText,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
