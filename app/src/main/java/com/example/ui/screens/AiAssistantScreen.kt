package com.example.ui.screens

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.AppLanguage
import com.example.data.ChatMessage
import com.example.data.DiseaseInfo
import com.example.data.MessageSender
import com.example.ui.components.AgroGlassCard
import com.example.ui.components.CameraXLeafScannerDialog
import com.example.ui.theme.AgroGoldTertiary
import com.example.ui.theme.AgroGreenPrimary
import com.example.ui.theme.AgroGreenSecondary
import com.example.ui.theme.AlertRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WeatherRainBlue
import com.example.viewmodel.KishanBandhuViewModel
import java.util.Locale

@Composable
fun AiAssistantScreen(
    viewModel: KishanBandhuViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    var selectedSection by remember { mutableStateOf(0) } // 0: Chatbot, 1: Disease Detector

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Sub Tab Row
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
                        text = if (language == AppLanguage.HINDI) "🤖 किसान AI सहायक" else "🤖 AI Assistant",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
            Tab(
                selected = selectedSection == 1,
                onClick = { selectedSection = 1 },
                text = {
                    Text(
                        text = if (language == AppLanguage.HINDI) "📸 फसल रोग जांच" else "📸 Disease Detector",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (selectedSection == 0) {
            ChatbotSection(viewModel = viewModel, language = language)
        } else {
            DiseaseDetectorSection(viewModel = viewModel, language = language)
        }
    }
}

@Composable
private fun ChatbotSection(
    viewModel: KishanBandhuViewModel,
    language: AppLanguage
) {
    val messages by viewModel.chatMessages.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()
    var textInput by remember { mutableStateOf("") }

    // Speech-to-Text launcher
    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()
            if (!spokenText.isNullOrBlank()) {
                viewModel.sendUserMessage(spokenText)
            }
        }
    }

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        // Quick suggestion chips
        val quickPrompts = if (language == AppLanguage.HINDI) {
            listOf("यूरिया कब और कैसे दें?", "गेहूं में पहली सिंचाई का सही समय?", "टमाटर का झुलसा रोग उपचार", "बारिश में क्या करें?")
        } else {
            listOf("When to apply Urea?", "Best time for Wheat 1st irrigation?", "Tomato early blight cure", "Rain precautions")
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(quickPrompts) { prompt ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.sendUserMessage(prompt) }
                ) {
                    Text(
                        text = "💡 $prompt",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Chat Message List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatMessageBubble(
                    message = msg,
                    onSpeak = { viewModel.speakText(msg.text) }
                )
            }

            if (isAiLoading) {
                item {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = AgroGreenPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (language == AppLanguage.HINDI) "किसान बंधु उत्तर तैयार कर रहा है..." else "Kishan Bandhu is typing...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Input Field + Mic + Send Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = {
                    Text(
                        if (language == AppLanguage.HINDI) "खेती-बाड़ी से जुड़ा सवाल लिखें..." else "Ask a farming query..."
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_field"),
                shape = RoundedCornerShape(24.dp),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(6.dp))

            // Mic Button (Speech-to-Text)
            IconButton(
                onClick = {
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                        putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                        )
                        putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE,
                            if (language == AppLanguage.HINDI) "hi-IN" else "en-US"
                        )
                        putExtra(
                            RecognizerIntent.EXTRA_PROMPT,
                            if (language == AppLanguage.HINDI) "अपना सवाल बोलें (राम राम)..." else "Speak your farm question..."
                        )
                    }
                    try {
                        speechRecognizerLauncher.launch(intent)
                    } catch (e: Exception) {
                        // fallback
                    }
                },
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(AgroGreenSecondary.copy(alpha = 0.15f))
                    .testTag("voice_input_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Input",
                    tint = AgroGreenSecondary
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            // Send Button
            IconButton(
                onClick = {
                    if (textInput.isNotBlank()) {
                        viewModel.sendUserMessage(textInput)
                        textInput = ""
                    }
                },
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(AgroGreenPrimary)
                    .testTag("send_message_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun ChatMessageBubble(
    message: ChatMessage,
    onSpeak: () -> Unit
) {
    val isUser = message.sender == MessageSender.USER
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(AgroGreenPrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            color = if (isUser) AgroGreenPrimary else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isUser) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (!isUser) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = onSpeak,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "TTS Readout",
                                tint = AgroGreenPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DiseaseDetectorSection(
    viewModel: KishanBandhuViewModel,
    language: AppLanguage
) {
    val isScanningLeaf by viewModel.isScanningLeaf.collectAsState()
    val leafDiagnosis by viewModel.leafDiagnosis.collectAsState()
    val scannedBitmap by viewModel.scannedBitmap.collectAsState()
    var uploadedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showCameraXScanner by remember { mutableStateOf(false) }

    // Direct phone camera capture launcher fallback
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            uploadedImageUri = null
            viewModel.scanLeafImage(bitmap, "early_blight")
        }
    }

    // Photo picker launcher (Gallery Upload)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            uploadedImageUri = uri
            viewModel.scanLeafImage(null, "leaf_curl")
        }
    }

    // CameraX Real-time Leaf Scanner Dialog
    if (showCameraXScanner) {
        CameraXLeafScannerDialog(
            onDismiss = { showCameraXScanner = false },
            onImageCaptured = { bitmap ->
                uploadedImageUri = null
                viewModel.scanLeafImage(bitmap, "early_blight")
            },
            onUploadClick = {
                photoPickerLauncher.launch(
                    androidx.activity.result.PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Upload & Camera Card
        item {
            AgroGlassCard(borderColor = AlertRed.copy(alpha = 0.4f)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "📸 CameraX AI लाइव पत्ती स्कैनर / 2 सेकंड में समाधान"
                        else
                            "📸 CameraX Live AI Leaf Scanner / 2-Sec Solution",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (language == AppLanguage.HINDI)
                            "CameraX कैमरा से रोगग्रस्त पत्ती की फोटो लें या गैलरी से अपलोड करें। AI मात्र 2 सेकंड में बताएगा कि रोग क्यों हुआ और इसका जैविक व रासायनिक समाधान क्या है।"
                        else
                            "Capture leaf directly using CameraX or upload from gallery. AI delivers results in 2 seconds explaining causes and organic/chemical solutions.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    if (scannedBitmap != null) {
                        Image(
                            bitmap = scannedBitmap!!.asImageBitmap(),
                            contentDescription = "Camera captured leaf",
                            modifier = Modifier
                                .size(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    } else if (uploadedImageUri != null) {
                        AsyncImage(
                            model = uploadedImageUri,
                            contentDescription = "Uploaded leaf image",
                            modifier = Modifier
                                .size(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    // Main CameraX Button and Upload Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { showCameraXScanner = true },
                            colors = ButtonDefaults.buttonColors(containerColor = AgroGreenPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1.2f)
                                .height(50.dp)
                                .testTag("open_camerax_scanner_button")
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) "CameraX से स्कैन" else "CameraX Scan",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    androidx.activity.result.PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("upload_leaf_photo_button")
                        ) {
                            Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) "फोटो अपलोड" else "Upload Photo",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                    }
                }
            }
        }

        // 2-Second Scanning Active Indicator
        if (isScanningLeaf) {
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = AgroGreenPrimary.copy(alpha = 0.12f),
                    border = BorderStroke(1.5.dp, AgroGreenPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = AgroGreenPrimary,
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 3.dp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (language == AppLanguage.HINDI)
                                "⚡ CameraX AI पत्ती का 2 सेकंड में विश्लेषण कर रहा है..."
                            else
                                "⚡ CameraX AI is analyzing leaf in 2 seconds...",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = AgroGreenPrimary
                            )
                        )
                    }
                }
            }
        }

        // Quick Sample Leaf Demos
        item {
            Text(
                text = if (language == AppLanguage.HINDI) "🔬 तुरंत परीक्षण हेतु पत्ती नमूना चुनें:" else "🔬 Quick Sample Leaf Tests:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))

            val sampleCases = listOf(
                Pair("early_blight", if (language == AppLanguage.HINDI) "टमाटर झुलसा" else "Tomato Blight"),
                Pair("yellow_rust", if (language == AppLanguage.HINDI) "गेहूं पीला रतुआ" else "Wheat Rust"),
                Pair("leaf_curl", if (language == AppLanguage.HINDI) "मिर्च पर्ण कुंचन" else "Chilli Leaf Curl"),
                Pair("powdery_mildew", if (language == AppLanguage.HINDI) "चूर्णी फफूंद" else "Powdery Mildew"),
                Pair("healthy_crop", if (language == AppLanguage.HINDI) "स्वस्थ पत्ती" else "Healthy Leaf")
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(sampleCases) { (id, label) ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { viewModel.scanLeafImage(null, id) }
                    ) {
                        Text(
                            text = "🍃 $label",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }

        // Full Diagnosis Result Card
        item {
            AgroGlassCard(
                borderColor = if (leafDiagnosis.diseaseNameHi.contains("स्वस्थ")) SuccessGreen else AlertRed
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Header with confidence
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (leafDiagnosis.diseaseNameHi.contains("स्वस्थ")) Icons.Default.Check else Icons.Default.Warning,
                                contentDescription = null,
                                tint = if (leafDiagnosis.diseaseNameHi.contains("स्वस्थ")) SuccessGreen else AlertRed,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) "रोग निदान रिपोर्ट" else "Inspection Report",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AgroGreenPrimary.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${leafDiagnosis.confidencePercent}% ${if (language == AppLanguage.HINDI) "सटीक" else "Confidence"}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AgroGreenPrimary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (language == AppLanguage.HINDI) leafDiagnosis.diseaseNameHi else leafDiagnosis.diseaseNameEn,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = AgroGreenPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 1. Why is it happening? (क्यों हो रहा है)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(AlertRed.copy(alpha = 0.08f))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (language == AppLanguage.HINDI) "❓ यह रोग क्यों हो रहा है? (कारण):" else "❓ Why is it happening? (Causes):",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = AlertRed
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) leafDiagnosis.whyHappeningHi else leafDiagnosis.whyHappeningEn,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 2. How to solve? - Organic (जैविक समाधान)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SuccessGreen.copy(alpha = 0.08f))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = if (language == AppLanguage.HINDI) "🌿 समस्या का जैविक समाधान (Organic Cure):" else "🌿 How to Solve - Organic Solution:",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) leafDiagnosis.howToSolveOrganicHi else leafDiagnosis.howToSolveOrganicEn,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 3. Chemical Solution (रासायनिक समाधान)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(AgroGoldTertiary.copy(alpha = 0.08f))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = if (language == AppLanguage.HINDI) "🧪 रासायनिक नियंत्रण (Chemical Control):" else "🧪 Chemical Remedy:",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AgroGoldTertiary
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) leafDiagnosis.howToSolveChemicalHi else leafDiagnosis.howToSolveChemicalEn,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 4. Prevention (रोकथाम)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = if (language == AppLanguage.HINDI) "🛡️ भविष्य में रोकथाम (Prevention Tips):" else "🛡️ Prevention Tips:",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (language == AppLanguage.HINDI) leafDiagnosis.preventionHi else leafDiagnosis.preventionEn,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Audio readout button
                    Button(
                        onClick = { viewModel.speakDiseaseSolution(leafDiagnosis) },
                        colors = ButtonDefaults.buttonColors(containerColor = AgroGreenPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("listen_disease_audio_button")
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == AppLanguage.HINDI) "🔊 3 सेकंड में बोलकर सुनें (Listen Solution)" else "🔊 Listen Solution in Voice",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
