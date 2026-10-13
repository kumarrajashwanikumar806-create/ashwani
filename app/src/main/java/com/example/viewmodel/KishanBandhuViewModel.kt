package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.CalendarContract
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AgroRepository
import com.example.data.AppLanguage
import com.example.data.ChatMessage
import com.example.data.CropInfo
import com.example.data.DiseaseDiagnosisResult
import com.example.data.DiseaseInfo
import com.example.data.LedgerEntry
import com.example.data.MandiPrice
import com.example.data.MessageSender
import com.example.data.SchemeInfo
import com.example.data.SeedStoreInfo
import com.example.data.StringsProvider
import com.example.data.TaskCategory
import com.example.data.TaskItem
import com.example.data.WeatherInfo
import com.example.data.YoutubeCreator
import com.example.network.GeminiService
import com.example.util.NotificationHelper
import com.example.util.TtsManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class FertilizerCalculation(
    val area: Double,
    val unit: String,
    val cropName: String,
    val ureaKg: Double,
    val dapKg: Double,
    val npkKg: Double,
    val compostTons: Double,
    val waterLiters: Long,
    val adviceHi: String,
    val adviceEn: String
)

class KishanBandhuViewModel(application: Application) : AndroidViewModel(application) {

    private val ttsManager = TtsManager(application.applicationContext)

    private val _language = MutableStateFlow(AppLanguage.HINDI)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // Weather
    private val _weather = MutableStateFlow(WeatherInfo())
    val weather: StateFlow<WeatherInfo> = _weather.asStateFlow()

    // Tasks & Calendar
    private val _tasks = MutableStateFlow(AgroRepository.initialTasks)
    val tasks: StateFlow<List<TaskItem>> = _tasks.asStateFlow()

    private val _selectedTaskCategory = MutableStateFlow<TaskCategory?>(null)
    val selectedTaskCategory: StateFlow<TaskCategory?> = _selectedTaskCategory.asStateFlow()

    // AI Chat
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = "m0",
                sender = MessageSender.ASSISTANT,
                text = "राम राम किसान भाई! 🙏 मैं आपका 'किसान बंधु' AI सहायक हूँ। फसल, खाद, कीट-रोग, मौसम या मंडी भाव से जुड़ा कोई भी सवाल पूछें।"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    // Crops & Guide
    private val _crops = MutableStateFlow(AgroRepository.crops)
    val crops: StateFlow<List<CropInfo>> = _crops.asStateFlow()

    private val _selectedCrop = MutableStateFlow(AgroRepository.crops.first())
    val selectedCrop: StateFlow<CropInfo> = _selectedCrop.asStateFlow()

    // Diseases
    private val _diseases = MutableStateFlow(AgroRepository.diseases)
    val diseases: StateFlow<List<DiseaseInfo>> = _diseases.asStateFlow()

    private val _selectedDisease = MutableStateFlow(AgroRepository.diseases.first())
    val selectedDisease: StateFlow<DiseaseInfo> = _selectedDisease.asStateFlow()

    // Mandi Prices
    private val _mandiPrices = MutableStateFlow(AgroRepository.mandiPrices)
    val mandiPrices: StateFlow<List<MandiPrice>> = _mandiPrices.asStateFlow()

    private val _mandiSearchQuery = MutableStateFlow("")
    val mandiSearchQuery: StateFlow<String> = _mandiSearchQuery.asStateFlow()

    private val _mandiCategoryFilter = MutableStateFlow("ALL")
    val mandiCategoryFilter: StateFlow<String> = _mandiCategoryFilter.asStateFlow()

    private val _mandiTrendFilter = MutableStateFlow("ALL")
    val mandiTrendFilter: StateFlow<String> = _mandiTrendFilter.asStateFlow()

    private val _isMandiRefreshing = MutableStateFlow(false)
    val isMandiRefreshing: StateFlow<Boolean> = _isMandiRefreshing.asStateFlow()

    private val _lastMandiUpdateTime = MutableStateFlow("आज सुबह 10:30 AM (दैनिक समीक्षा)")
    val lastMandiUpdateTime: StateFlow<String> = _lastMandiUpdateTime.asStateFlow()

    // Schemes
    private val _schemes = MutableStateFlow(AgroRepository.schemes)
    val schemes: StateFlow<List<SchemeInfo>> = _schemes.asStateFlow()

