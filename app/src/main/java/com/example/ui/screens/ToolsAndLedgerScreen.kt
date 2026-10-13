package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.LedgerEntry
import com.example.data.SchemeInfo
import com.example.data.YoutubeCreator
import com.example.ui.components.AgroGlassCard
import com.example.ui.theme.AgroGoldTertiary
import com.example.ui.theme.AgroGreenPrimary
import com.example.ui.theme.AgroGreenSecondary
import com.example.ui.theme.AlertRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WeatherRainBlue
import com.example.ui.theme.WheatAmber
import com.example.viewmodel.KishanBandhuViewModel

@Composable
fun ToolsAndLedgerScreen(
    viewModel: KishanBandhuViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    var selectedSection by remember { mutableStateOf(0) } // 0: Calc, 1: Ledger, 2: Schemes, 3: YouTubers

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // 4 Tools Tab Row
        ScrollableTabRow(
            selectedTabIndex = selectedSection,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = AgroGreenPrimary,
            edgePadding = 0.dp,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            val tabs = listOf(
                if (language == AppLanguage.HINDI) "🧪 खाद कैलकुलेटर" else "🧪 Fertilizer",
                if (language == AppLanguage.HINDI) "🧾 किसान बहीखाता (बिल)" else "🧾 Kisan Ledger",
                if (language == AppLanguage.HINDI) "📜 सरकारी योजनाएं" else "📜 Govt Schemes",
                if (language == AppLanguage.HINDI) "📺 यूट्यूबर व वीडियो" else "📺 Video Guides"
            )

            tabs.forEachIndexed { idx, title ->
                Tab(
                    selected = selectedSection == idx,
                    onClick = { selectedSection = idx },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedSection == idx) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (selectedSection) {
            0 -> FertilizerCalculatorSection(viewModel = viewModel, language = language)
            1 -> KisanLedgerSection(viewModel = viewModel, language = language)
            2 -> GovernmentSchemesSection(viewModel = viewModel, language = language)
            3 -> AgricultureVideoSection(viewModel = viewModel, language = language)
        }
    }
}

@Composable
private fun FertilizerCalculatorSection(
    viewModel: KishanBandhuViewModel,
    language: AppLanguage
) {
    var areaInput by remember { mutableStateOf("2.5") }
    var isAcre by remember { mutableStateOf(true) }
    var selectedCropId by remember { mutableStateOf("wheat") }
    val calcResult by viewModel.calcResult.collectAsState()

    val cropOptions = listOf(
        Pair("wheat", "गेहूं (Wheat)"),
        Pair("paddy", "धान (Paddy)"),
        Pair("tomato", "टमाटर (Tomato)"),
        Pair("mustard", "सरसों (Mustard)"),
        Pair("cotton", "कपास (Cotton)")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            AgroGlassCard(borderColor = AgroGreenPrimary.copy(alpha = 0.35f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "🧪 सटीक उर्वरक एवं जल आवश्यकता कैलकुलेटर"
                        else
                            "🧪 Accurate Fertilizer & Water Dosage Calculator",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "खेत का क्षेत्रफल और फसल चुनकर यूरिया, डीएपी, पोटाश व जैविक खाद की सटीक मात्रा जानें"
                        else
                            "Enter farm area and crop to get precise Urea, DAP, NPK and compost dosages",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = areaInput,
                            onValueChange = { areaInput = it },
                            label = { Text(if (language == AppLanguage.HINDI) "खेत का क्षेत्रफल" else "Farm Area") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("fertilizer_area_input"),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        // Unit Selector Toggle
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isAcre) "एकड़ (Acre)" else "बीघा (Bigha)",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = AgroGreenPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Switch(
                                checked = isAcre,
                                onCheckedChange = { isAcre = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = AgroGreenPrimary)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (language == AppLanguage.HINDI) "फसल चुनें:" else "Select Crop:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(cropOptions) { (id, label) ->
                            val isSelected = selectedCropId == id
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSelected) AgroGreenPrimary else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable { selectedCropId = id }
                            ) {
                                Text(
                                    text = label,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            val area = areaInput.toDoubleOrNull() ?: 1.0
                            viewModel.calculateFertilizer(area, isAcre, selectedCropId)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AgroGreenPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("calculate_fertilizer_button")
                    ) {
                        Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (language == AppLanguage.HINDI) "मात्रा की गणना करें" else "Calculate Dosage")
                    }
                }
            }
        }

        // Result Card
        if (calcResult != null) {
            val res = calcResult!!
            item {
                AgroGlassCard(borderColor = SuccessGreen) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (language == AppLanguage.HINDI)
                                "📊 अनुशंसित खाद मात्रा (${res.cropName} - ${res.area} ${res.unit})"
                            else
                                "📊 Fertilizer Recommendation (${res.cropName})",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AgroGreenPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            NutrientBadge(
                                label = if (language == AppLanguage.HINDI) "यूरिया (Urea)" else "Urea",
                                amount = "${res.ureaKg.toInt()} kg",
                                color = AgroGreenPrimary
                            )
                            NutrientBadge(
                                label = if (language == AppLanguage.HINDI) "डीएपी (DAP)" else "DAP",
                                amount = "${res.dapKg.toInt()} kg",
                                color = AgroGoldTertiary
                            )
                            NutrientBadge(
                                label = if (language == AppLanguage.HINDI) "पोटाश (MOP)" else "MOP/NPK",
                                amount = "${res.npkKg.toInt()} kg",
                                color = WheatAmber
                            )
                            NutrientBadge(
                                label = if (language == AppLanguage.HINDI) "गोबर खाद" else "Compost",
                                amount = "${String.format("%.1f", res.compostTons)} टन",
                                color = AgroGreenSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(WeatherRainBlue.copy(alpha = 0.1f))
                                .padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WaterDrop,
                                contentDescription = null,
                                tint = WeatherRainBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI)
                                    "अनुमानित सिंचाई जल आवश्यकता: ~${res.waterLiters} लीटर"
                                else
                                    "Est. Irrigation Water Volume: ~${res.waterLiters} Litres",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = WeatherRainBlue
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (language == AppLanguage.HINDI) "कृषि विशेषज्ञ परामर्श:" else "Agronomist Advice:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (language == AppLanguage.HINDI) res.adviceHi else res.adviceEn,
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
private fun NutrientBadge(
    label: String,
    amount: String,
    color: Color
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = amount,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = color
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun KisanLedgerSection(
    viewModel: KishanBandhuViewModel,
    language: AppLanguage
) {
    val context = LocalContext.current
    var cropName by remember { mutableStateOf("गेहूं (Wheat)") }
    var qty by remember { mutableStateOf("35") }
    var unit by remember { mutableStateOf("क्विंटल") }
    var rate by remember { mutableStateOf("2450") }
    var buyerName by remember { mutableStateOf("अग्रवाल ट्रेडिंग कंपनी") }
    var isPaid by remember { mutableStateOf(true) }

    val receipt by viewModel.generatedReceipt.collectAsState()
    val ledgerEntries by viewModel.ledgerEntries.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Bill Form Card
        item {
            AgroGlassCard(borderColor = WeatherRainBlue.copy(alpha = 0.4f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "🧾 किसान बही-खाता (डिजिटल बिक्री रसीद)"
                        else
                            "🧾 Digital Kisan Ledger & Sales Bill Generator",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "फसल उपज, दर और व्यापारी का नाम डालकर तुरंत पक्की पर्ची बनाएं व साझा करें"
                        else
                            "Generate professional invoices for farm produce and share with buyers",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = cropName,
                        onValueChange = { cropName = it },
                        label = { Text(if (language == AppLanguage.HINDI) "फसल का नाम" else "Crop Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = qty,
                            onValueChange = { qty = it },
                            label = { Text(if (language == AppLanguage.HINDI) "मात्रा" else "Quantity") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = rate,
                            onValueChange = { rate = it },
                            label = { Text(if (language == AppLanguage.HINDI) "भाव/दर (₹)" else "Rate (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = buyerName,
                        onValueChange = { buyerName = it },
                        label = { Text(if (language == AppLanguage.HINDI) "खरीदार / आढ़ती का नाम" else "Buyer / Trader Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (language == AppLanguage.HINDI) "भुगतान स्थिति: ${if (isPaid) "पूर्ण चुकता" else "बकाया"}" else "Payment: ${if (isPaid) "Paid" else "Pending"}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Switch(
                            checked = isPaid,
                            onCheckedChange = { isPaid = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = SuccessGreen)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val q = qty.toDoubleOrNull() ?: 1.0
                            val r = rate.toDoubleOrNull() ?: 0.0
                            viewModel.generateBill(cropName, q, unit, r, buyerName, isPaid)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AgroGreenPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("generate_bill_button")
                    ) {
                        Icon(Icons.Default.ReceiptLong, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (language == AppLanguage.HINDI) "डिजिटल पर्ची / बिल तैयार करें" else "Generate Invoice Bill")
                    }
                }
            }
        }

        // Active Generated Receipt Card
        if (receipt != null) {
            val r = receipt!!
            item {
                AgroGlassCard(borderColor = AgroGreenPrimary) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🧾 ${r.id}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (r.isPaid) SuccessGreen.copy(alpha = 0.15f) else AlertRed.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = if (r.isPaid) "✅ PAID" else "⏳ PENDING",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (r.isPaid) SuccessGreen else AlertRed
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "दिनांक / Date: ${r.date}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "खरीदार / Buyer: ${r.buyerName}", style = MaterialTheme.typography.bodySmall)

                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${r.cropName} (${r.quantity} ${r.unit})", fontWeight = FontWeight.SemiBold)
                            Text(text = "@ ₹${r.ratePerUnit}")
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (language == AppLanguage.HINDI) "कुल देय राशि:" else "Total Amount:",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "₹${r.totalAmount.toInt()}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = AgroGreenPrimary
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { viewModel.shareBill(context, r) },
                            colors = ButtonDefaults.buttonColors(containerColor = WeatherRainBlue),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (language == AppLanguage.HINDI) "व्हाट्सएप / पर्ची साझा करें" else "Share via WhatsApp / Print")
                        }
                    }
                }
            }
        }

        // Ledger History
        item {
            Text(
                text = if (language == AppLanguage.HINDI) "📚 पूर्व बिक्री इतिहास (Ledger Records):" else "📚 Sales Ledger Records:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
        }

        items(ledgerEntries) { item ->
            AgroGlassCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = item.cropName, fontWeight = FontWeight.Bold)
                        Text(
                            text = "${item.date} • ${item.buyerName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${item.quantity} ${item.unit} @ ₹${item.ratePerUnit}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "₹${item.totalAmount.toInt()}",
                            fontWeight = FontWeight.Bold,
                            color = AgroGreenPrimary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (item.isPaid) SuccessGreen.copy(alpha = 0.15f) else AlertRed.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = if (item.isPaid) "PAID" else "PENDING",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (item.isPaid) SuccessGreen else AlertRed
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GovernmentSchemesSection(
    viewModel: KishanBandhuViewModel,
    language: AppLanguage
) {
    val context = LocalContext.current
    val schemes by viewModel.schemes.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            AgroGlassCard(borderColor = AgroGoldTertiary.copy(alpha = 0.4f)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (language == AppLanguage.HINDI) "🏛️ प्रमुख सरकारी कृषि योजनाएं व सब्सिडी पोर्टल" else "🏛️ Government Agricultural Schemes & Subsidies",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "पात्रता जांचें, आवश्यक दस्तावेज देखें और सीधे आधिकारिक पोर्टल से आवेदन करें"
                        else
                            "Check eligibility, mandatory documents and apply directly on official portals",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(schemes, key = { it.id }) { scheme ->
            AgroGlassCard(borderColor = AgroGreenSecondary.copy(alpha = 0.3f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (language == AppLanguage.HINDI) scheme.titleHi else scheme.titleEn,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = AgroGreenPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "💰 लाभ / Benefit:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (language == AppLanguage.HINDI) scheme.benefitHi else scheme.benefitEn,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "📋 पात्रता / Eligibility:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (language == AppLanguage.HINDI) scheme.eligibilityHi else scheme.eligibilityEn,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "📄 दस्तावेज / Required Documents:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (language == AppLanguage.HINDI) scheme.docsHi else scheme.docsEn,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.openExternalUrl(context, scheme.applyUrl) },
                        colors = ButtonDefaults.buttonColors(containerColor = AgroGreenPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (language == AppLanguage.HINDI) "आधिकारिक पोर्टल पर आवेदन करें" else "Apply on Official Portal")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun AgricultureVideoSection(
    viewModel: KishanBandhuViewModel,
    language: AppLanguage
) {
    val context = LocalContext.current
    val creators by viewModel.creators.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            AgroGlassCard(borderColor = Color(0xFFD32F2F).copy(alpha = 0.4f)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (language == AppLanguage.HINDI) "📺 शीर्ष कृषि यूट्यूब चैनल व वीडियो ट्यूटोरियल" else "📺 Top Agriculture Creators & Video Tutorials",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "प्रमाणित किसान विशेषज्ञों से सीखें आधुनिक खेती, ड्रिप सिंचाई, जैविक खाद व मशीनरी जुगाड़"
                        else
                            "Learn modern farming hacks, drip irrigation & organic techniques from verified creators",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(creators, key = { it.id }) { creator ->
            AgroGlassCard(borderColor = Color(0xFFD32F2F).copy(alpha = 0.25f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFFEBEE)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = Color(0xFFD32F2F),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = creator.name,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "${creator.channelName} • ${creator.subscribers} सदस्य",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFD32F2F).copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = "YouTube",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD32F2F)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "🎬 ट्यूटोरियल: ${if (language == AppLanguage.HINDI) creator.tutorialTitleHi else creator.tutorialTitleEn}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "विषय: ${if (language == AppLanguage.HINDI) creator.topicHi else creator.topicEn}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.openExternalUrl(context, creator.youtubeUrl) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Subscriptions, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (language == AppLanguage.HINDI) "यूट्यूब पर वीडियो देखें" else "Watch on YouTube")
                    }
                }
            }
        }
    }
}
