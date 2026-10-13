package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.CropInfo
import com.example.data.MandiPrice
import com.example.ui.components.AgroGlassCard
import com.example.ui.theme.AgroGoldTertiary
import com.example.ui.theme.AgroGreenPrimary
import com.example.ui.theme.AgroGreenSecondary
import com.example.ui.theme.AlertRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WeatherRainBlue
import com.example.viewmodel.KishanBandhuViewModel

@Composable
fun MandiAndCropScreen(
    viewModel: KishanBandhuViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    var selectedSection by remember { mutableStateOf(0) } // 0: Mandi Rates & MSP, 1: Crop Guide

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Tab Toggle
        TabRow(
            selectedTabIndex = selectedSection,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = AgroGreenPrimary,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = selectedSection == 0,
                onClick = { selectedSection = 0 },
                text = {
                    Text(
                        text = if (language == AppLanguage.HINDI) "⚖️ मंडी भाव व MSP" else "⚖️ Mandi Rates & MSP",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
            Tab(
                selected = selectedSection == 1,
                onClick = { selectedSection = 1 },
                text = {
                    Text(
                        text = if (language == AppLanguage.HINDI) "🌾 फसल गाइड व समय-चक्र" else "🌾 Crop Guide & Timeline",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (selectedSection == 0) {
            MandiRatesSection(viewModel = viewModel, language = language)
        } else {
            CropGuideSection(viewModel = viewModel, language = language)
        }
    }
}

@Composable
private fun MandiRatesSection(
    viewModel: KishanBandhuViewModel,
    language: AppLanguage
) {
    val mandiPrices by viewModel.mandiPrices.collectAsState()
    val searchQuery by viewModel.mandiSearchQuery.collectAsState()
    val categoryFilter by viewModel.mandiCategoryFilter.collectAsState()
    val trendFilter by viewModel.mandiTrendFilter.collectAsState()
    val isRefreshing by viewModel.isMandiRefreshing.collectAsState()
    val lastUpdateTime by viewModel.lastMandiUpdateTime.collectAsState()
    val isSpeaking by viewModel.isTtsSpeaking.collectAsState()
    val currentSpeechTag by viewModel.currentSpeechTag.collectAsState()

    var calculatorCrop by remember { mutableStateOf<MandiPrice?>(null) }

    // Filter logic
    val filteredPrices = remember(mandiPrices, searchQuery, categoryFilter, trendFilter) {
        mandiPrices.filter { item ->
            val matchesSearch = if (searchQuery.isBlank()) {
                true
            } else {
                item.cropHi.contains(searchQuery, ignoreCase = true) ||
                        item.cropEn.contains(searchQuery, ignoreCase = true) ||
                        item.mandiHi.contains(searchQuery, ignoreCase = true) ||
                        item.mandiEn.contains(searchQuery, ignoreCase = true)
            }

            val matchesCategory = if (categoryFilter == "ALL") {
                true
            } else {
                item.category == categoryFilter
            }

            val matchesTrend = when (trendFilter) {
                "BULLISH" -> item.isBullish
                "BEARISH" -> !item.isBullish
                "ABOVE_MSP" -> item.mspPrice > 0 && item.currentPrice >= item.mspPrice
                else -> true
            }

            matchesSearch && matchesCategory && matchesTrend
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Live Header & Market Board
        item {
            MandiHeaderBoard(
                language = language,
                lastUpdateTime = lastUpdateTime,
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refreshMandiPrices() },
                onOpenCalculator = { calculatorCrop = mandiPrices.firstOrNull() }
            )
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setMandiSearchQuery(it) },
                placeholder = {
                    Text(
                        text = if (language == AppLanguage.HINDI) "फसल या मंडी खोजें (उदा. गेहूं, करनाल)..." else "Search crop or market (e.g. Wheat, Karnal)...",
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Mandi",
                        tint = AgroGreenPrimary
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setMandiSearchQuery("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AgroGreenPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mandi_search_input")
            )
        }

        // Category Filter Chips
        item {
            val categories = listOf(
                "ALL" to (if (language == AppLanguage.HINDI) "सभी फसलें" else "All Crops"),
                "अनाज" to (if (language == AppLanguage.HINDI) "🌾 अनाज (Cereals)" else "🌾 Cereals"),
                "दलहन" to (if (language == AppLanguage.HINDI) "🌱 दलहन (Pulses)" else "🌱 Pulses"),
                "तिलहन" to (if (language == AppLanguage.HINDI) "🌻 तिलहन (Oilseeds)" else "🌻 Oilseeds"),
                "नकदी" to (if (language == AppLanguage.HINDI) "💰 नकदी (Cash Crops)" else "💰 Cash Crops"),
                "सब्जियां" to (if (language == AppLanguage.HINDI) "🥦 सब्जियां (Vegetables)" else "🥦 Vegetables"),
                "मसाले" to (if (language == AppLanguage.HINDI) "🧄 मसाले (Spices)" else "🧄 Spices")
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { (catKey, catLabel) ->
                    val isSelected = categoryFilter == catKey
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) AgroGreenPrimary else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel.setMandiCategoryFilter(catKey) }
                    ) {
                        Text(
                            text = catLabel,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }

        // Trend Quick Filters (Up / Down / Above MSP)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TrendFilterChip(
                    label = if (language == AppLanguage.HINDI) "सभी भाव" else "All Trends",
                    isSelected = trendFilter == "ALL",
                    onClick = { viewModel.setMandiTrendFilter("ALL") },
                    modifier = Modifier.weight(1f)
                )
                TrendFilterChip(
                    label = if (language == AppLanguage.HINDI) "🟢 तेजी (Up)" else "🟢 Bullish (Up)",
                    isSelected = trendFilter == "BULLISH",
                    onClick = { viewModel.setMandiTrendFilter("BULLISH") },
                    modifier = Modifier.weight(1f)
                )
                TrendFilterChip(
                    label = if (language == AppLanguage.HINDI) "🔴 मंदी (Down)" else "🔴 Bearish (Down)",
                    isSelected = trendFilter == "BEARISH",
                    onClick = { viewModel.setMandiTrendFilter("BEARISH") },
                    modifier = Modifier.weight(1f)
                )
                TrendFilterChip(
                    label = if (language == AppLanguage.HINDI) "⭐ MSP+" else "⭐ Above MSP",
                    isSelected = trendFilter == "ABOVE_MSP",
                    onClick = { viewModel.setMandiTrendFilter("ABOVE_MSP") },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Empty state if nothing matches
        if (filteredPrices.isEmpty()) {
            item {
                AgroGlassCard(borderColor = AgroGreenSecondary.copy(alpha = 0.3f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🔍", fontSize = 36.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (language == AppLanguage.HINDI) "कोई मंडी भाव नहीं मिला" else "No matching Mandi prices found",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (language == AppLanguage.HINDI) "कृपया अन्य फसल या फ़िल्टर चुनें" else "Try clearing your search or filters",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Mandi Cards
        items(filteredPrices, key = { it.id }) { item ->
            val speechTag = "mandi_rate_${item.id}"
            val isThisSpeaking = isSpeaking && currentSpeechTag == speechTag

            MandiPriceCard(
                item = item,
                language = language,
                isSpeaking = isThisSpeaking,
                onToggleSpeech = { viewModel.toggleMandiSpeech(item) },
                onOpenCalculator = { calculatorCrop = item }
            )
        }
    }

    // Profit Calculator Dialog
    calculatorCrop?.let { crop ->
        MandiCalculatorDialog(
            crop = crop,
            language = language,
            onDismiss = { calculatorCrop = null }
        )
    }
}

@Composable
private fun MandiHeaderBoard(
    language: AppLanguage,
    lastUpdateTime: String,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onOpenCalculator: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "spin")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    AgroGlassCard(borderColor = AgroGreenSecondary.copy(alpha = 0.4f)) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(AlertRed)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (language == AppLanguage.HINDI) "LIVE मंडी भाव व MSP बोर्ड" else "LIVE Mandi & MSP Price Board",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onRefresh,
                        enabled = !isRefreshing,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Mandi Prices",
                            tint = AgroGreenPrimary,
                            modifier = if (isRefreshing) Modifier.rotate(rotation) else Modifier
                        )
                    }

                    OutlinedButton(
                        onClick = onOpenCalculator,
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        border = BorderStroke(1.dp, AgroGreenPrimary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = AgroGreenPrimary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (language == AppLanguage.HINDI) "मुनाफा कैलकुलेटर" else "Calculator",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = AgroGreenPrimary
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⏱️ $lastUpdateTime",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = if (language == AppLanguage.HINDI) "दैनिक ई-नाम / एपीएमसी सिमुलेशन" else "Daily e-NAM / APMC Tracker",
                    style = MaterialTheme.typography.labelSmall,
                    color = AgroGreenPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun TrendFilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) AgroGreenPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) AgroGreenPrimary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        ),
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) AgroGreenPrimary else MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.padding(vertical = 6.dp)
        )
    }
}

@Composable
private fun MandiPriceCard(
    item: MandiPrice,
    language: AppLanguage,
    isSpeaking: Boolean,
    onToggleSpeech: () -> Unit,
    onOpenCalculator: () -> Unit
) {
    AgroGlassCard(
        borderColor = if (item.isBullish) SuccessGreen.copy(alpha = 0.45f) else AlertRed.copy(alpha = 0.35f)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Top Row: Crop Name, Category, Up/Down Trend Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (language == AppLanguage.HINDI) item.cropHi else item.cropEn,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = AgroGreenPrimary.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = item.category,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = AgroGreenPrimary
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "📍 ${if (language == AppLanguage.HINDI) item.mandiHi else item.mandiEn}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Visual Up/Down Indicator Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (item.isBullish) SuccessGreen.copy(alpha = 0.14f) else AlertRed.copy(alpha = 0.14f),
                    border = BorderStroke(
                        1.dp,
                        if (item.isBullish) SuccessGreen.copy(alpha = 0.5f) else AlertRed.copy(alpha = 0.5f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (item.isBullish) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                            contentDescription = if (item.isBullish) "Price Up" else "Price Down",
                            tint = if (item.isBullish) SuccessGreen else AlertRed,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${if (item.changePercent > 0) "+" else ""}${item.changePercent}% ${if (item.isBullish) (if (language == AppLanguage.HINDI) "तेजी" else "Up") else (if (language == AppLanguage.HINDI) "मंदी" else "Down")}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = if (item.isBullish) SuccessGreen else AlertRed
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Price Row & MSP Comparison Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = if (language == AppLanguage.HINDI) "ताजा मंडी भाव (Modal Price)" else "Market Price",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "₹${item.currentPrice}",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Black,
                                color = AgroGreenPrimary
                            )
                        )
                        Text(
                            text = if (language == AppLanguage.HINDI) " /क्विंटल" else " /Qtl",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                }

                // Daily Range & Arrivals
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "दैनिक आवक: ${item.arrivalsTons} टन",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "रेंज: ₹${item.minPrice} - ₹${item.maxPrice}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
            Spacer(modifier = Modifier.height(8.dp))

            // MSP Comparison Badge
            if (item.mspPrice > 0) {
                val diff = item.currentPrice - item.mspPrice
                val isAboveMsp = diff >= 0

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isAboveMsp) SuccessGreen.copy(alpha = 0.08f) else AlertRed.copy(alpha = 0.08f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🏛️ सरकारी MSP: ₹${item.mspPrice}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Text(
                            text = if (isAboveMsp) {
                                if (language == AppLanguage.HINDI) "+₹$diff (MSP से अधिक मुनाफा! 🟢)" else "+₹$diff above MSP 🟢"
                            } else {
                                if (language == AppLanguage.HINDI) "₹$diff (MSP से कम, सतर्क रहें 🔴)" else "₹$diff below MSP 🔴"
                            },
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = if (isAboveMsp) SuccessGreen else AlertRed
                            )
                        )
                    }
                }
            } else {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = WeatherRainBlue.copy(alpha = 0.08f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "🥬 खुली सब्जी मंडी दर (मांग व आपूर्ति आधारित स्वतंत्र भाव)"
                        else
                            "🥬 Open Market Arrival (Demand & supply based)",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 7-Day Mini Trend Sparkline
            WeeklyTrendSparkline(weeklyTrend = item.weeklyTrend, isBullish = item.isBullish)

            Spacer(modifier = Modifier.height(8.dp))

            // AI Predictor Advice
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (item.isBullish) SuccessGreen.copy(alpha = 0.06f) else AlertRed.copy(alpha = 0.06f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "💡 ${if (language == AppLanguage.HINDI) item.adviceHi else item.adviceEn}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action Row: Audio TTS Readout + Profit Calculator shortcut
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onToggleSpeech,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, if (isSpeaking) AlertRed else AgroGreenPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (isSpeaking) AlertRed else AgroGreenPrimary
                    ),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("tts_mandi_${item.id}")
                ) {
                    Icon(
                        imageVector = if (isSpeaking) Icons.Default.Stop else Icons.Default.VolumeUp,
                        contentDescription = "Speak Mandi Report",
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isSpeaking) {
                            if (language == AppLanguage.HINDI) "रोकें" else "Stop"
                        } else {
                            if (language == AppLanguage.HINDI) "🔊 भाव रिपोर्ट सुनें" else "🔊 Listen"
                        },
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Button(
                    onClick = onOpenCalculator,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AgroGreenPrimary),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (language == AppLanguage.HINDI) "मुनाफा जोड़ें" else "Estimate",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Composable
private fun WeeklyTrendSparkline(
    weeklyTrend: List<Int>,
    isBullish: Boolean
) {
    if (weeklyTrend.isEmpty()) return

    val min = weeklyTrend.minOrNull() ?: 1
    val max = weeklyTrend.maxOrNull() ?: 1
    val range = (max - min).coerceAtLeast(1)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "📊 6-दिवसीय भाव ट्रेंड (Past 6 Days Trend):",
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            weeklyTrend.forEachIndexed { index, price ->
                val ratio = (price - min).toFloat() / range.toFloat()
                val barHeight = (12 + (ratio * 24)).dp
                val isToday = index == weeklyTrend.size - 1

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "₹$price",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 8.sp,
                            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                            color = if (isToday) (if (isBullish) SuccessGreen else AlertRed) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(barHeight)
                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                            .background(
                                if (isToday) {
                                    if (isBullish) SuccessGreen else AlertRed
                                } else {
                                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                }
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun MandiCalculatorDialog(
    crop: MandiPrice,
    language: AppLanguage,
    onDismiss: () -> Unit
) {
    var quantityInput by remember { mutableStateOf("25") }
    val quantity = quantityInput.toDoubleOrNull() ?: 0.0

    val mandiTotal = (quantity * crop.currentPrice).toLong()
    val mspTotal = if (crop.mspPrice > 0) (quantity * crop.mspPrice).toLong() else 0L
    val profitDiff = if (crop.mspPrice > 0) mandiTotal - mspTotal else 0L

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = null,
                    tint = AgroGreenPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (language == AppLanguage.HINDI) "मंडी मुनाफा कैलकुलेटर" else "Mandi Profit Calculator",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${if (language == AppLanguage.HINDI) crop.cropHi else crop.cropEn} (📍 ${if (language == AppLanguage.HINDI) crop.mandiHi else crop.mandiEn})",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = AgroGreenPrimary
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = quantityInput,
                    onValueChange = { quantityInput = it },
                    label = {
                        Text(if (language == AppLanguage.HINDI) "उपज मात्रा (क्विंटल में)" else "Crop Quantity (in Quintals)")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (language == AppLanguage.HINDI) "मंडी भाव दर:" else "Mandi Rate:",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "₹${crop.currentPrice} /क्विंटल",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (language == AppLanguage.HINDI) "कुल संभावित आय:" else "Total Mandi Payout:",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "₹$mandiTotal",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = AgroGreenPrimary
                                )
                            )
                        }

                        if (crop.mspPrice > 0) {
                            Spacer(modifier = Modifier.height(6.dp))
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (language == AppLanguage.HINDI) "सरकारी MSP दर:" else "Govt MSP Rate:",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "₹${crop.mspPrice} /क्विंटल",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (language == AppLanguage.HINDI) "MSP पर कुल मूल्य:" else "Value at MSP:",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "₹$mspTotal",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (profitDiff >= 0) {
                                        if (language == AppLanguage.HINDI) "अतिरिक्त मुनाफा (Net Gain):" else "Extra Profit above MSP:"
                                    } else {
                                        if (language == AppLanguage.HINDI) "MSP से कम अंतर (Loss):" else "Diff below MSP:"
                                    },
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (profitDiff >= 0) SuccessGreen else AlertRed
                                )
                                Text(
                                    text = if (profitDiff >= 0) "+₹$profitDiff" else "₹$profitDiff",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (profitDiff >= 0) SuccessGreen else AlertRed
                                    )
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = AgroGreenPrimary)
            ) {
                Text(if (language == AppLanguage.HINDI) "ठीक है (OK)" else "Close")
            }
        }
    )
}

@Composable
private fun CropGuideSection(
    viewModel: KishanBandhuViewModel,
    language: AppLanguage
) {
    val crops by viewModel.crops.collectAsState()
    val selectedCrop by viewModel.selectedCrop.collectAsState()
    val isSpeaking by viewModel.isTtsSpeaking.collectAsState()
    val currentSpeechTag by viewModel.currentSpeechTag.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Crop Selector Carousel
        item {
            Text(
                text = if (language == AppLanguage.HINDI) "फसल का चुनाव करें:" else "Select Crop Guide:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(crops) { crop ->
                    val isSelected = crop.id == selectedCrop.id
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) AgroGreenPrimary else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel.selectCrop(crop) }
                    ) {
                        Text(
                            text = if (language == AppLanguage.HINDI) crop.nameHi else crop.nameEn,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }

        // Details Overview Card
        item {
            AgroGlassCard(borderColor = AgroGreenPrimary.copy(alpha = 0.4f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (language == AppLanguage.HINDI) selectedCrop.nameHi else selectedCrop.nameEn,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = AgroGreenPrimary
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        val cropTag = "crop_advice_${selectedCrop.id}"
                        val isCropSpeaking = isSpeaking && currentSpeechTag == cropTag
                        OutlinedButton(
                            onClick = { viewModel.toggleCropAdviceSpeech(selectedCrop) },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (isCropSpeaking) AlertRed else AgroGreenPrimary
                            ),
                            border = BorderStroke(1.dp, if (isCropSpeaking) AlertRed else AgroGreenPrimary),
                            modifier = Modifier.testTag("tts_crop_advice_button")
                        ) {
                            Icon(
                                imageVector = if (isCropSpeaking) Icons.Default.Stop else Icons.Default.VolumeUp,
                                contentDescription = "Listen Crop Advice",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isCropSpeaking) {
                                    if (language == AppLanguage.HINDI) "रोकें" else "Stop"
                                } else {
                                    if (language == AppLanguage.HINDI) "🔊 सलाह सुनें" else "🔊 Listen"
                                },
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    CropPropertyRow(
                        label = if (language == AppLanguage.HINDI) "ऋतु व बुवाई समय:" else "Season & Sowing:",
                        value = if (language == AppLanguage.HINDI) selectedCrop.seasonHi else selectedCrop.seasonEn
                    )
                    CropPropertyRow(
                        label = if (language == AppLanguage.HINDI) "फसल की अवधि:" else "Crop Duration:",
                        value = selectedCrop.durationDays
                    )
                    CropPropertyRow(
                        label = if (language == AppLanguage.HINDI) "उपयुक्त मिट्टी:" else "Soil Type:",
                        value = if (language == AppLanguage.HINDI) selectedCrop.soilHi else selectedCrop.soilEn
                    )
                    CropPropertyRow(
                        label = if (language == AppLanguage.HINDI) "वर्षा व सिंचाई आवश्यकता:" else "Water & Rainfall:",
                        value = if (language == AppLanguage.HINDI) selectedCrop.rainfallHi else selectedCrop.rainfallEn
                    )
                    CropPropertyRow(
                        label = if (language == AppLanguage.HINDI) "अनुकूल तापमान:" else "Ideal Temperature:",
                        value = selectedCrop.idealTemp
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (language == AppLanguage.HINDI) "🧪 अनुशंसित खाद व उर्वरक मात्रा:" else "🧪 Recommended Fertilizer Dosage:",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (language == AppLanguage.HINDI) selectedCrop.fertilizerHi else selectedCrop.fertilizerEn,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Stage Timeline
        item {
            Text(
                text = if (language == AppLanguage.HINDI) "⏳ चरणबद्ध विकास एवं कार्य समय-चक्र (Cultivation Timeline):" else "⏳ Cultivation Stage Timeline:",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        items(selectedCrop.stages) { stage ->
            AgroGlassCard(
                borderColor = WeatherRainBlue.copy(alpha = 0.3f),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AgroGreenPrimary,
                        contentColor = Color.White
                    ) {
                        Text(
                            text = stage.daysRange,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (language == AppLanguage.HINDI) stage.stageNameHi else stage.stageNameEn,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (language == AppLanguage.HINDI) stage.activityHi else stage.activityEn,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CropPropertyRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
