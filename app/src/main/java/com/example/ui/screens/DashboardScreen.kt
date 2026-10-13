package com.example.ui.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.AppLanguage
import com.example.data.TaskCategory
import com.example.ui.components.AgroGlassCard
import com.example.ui.components.InteractiveCategoryProgressChart
import com.example.ui.theme.AgroGoldTertiary
import com.example.ui.theme.AgroGreenPrimary
import com.example.ui.theme.AgroGreenSecondary
import com.example.ui.theme.AlertRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WeatherRainBlue
import com.example.ui.theme.WheatAmber
import com.example.viewmodel.KishanBandhuViewModel

@Composable
fun DashboardScreen(
    viewModel: KishanBandhuViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val language by viewModel.language.collectAsState()
    val weather by viewModel.weather.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val isSpeaking by viewModel.isTtsSpeaking.collectAsState()
    val currentSpeechTag by viewModel.currentSpeechTag.collectAsState()
    val nearbySeedStores by viewModel.nearbySeedStores.collectAsState()
    val mandiPrices by viewModel.mandiPrices.collectAsState()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {
        viewModel.openGoogleMapsNearSeeds(context)
    }

    val completedCount = tasks.count { it.isDone }
    val totalCount = tasks.size
    val totalProgress = if (totalCount > 0) completedCount.toFloat() / totalCount.toFloat() else 0f

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Farm Landscape Banner
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_farm_banner),
                        contentDescription = "Farm Hero Banner",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.75f)
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = if (language == AppLanguage.HINDI) "राम राम किसान भाई! 🌾" else "Ram Ram Kishan Bhai! 🌾",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = if (language == AppLanguage.HINDI)
                                "डिजिटल तकनीक और AI से उन्नत बनेगी आपकी खेती"
                            else
                                "Modernizing agriculture with smart AI & technology",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )
                    }
                }
            }
        }

        // Live Weather & Climate Hub
        item {
            AgroGlassCard(
                borderColor = WeatherRainBlue.copy(alpha = 0.4f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CloudQueue,
                                contentDescription = "Weather",
                                tint = WeatherRainBlue,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) "मौसम व जलवायु केंद्र" else "Weather & Climate Hub",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Text(
                            text = "${weather.temperature}°C",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = WeatherRainBlue
                            )
                        )
                    }

                    // Weather TTS Listen Button & Temp
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${weather.city} • ${if (language == AppLanguage.HINDI) weather.conditionHi else weather.conditionEn}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )

                        val isWeatherSpeaking = isSpeaking && currentSpeechTag == "weather_update"
                        OutlinedButton(
                            onClick = { viewModel.toggleWeatherSpeech() },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (isWeatherSpeaking) AlertRed else WeatherRainBlue
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isWeatherSpeaking) AlertRed else WeatherRainBlue
                            ),
                            modifier = Modifier.testTag("tts_weather_button")
                        ) {
                            Icon(
                                imageVector = if (isWeatherSpeaking) Icons.Default.Stop else Icons.Default.VolumeUp,
                                contentDescription = "Listen Weather",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isWeatherSpeaking) {
                                    if (language == AppLanguage.HINDI) "रोकें" else "Stop"
                                } else {
                                    if (language == AppLanguage.HINDI) "🔊 मौसम सुनें" else "🔊 Listen"
                                },
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        WeatherMetric(
                            icon = Icons.Default.WaterDrop,
                            label = if (language == AppLanguage.HINDI) "वर्षा संभावना" else "Rain Chance",
                            value = "${weather.rainChance}%",
                            color = WeatherRainBlue
                        )
                        WeatherMetric(
                            icon = Icons.Default.CloudQueue,
                            label = if (language == AppLanguage.HINDI) "नमी (आर्द्रता)" else "Humidity",
                            value = "${weather.humidity}%",
                            color = AgroGreenPrimary
                        )
                        WeatherMetric(
                            icon = Icons.Default.Air,
                            label = if (language == AppLanguage.HINDI) "हवा की गति" else "Wind",
                            value = "${weather.windSpeedKmh} km/h",
                            color = AgroGoldTertiary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Smart Irrigation Alert Box with TTS
                    val isIrrigationSpeaking = isSpeaking && currentSpeechTag == "irrigation_alert"
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(WeatherRainBlue.copy(alpha = 0.12f))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (language == AppLanguage.HINDI)
                                        "🌧️ स्मार्ट सिंचाई परामर्श (Smart Irrigation)"
                                    else
                                        "🌧️ Smart Irrigation Alert",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = WeatherRainBlue
                                    ),
                                    modifier = Modifier.weight(1f)
                                )

                                IconButton(
                                    onClick = { viewModel.toggleSmartIrrigationSpeech() },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .testTag("tts_irrigation_button")
                                ) {
                                    Icon(
                                        imageVector = if (isIrrigationSpeaking) Icons.Default.Stop else Icons.Default.VolumeUp,
                                        contentDescription = "Listen Irrigation Advice",
                                        tint = if (isIrrigationSpeaking) AlertRed else WeatherRainBlue,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) weather.irrigationTipHi else weather.irrigationTipEn,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.triggerWeatherAlertNotification(context) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("weather_notification_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = WeatherRainBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == AppLanguage.HINDI)
                                "मौसम अलर्ट नोटिफिकेशन भेजें (अलर्ट टेस्ट)"
                            else
                                "Send Weather Notification Alert"
                        )
                    }
                }
            }
        }

        // Interactive Progress & Category Breakdown Charts
        item {
            AgroGlassCard(borderColor = AgroGreenPrimary.copy(alpha = 0.35f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "📊 फसल चक्र प्रगति चार्ट (Farm Progress)"
                        else
                            "📊 Crop Cycle Progress Charts",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "श्रेणीवार कार्य पूर्णता एवं खेत प्रबंधन स्थिति"
                        else
                            "Category completion breakdown and farm operations",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Overall Summary Ring
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (language == AppLanguage.HINDI) "कुल पूर्ण कार्य" else "Total Tasks Completed",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                text = "$completedCount / $totalCount ${if (language == AppLanguage.HINDI) "कार्य पूर्ण" else "done"}",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AgroGreenPrimary
                                )
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(68.dp)
                        ) {
                            CircularProgressIndicator(
                                progress = { totalProgress },
                                modifier = Modifier.fillMaxSize(),
                                color = AgroGreenPrimary,
                                strokeWidth = 6.dp,
                                trackColor = AgroGreenPrimary.copy(alpha = 0.15f)
                            )
                            Text(
                                text = "${(totalProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AgroGreenPrimary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Category breakdown cards
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        val categories = listOf(
                            Triple(TaskCategory.SOWING, AgroGreenPrimary, "बुवाई (Sowing)"),
                            Triple(TaskCategory.IRRIGATION, WeatherRainBlue, "सिंचाई (Irrigation)"),
                            Triple(TaskCategory.FERTILIZER, AgroGoldTertiary, "उर्वरक/खाद (Fertilizer)"),
                            Triple(TaskCategory.PEST_CONTROL, AlertRed, "कीट नियंत्रण (Pest Control)"),
                            Triple(TaskCategory.HARVESTING, WheatAmber, "कटाई (Harvesting)"),
                            Triple(TaskCategory.MANDI, AgroGreenSecondary, "मंडी बिक्री (Mandi)")
                        )

                        categories.forEach { (cat, color, label) ->
                            val catTasks = tasks.filter { it.category == cat }
                            val catDone = catTasks.count { it.isDone }
                            val catTotal = catTasks.size
                            InteractiveCategoryProgressChart(
                                categoryTitle = if (language == AppLanguage.HINDI) cat.labelHi else cat.labelEn,
                                completed = catDone,
                                total = if (catTotal == 0) 1 else catTotal,
                                accentColor = color
                            )
                        }
                    }
                }
            }
        }

        // Quick Actions Grid
        item {
            Text(
                text = if (language == AppLanguage.HINDI) "⚡ त्वरित सेवाएं (Quick Actions)" else "⚡ Quick Actions",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionButton(
                    icon = Icons.Default.SmartToy,
                    label = if (language == AppLanguage.HINDI) "AI सहायक" else "AI Chat",
                    color = AgroGreenPrimary,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.setTab(2) // Kisan AI
                }

                QuickActionButton(
                    icon = Icons.Default.DocumentScanner,
                    label = if (language == AppLanguage.HINDI) "रोग जांच" else "Disease Scan",
                    color = AlertRed,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.setTab(2) // Disease detector inside AI
                }

                QuickActionButton(
                    icon = Icons.Default.Storefront,
                    label = if (language == AppLanguage.HINDI) "मंडी भाव" else "Mandi Rates",
                    color = AgroGoldTertiary,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.setTab(3) // Mandi
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionButton(
                    icon = Icons.Default.Calculate,
                    label = if (language == AppLanguage.HINDI) "खाद कैलकुलेटर" else "Fertilizer Calc",
                    color = AgroGreenSecondary,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.setTab(4) // Tools & Ledger
                }

                QuickActionButton(
                    icon = Icons.Default.ReceiptLong,
                    label = if (language == AppLanguage.HINDI) "बहीखाता / बिल" else "Kisan Bill",
                    color = WeatherRainBlue,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.setTab(4)
                }

                QuickActionButton(
                    icon = Icons.Default.PlayCircleOutline,
                    label = if (language == AppLanguage.HINDI) "कृषि वीडियो" else "Video Guides",
                    color = Color(0xFFD32F2F),
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.setTab(4)
                }
            }
        }

        // Live Mandi Rates & MSP Tracker Preview Card
        item {
            AgroGlassCard(borderColor = AgroGreenSecondary.copy(alpha = 0.45f)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Storefront,
                                contentDescription = null,
                                tint = AgroGreenPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) "मंडी भाव व MSP लाइव ट्रैकर" else "Mandi Rates & MSP Tracker",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        OutlinedButton(
                            onClick = { viewModel.setTab(3) },
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (language == AppLanguage.HINDI) "सभी 12 फसलें →" else "View All →",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(mandiPrices.take(6)) { item ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                border = BorderStroke(
                                    1.dp,
                                    if (item.isBullish) SuccessGreen.copy(alpha = 0.4f) else AlertRed.copy(alpha = 0.4f)
                                ),
                                modifier = Modifier
                                    .width(175.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { viewModel.setTab(3) }
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (language == AppLanguage.HINDI) item.cropHi.substringBefore(" (") else item.cropEn.substringBefore(" ("),
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.weight(1f)
                                        )

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = if (item.isBullish) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                                                contentDescription = null,
                                                tint = if (item.isBullish) SuccessGreen else AlertRed,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(2.dp))
                                            Text(
                                                text = "${if (item.changePercent > 0) "+" else ""}${item.changePercent}%",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (item.isBullish) SuccessGreen else AlertRed
                                                )
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "₹${item.currentPrice}/qtl",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = AgroGreenPrimary
                                        )
                                    )

                                    if (item.mspPrice > 0) {
                                        val diff = item.currentPrice - item.mspPrice
                                        Text(
                                            text = if (diff >= 0) "MSP: ₹${item.mspPrice} (+₹$diff)" else "MSP: ₹${item.mspPrice} (-₹${-diff})",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = if (diff >= 0) SuccessGreen else AlertRed,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    } else {
                                        Text(
                                            text = "खुली मंडी दर",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Smart Calendar Sync Card
        item {
            AgroGlassCard(borderColor = AgroGreenSecondary.copy(alpha = 0.4f)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(AgroGreenSecondary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = AgroGreenSecondary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (language == AppLanguage.HINDI) "स्मार्ट कैलेंडर सिंक (Google Calendar)" else "Smart Google Calendar Sync",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (language == AppLanguage.HINDI)
                                "कृषि कार्यों और सिंचाई की तारीखों को मोबाइल कैलेंडर में जोड़ें"
                            else
                                "Add farm schedules & irrigation reminders to your Calendar",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = { viewModel.setTab(1) }, // Go to Tasks screen
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (language == AppLanguage.HINDI) "खोलें" else "Sync")
                    }
                }
            }
        }

        // Near Seeds Store & Google Maps Section
        item {
            AgroGlassCard(borderColor = AgroGreenPrimary.copy(alpha = 0.5f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PinDrop,
                                contentDescription = "Near Seeds Store",
                                tint = AgroGreenPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = if (language == AppLanguage.HINDI) "नजदीकी बीज एवं खाद भंडार" else "Near Seeds & Fertilizer Store",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "Near Seeds Store on Google Maps",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Button(
                            onClick = {
                                locationPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AgroGreenPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("open_seeds_maps_button")
                        ) {
                            Icon(Icons.Default.NearMe, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (language == AppLanguage.HINDI) "गूगल मैप" else "Maps")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "लोकेशन अनुमति देने पर आपके सबसे नजदीकी प्रमाणित बीज केंद्र खुलेंगे:"
                        else
                            "Allow location to find certified seed stores nearest to your farm:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        nearbySeedStores.forEach { store ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = if (language == AppLanguage.HINDI) store.nameHi else store.nameEn,
                                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = "📍 ${if (language == AppLanguage.HINDI) store.addressHi else store.addressEn} • ${store.distanceKm}",
                                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }

                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = AgroGreenPrimary.copy(alpha = 0.12f)
                                        ) {
                                            Text(
                                                text = store.rating,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = AgroGreenPrimary
                                                )
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        items(store.certifiedSeeds) { seed ->
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = AgroGoldTertiary.copy(alpha = 0.1f)
                                            ) {
                                                Text(
                                                    text = "🌱 $seed",
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = { viewModel.speakStoreDetails(store) },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.VolumeUp,
                                                contentDescription = "Listen details",
                                                tint = AgroGreenPrimary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(6.dp))

                                        OutlinedButton(
                                            onClick = {
                                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${store.phone}"))
                                                context.startActivity(intent)
                                            },
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.height(34.dp)
                                        ) {
                                            Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(if (language == AppLanguage.HINDI) "कॉल" else "Call", fontSize = 11.sp)
                                        }

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Button(
                                            onClick = { viewModel.openGoogleMapsForStore(context, store) },
                                            colors = ButtonDefaults.buttonColors(containerColor = AgroGreenPrimary),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.height(34.dp)
                                        ) {
                                            Icon(Icons.Default.PinDrop, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(if (language == AppLanguage.HINDI) "मैप पर देखें" else "Map", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Emergency Kisan Helpline 1551
        item {
            AgroGlassCard(
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                borderColor = Color.Gray.copy(alpha = 0.3f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Helpline",
                            tint = AgroGreenPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (language == AppLanguage.HINDI) "किसान कॉल सेंटर (निःशुल्क)" else "Kisan Call Center (Toll Free)",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "1800-180-1551 / 1551",
                                style = MaterialTheme.typography.bodySmall.copy(color = AgroGreenPrimary),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:1551"))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AgroGreenPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = if (language == AppLanguage.HINDI) "कॉल करें" else "Call")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun WeatherMetric(
    icon: ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = color
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun QuickActionButton(
    icon: ImageVector,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.12f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }
    }
}