    // YouTube Creators
    private val _creators = MutableStateFlow(AgroRepository.youtubeCreators)
    val creators: StateFlow<List<YoutubeCreator>> = _creators.asStateFlow()

    // Ledger
    private val _ledgerEntries = MutableStateFlow(AgroRepository.initialLedger)
    val ledgerEntries: StateFlow<List<LedgerEntry>> = _ledgerEntries.asStateFlow()

    // Fertilizer Calculation State
    private val _calcResult = MutableStateFlow<FertilizerCalculation?>(null)
    val calcResult: StateFlow<FertilizerCalculation?> = _calcResult.asStateFlow()

    // Current Bill Receipt
    private val _generatedReceipt = MutableStateFlow<LedgerEntry?>(null)
    val generatedReceipt: StateFlow<LedgerEntry?> = _generatedReceipt.asStateFlow()

    // Farmer Auth & Profile
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _farmerName = MutableStateFlow("किसान भाई (Kishan Bhai)")
    val farmerName: StateFlow<String> = _farmerName.asStateFlow()

    private val _farmerPhone = MutableStateFlow("+91 98765 01234")
    val farmerPhone: StateFlow<String> = _farmerPhone.asStateFlow()

    private val _farmerVillage = MutableStateFlow("करनाल, हरियाणा (Karnal, Haryana)")
    val farmerVillage: StateFlow<String> = _farmerVillage.asStateFlow()

    // 22-Language Modal State
    private val _showLanguagePicker = MutableStateFlow(false)
    val showLanguagePicker: StateFlow<Boolean> = _showLanguagePicker.asStateFlow()

    // Nearby Seed Stores
    private val _nearbySeedStores = MutableStateFlow(AgroRepository.nearbySeedStores)
    val nearbySeedStores: StateFlow<List<SeedStoreInfo>> = _nearbySeedStores.asStateFlow()

    // Fast 1-Second Camera Leaf Scanner & Solution
    private val _isScanningLeaf = MutableStateFlow(false)
    val isScanningLeaf: StateFlow<Boolean> = _isScanningLeaf.asStateFlow()

    private val _scannedBitmap = MutableStateFlow<Bitmap?>(null)
    val scannedBitmap: StateFlow<Bitmap?> = _scannedBitmap.asStateFlow()

    private val _leafDiagnosis = MutableStateFlow<DiseaseDiagnosisResult>(AgroRepository.getDiseaseDiagnosis("early_blight"))
    val leafDiagnosis: StateFlow<DiseaseDiagnosisResult> = _leafDiagnosis.asStateFlow()

    init {
        NotificationHelper.createNotificationChannel(application.applicationContext)
        calculateFertilizer(area = 2.0, isAcre = true, cropId = "wheat")
    }

    fun login(name: String, phone: String, village: String) {
        _farmerName.value = name.ifBlank { "किसान भाई" }
        _farmerPhone.value = phone.ifBlank { "+91 98765 01234" }
        _farmerVillage.value = village.ifBlank { "करनाल (हरियाणा)" }
        _isLoggedIn.value = true
        // Immediate Ram Ram greeting on entry
        speakGreeting()
    }

    fun logout() {
        _isLoggedIn.value = false
    }

    fun openLanguagePicker() {
        _showLanguagePicker.value = true
    }

    fun closeLanguagePicker() {
        _showLanguagePicker.value = false
    }

    fun selectLanguage(lang: AppLanguage) {
        _language.value = lang
        _showLanguagePicker.value = false
        speakGreeting()
    }

    fun scanLeafImage(bitmap: Bitmap?, sampleDiseaseId: String = "early_blight") {
        _scannedBitmap.value = bitmap
        viewModelScope.launch {
            _isScanningLeaf.value = true
            // 2 second fast AI scan animation as requested
            delay(2000)
            val diagnosis = AgroRepository.getDiseaseDiagnosis(sampleDiseaseId)
            _leafDiagnosis.value = diagnosis
            _isScanningLeaf.value = false
            // Auto narrate why it's happening and how to solve it
            speakDiseaseSolution(diagnosis)
        }
    }

