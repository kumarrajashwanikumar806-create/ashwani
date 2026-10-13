package com.example.data

enum class TaskCategory(val labelHi: String, val labelEn: String) {
    SOWING("बुवाई", "Sowing"),
    IRRIGATION("सिंचाई", "Irrigation"),
    FERTILIZER("उर्वरक/खाद", "Fertilizer"),
    PEST_CONTROL("कीट नियंत्रण", "Pest Control"),
    HARVESTING("कटाई", "Harvesting"),
    MANDI("मंडी बिक्री", "Mandi Sale")
}

data class TaskItem(
    val id: String,
    val titleHi: String,
    val titleEn: String,
    val category: TaskCategory,
    val dueDate: String,
    val isDone: Boolean = false,
    val notes: String = ""
)

data class WeatherInfo(
    val city: String = "करनाल / Karnal (Agro Hub)",
    val temperature: Int = 28,
    val conditionHi: String = "बादल छाए रहने के साथ बारिश के आसार",
    val conditionEn: String = "Overcast with rain showers expected",
    val humidity: Int = 78,
    val rainChance: Int = 85,
    val windSpeedKmh: Int = 14,
    val irrigationTipHi: String = "आज बारिश की 85% संभावना है। बिजली व पानी की बचत हेतु फसलों में सिंचाई स्थगित रखें!",
    val irrigationTipEn: String = "85% chance of rain today. Defer crop irrigation to conserve electricity and water!"
)

data class CropStage(
    val stageNameHi: String,
    val stageNameEn: String,
    val daysRange: String,
    val activityHi: String,
    val activityEn: String
)

data class CropInfo(
    val id: String,
    val nameHi: String,
    val nameEn: String,
    val seasonHi: String,
    val seasonEn: String,
    val durationDays: String,
    val soilHi: String,
    val soilEn: String,
    val rainfallHi: String,
    val rainfallEn: String,
    val idealTemp: String,
    val stages: List<CropStage>,
    val fertilizerHi: String,
    val fertilizerEn: String
)

data class DiseaseInfo(
    val id: String,
    val nameHi: String,
    val nameEn: String,
    val cropHi: String,
    val cropEn: String,
    val severityHi: String,
    val severityEn: String,
    val symptomsHi: String,
    val symptomsEn: String,
    val organicTreatmentHi: String,
    val organicTreatmentEn: String,
    val chemicalTreatmentHi: String,
    val chemicalTreatmentEn: String
)

data class MandiPrice(
    val id: String,
    val cropHi: String,
    val cropEn: String,
    val mandiHi: String,
    val mandiEn: String,
    val currentPrice: Int, // Rs per Quintal
    val mspPrice: Int,
    val changePercent: Double,
    val isBullish: Boolean,
    val adviceHi: String,
    val adviceEn: String,
    val category: String = "अनाज",
    val minPrice: Int = (currentPrice * 0.95).toInt(),
    val maxPrice: Int = (currentPrice * 1.05).toInt(),
    val arrivalsTons: Int = 120,
    val weeklyTrend: List<Int> = listOf(
        (currentPrice * 0.95).toInt(),
        (currentPrice * 0.96).toInt(),
        (currentPrice * 0.98).toInt(),
        (currentPrice * 0.97).toInt(),
        (currentPrice * 0.99).toInt(),
        currentPrice
    )
)

data class SchemeInfo(
    val id: String,
    val titleHi: String,
    val titleEn: String,
    val benefitHi: String,
    val benefitEn: String,
    val eligibilityHi: String,
    val eligibilityEn: String,
    val docsHi: String,
    val docsEn: String,
    val applyUrl: String
)

data class LedgerEntry(
    val id: String,
    val date: String,
    val cropName: String,
    val quantity: Double, // in Quintal or Kg
    val unit: String, // Quintal / Kg
    val ratePerUnit: Double,
    val totalAmount: Double,
    val buyerName: String,
    val isPaid: Boolean
)

data class YoutubeCreator(
    val id: String,
    val name: String,
    val channelName: String,
    val subscribers: String,
    val topicHi: String,
    val topicEn: String,
    val youtubeUrl: String,
    val tutorialTitleHi: String,
    val tutorialTitleEn: String
)

enum class MessageSender {
    USER,
    ASSISTANT
}

data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class SeedStoreInfo(
    val id: String,
    val nameHi: String,
    val nameEn: String,
    val distanceKm: String,
    val addressHi: String,
    val addressEn: String,
    val phone: String,
    val rating: String,
    val certifiedSeeds: List<String>,
    val mapQuery: String
)

data class DiseaseDiagnosisResult(
    val diseaseNameHi: String,
    val diseaseNameEn: String,
    val confidencePercent: Int,
    val whyHappeningHi: String,
    val whyHappeningEn: String,
    val howToSolveOrganicHi: String,
    val howToSolveOrganicEn: String,
    val howToSolveChemicalHi: String,
    val howToSolveChemicalEn: String,
    val preventionHi: String,
    val preventionEn: String
)