    fun speakDiseaseSolution(diagnosis: DiseaseDiagnosisResult = _leafDiagnosis.value) {
        val why = if (_language.value == AppLanguage.HINDI) diagnosis.whyHappeningHi else diagnosis.whyHappeningEn
        val organic = if (_language.value == AppLanguage.HINDI) diagnosis.howToSolveOrganicHi else diagnosis.howToSolveOrganicEn
        val chemical = if (_language.value == AppLanguage.HINDI) diagnosis.howToSolveChemicalHi else diagnosis.howToSolveChemicalEn
        val title = if (_language.value == AppLanguage.HINDI) diagnosis.diseaseNameHi else diagnosis.diseaseNameEn
        val fullMsg = "$title। कारण: $why। जैविक समाधान: $organic। रासायनिक समाधान: $chemical।"
        ttsManager.speak(fullMsg, _language.value, "disease_solution")
    }

    fun openGoogleMapsNearSeeds(context: Context) {
        try {
            val gmmIntentUri = Uri.parse("geo:0,0?q=seeds+store+fertilizer+near+me")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                setPackage("com.google.android.apps.maps")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/seeds+store+fertilizer+near+me/")).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(webIntent)
            }
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/seeds+store+fertilizer+near+me/")).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(webIntent)
        }
    }

    fun openGoogleMapsForStore(context: Context, store: SeedStoreInfo) {
        try {
            val query = Uri.encode("${store.nameHi} ${store.addressHi}")
            val gmmIntentUri = Uri.parse("geo:0,0?q=$query")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/${Uri.encode(store.mapQuery)}/")).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(webIntent)
        }
    }

    fun speakStoreDetails(store: SeedStoreInfo) {
        val msg = if (_language.value == AppLanguage.HINDI) {
            "${store.nameHi}। दूरी: ${store.distanceKm}। पता: ${store.addressHi}। उपलब्ध प्रमाणित बीज: ${store.certifiedSeeds.joinToString(", ")}। फोन: ${store.phone}।"
        } else {
            "${store.nameEn}. Distance: ${store.distanceKm}. Address: ${store.addressEn}. Available seeds: ${store.certifiedSeeds.joinToString(", ")}. Phone: ${store.phone}."
        }
        ttsManager.speak(msg, _language.value, "store_${store.id}")
    }

    fun toggleLanguage() {
        val next = if (_language.value == AppLanguage.HINDI) AppLanguage.ENGLISH else AppLanguage.HINDI
        _language.value = next
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun setTab(index: Int) {
        _selectedTab.value = index
    }

    fun selectCrop(crop: CropInfo) {
        _selectedCrop.value = crop
    }

    fun selectDisease(disease: DiseaseInfo) {
        _selectedDisease.value = disease
    }

    fun toggleTask(taskId: String) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == taskId) task.copy(isDone = !task.isDone) else task
        }
    }

    fun addTask(titleHi: String, titleEn: String, category: TaskCategory, dueDate: String) {
        val newTask = TaskItem(
            id = UUID.randomUUID().toString(),
            titleHi = titleHi.ifBlank { "नया कृषि कार्य" },
            titleEn = titleEn.ifBlank { "New Farm Task" },
            category = category,
            dueDate = dueDate.ifBlank { "आज (Today)" },
            isDone = false
        )
        _tasks.value = listOf(newTask) + _tasks.value
    }

    fun deleteTask(taskId: String) {
        _tasks.value = _tasks.value.filterNot { it.id == taskId }
    }

    fun filterTaskCategory(category: TaskCategory?) {
        _selectedTaskCategory.value = category
    }

    // TTS state
    val isTtsSpeaking: StateFlow<Boolean> = ttsManager.isSpeaking
    val currentSpeechTag: StateFlow<String?> = ttsManager.currentUtteranceTag

    // Voice & TTS
    fun speakGreeting() {
        val text = StringsProvider.ramRamGreeting(_language.value)
        ttsManager.speak(text, isHindi = (_language.value == AppLanguage.HINDI), utteranceTag = "greeting")
    }

    fun speakText(text: String, tag: String = "general_speech") {
        ttsManager.speak(text, isHindi = (_language.value == AppLanguage.HINDI), utteranceTag = tag)
    }

    fun toggleWeatherSpeech() {
        if (ttsManager.isSpeaking.value && ttsManager.currentUtteranceTag.value == "weather_update") {
            ttsManager.stop()
        } else {
            ttsManager.speakWeatherUpdate(_weather.value, _language.value)
        }
    }

    fun toggleSmartIrrigationSpeech() {
        if (ttsManager.isSpeaking.value && ttsManager.currentUtteranceTag.value == "irrigation_alert") {
            ttsManager.stop()
        } else {
            ttsManager.speakSmartIrrigation(_weather.value, _language.value)
        }
    }

    fun toggleCropAdviceSpeech(crop: CropInfo) {
        val tag = "crop_advice_${crop.id}"
        if (ttsManager.isSpeaking.value && ttsManager.currentUtteranceTag.value == tag) {
            ttsManager.stop()
        } else {
            ttsManager.speakCropAdvice(crop, _language.value)
        }
    }

    fun toggleMandiSpeech(item: MandiPrice) {
        val tag = "mandi_rate_${item.id}"
        if (ttsManager.isSpeaking.value && ttsManager.currentUtteranceTag.value == tag) {
            ttsManager.stop()
        } else {
            ttsManager.speakMandiRate(item, _language.value)
        }
    }

    fun setMandiSearchQuery(query: String) {
        _mandiSearchQuery.value = query
    }

    fun setMandiCategoryFilter(category: String) {
        _mandiCategoryFilter.value = category
    }

    fun setMandiTrendFilter(filter: String) {
        _mandiTrendFilter.value = filter
    }

    fun refreshMandiPrices() {
        viewModelScope.launch {
            _isMandiRefreshing.value = true
            delay(700)
            _mandiPrices.value = _mandiPrices.value.map { item ->
                val delta = ((-15..20).random()) / 10.0
                val newPrice = (item.currentPrice * (1.0 + delta / 100.0)).toInt()
                val isBullishNow = delta >= 0
                val newWeekly = item.weeklyTrend.drop(1) + newPrice
                item.copy(
                    currentPrice = newPrice,
                    changePercent = ((delta * 10).toInt() / 10.0),
                    isBullish = isBullishNow,
                    weeklyTrend = newWeekly
                )
            }
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            _lastMandiUpdateTime.value = "ताजा भाव अपडेट (${timeFormat.format(Date())})"
            _isMandiRefreshing.value = false
        }
    }

    fun stopSpeaking() {
        ttsManager.stop()
    }

    // Weather & Notification
    fun triggerWeatherAlertNotification(context: Context) {
        val title = StringsProvider.smartIrrigationAlertTitle(_language.value)
        val msg = StringsProvider.smartIrrigationAlertDesc(_language.value)
        NotificationHelper.sendWeatherNotification(context, title, msg)
        Toast.makeText(context, "🔔 मौसम अलर्ट नोटिफिकेशन भेजा गया!", Toast.LENGTH_SHORT).show()
    }

    // Google Calendar Sync
    fun syncTaskWithCalendar(context: Context, task: TaskItem) {
        try {
            val title = if (_language.value == AppLanguage.HINDI) task.titleHi else task.titleEn
            val desc = "Kishan Bandhu Farm Task [${task.category.labelEn}]: ${task.notes}"
            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, title)
                putExtra(CalendarContract.Events.DESCRIPTION, desc)
                putExtra(CalendarContract.Events.EVENT_LOCATION, "फार्म / खेत (Farm)")
                putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "कैलेंडर ऐप उपलब्ध नहीं है", Toast.LENGTH_SHORT).show()
        }
    }

    // Chatbot
    fun sendUserMessage(text: String) {
        if (text.isBlank()) return
        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = MessageSender.USER,
            text = text.trim()
        )
        _chatMessages.value = _chatMessages.value + userMsg

        viewModelScope.launch {
            _isAiLoading.value = true
            val botReply = GeminiService.askAgroAssistant(text.trim(), _language.value)
            val assistantMsg = ChatMessage(
                id = UUID.randomUUID().toString(),
                sender = MessageSender.ASSISTANT,
                text = botReply
            )
            _chatMessages.value = _chatMessages.value + assistantMsg
            _isAiLoading.value = false
            // Auto-speak reply
            speakText(botReply)
        }
    }

    // Fertilizer Calculator
    fun calculateFertilizer(area: Double, isAcre: Boolean, cropId: String) {
        val acreFactor = if (isAcre) area else (area * 0.4) // 1 Bigha ~ 0.4 Acre
        val unitLabel = if (isAcre) "एकड़ (Acre)" else "बीघा (Bigha)"

        val (cropName, ureaPerAcre, dapPerAcre, npkPerAcre, compostPerAcre, adviceHi, adviceEn) = when (cropId) {
            "wheat" -> Tuple7(
                "गेहूं (Wheat)",
                120.0, 50.0, 20.0, 4.0,
                "यूरिया 3 भागों में दें: 1/3 बुवाई पर, 1/3 पहली सिंचाई पर, 1/3 गाभा अवस्था पर। डीएपी पूरी मात्रा बुवाई के समय डालें।",
                "Split Urea into 3 doses: 1/3 at sowing, 1/3 at 1st irrigation, 1/3 at boot stage. Apply full DAP at basal sowing."
            )
            "paddy" -> Tuple7(
                "धान (Paddy)",
                95.0, 45.0, 30.0, 5.0,
                "जिंक सल्फेट 10 किग्रा अवश्य मिलाएं। यूरिया को रोपाई के 21 और 42 दिन बाद टॉप ड्रेस करें।",
                "Add 10 kg Zinc Sulphate. Top dress Urea at 21 and 42 days after transplanting."
            )
            "tomato" -> Tuple7(
                "टमाटर (Tomato)",
                80.0, 60.0, 50.0, 8.0,
                "गोबर की सड़ी खाद अच्छी तरह मिलाएँ। ड्रिप में घुलनशील NPK 19:19:19 का प्रयोग करें।",
                "Incorporate well-rotted farmyard manure. Prefer water-soluble NPK 19:19:19 via drip."
            )
            "mustard" -> Tuple7(
                "सरसों (Mustard)",
                65.0, 40.0, 25.0, 3.5,
                "सरसों में 20 किग्रा सल्फर अनिवार्य है। इससे तेल की मात्रा 3-5% बढ़ जाती है।",
                "Sulphur (20 kg) is crucial for mustard to boost oil content by 3-5%."
            )
            else -> Tuple7(
                "कपास (Cotton)",
                90.0, 50.0, 35.0, 6.0,
                "कपास में नत्रजन अधिक न दें अन्यथा वानस्पतिक वृद्धि ज्यादा और टिंडे कम बनेंगे।",
                "Avoid excess Nitrogen in cotton to prevent vegetative growth at the expense of bolls."
            )
        }

        val totalUrea = ureaPerAcre * acreFactor
        val totalDap = dapPerAcre * acreFactor
        val totalNpk = npkPerAcre * acreFactor
        val totalCompost = compostPerAcre * acreFactor
        val waterNeeded = (acreFactor * 12000).toLong()

        _calcResult.value = FertilizerCalculation(
            area = area,
            unit = unitLabel,
            cropName = cropName,
            ureaKg = totalUrea,
            dapKg = totalDap,
            npkKg = totalNpk,
            compostTons = totalCompost,
            waterLiters = waterNeeded,
            adviceHi = adviceHi,
            adviceEn = adviceEn
        )
    }

    // Bill Generator
    fun generateBill(
        cropName: String,
        qty: Double,
        unit: String,
        rate: Double,
        buyerName: String,
        isPaid: Boolean
    ): LedgerEntry {
        val total = qty * rate
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val dateStr = sdf.format(Date())

        val entry = LedgerEntry(
            id = "KB-" + System.currentTimeMillis().toString().takeLast(6),
            date = dateStr,
            cropName = cropName.ifBlank { "गेहूं (Wheat)" },
            quantity = qty,
            unit = unit,
            ratePerUnit = rate,
            totalAmount = total,
            buyerName = buyerName.ifBlank { "कृषि व्यापारी (Mandi Trader)" },
            isPaid = isPaid
        )
        _generatedReceipt.value = entry
        _ledgerEntries.value = listOf(entry) + _ledgerEntries.value
        return entry
    }

    fun shareBill(context: Context, entry: LedgerEntry) {
        val text = """
            🧾 किसान बंधु - बिक्री पर्ची / Bahi Khata
            बिल संख्या: ${entry.id}
            दिनांक: ${entry.date}
            फसल: ${entry.cropName}
            मात्रा: ${entry.quantity} ${entry.unit}
            दर / भाव: ₹${entry.ratePerUnit} प्रति ${entry.unit}
            कुल राशि: ₹${entry.totalAmount}
            खरीदार: ${entry.buyerName}
            भुगतान स्थिति: ${if (entry.isPaid) "✅ पूर्ण चुकता (PAID)" else "⏳ बकाया (PENDING)"}
            -----------------------------
            किसान बंधु ऐप द्वारा उत्पन्न
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(Intent.createChooser(intent, "पर्ची साझा करें / Share Bill"))
    }

    fun openExternalUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "लिंक खोलने में असमर्थ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}

private data class Tuple7<A, B, C, D, E, F, G>(
    val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G
)
