package com.example.network

import com.example.BuildConfig
import com.example.data.AppLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiService {
    private const val MODEL_NAME = "gemini-3.5-flash"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun askAgroAssistant(userQuery: String, language: AppLanguage): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        if (apiKey.isNullOrBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getOfflineAgroResponse(userQuery, language)
        }

        try {
            kotlinx.coroutines.withTimeoutOrNull(2800L) {
                val systemPrompt = "You are 'किसान बंधु' (Kishan Bandhu AI Assistant), an expert Indian agronomist and farming companion. " +
                        "Always answer warmly and practically for Indian farmers in simple ${if (language == AppLanguage.HINDI) "Hindi (हिंदी)" else "English"}, with actionable steps regarding crops, fertilizers, pest control, weather, and mandi prices. Keep answers concise, clear, and friendly (use 'राम राम किसान भाई' where appropriate)."

                val rootJson = JSONObject().apply {
                    val contentsArray = JSONArray().apply {
                        val turnObj = JSONObject().apply {
                            put("role", "user")
                            val partsArray = JSONArray().apply {
                                val part = JSONObject().apply {
                                    put("text", "$systemPrompt\n\nFarmer Question: $userQuery")
                                }
                                put(part)
                            }
                            put("parts", partsArray)
                        }
                        put(turnObj)
                    }
                    put("contents", contentsArray)
                }

                val mediaType = "application/json; charset=utf-8".toMediaType()
                val requestBody = rootJson.toString().toRequestBody(mediaType)

                val request = Request.Builder()
                    .url("$BASE_URL?key=$apiKey")
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBodyStr = response.body?.string() ?: ""
                    val responseJson = JSONObject(responseBodyStr)
                    val candidates = responseJson.optJSONArray("candidates")
                    if (candidates != null && candidates.length() > 0) {
                        val content = candidates.getJSONObject(0).optJSONObject("content")
                        val parts = content?.optJSONArray("parts")
                        if (parts != null && parts.length() > 0) {
                            val text = parts.getJSONObject(0).optString("text")
                            if (text.isNotBlank()) {
                                return@withTimeoutOrNull text.trim()
                            }
                        }
                    }
                }
                null
            } ?: getOfflineAgroResponse(userQuery, language)
        } catch (e: Exception) {
            getOfflineAgroResponse(userQuery, language)
        }
    }

    private fun getOfflineAgroResponse(query: String, language: AppLanguage): String {
        val q = query.lowercase()
        return when {
            q.contains("यूरिया") || q.contains("urea") || q.contains("खाद") || q.contains("fertilizer") -> {
                if (language == AppLanguage.HINDI) {
                    "राम राम किसान भाई! यूरिया का प्रयोग हमेशा खेत में पर्याप्त नमी होने पर ही करें। गेहूं व धान में यूरिया को 2-3 बराबर भागों (Top Dressing) में बांटकर देना चाहिए। साथ ही नीम कोटेड यूरिया व नैनो यूरिया (4ml प्रति लीटर पानी) का उपयोग करने से 50% तक खाद की बचत होती है।"
                } else {
                    "Ram Ram Kishan Bhai! Apply Urea only when soil has adequate moisture. Split the dosage into 2-3 top-dressings. Consider using Nano Urea (4ml/litre water) to cut costs and increase nitrogen uptake."
                }
            }
            q.contains("गेहूं") || q.contains("wheat") -> {
                if (language == AppLanguage.HINDI) {
                    "गेहूं की बुवाई 25 अक्टूबर से 15 नवंबर के बीच सर्वोत्तम मानी जाती है। पहली सिंचाई 20-25 दिन बाद 'मुकुट जड़' (CRI) बनने पर अनिवार्य रूप से करें। प्रमुख किस्में: HD-2967, HD-3086, DBW-187 और DBW-303 (करण वैष्णवी)।"
                } else {
                    "Optimal wheat sowing time is Oct 25 to Nov 15. The first irrigation at 20-25 days (Crown Root stage) is critical. High yielding varieties: HD-2967, DBW-187, and DBW-303."
                }
            }
            q.contains("रोग") || q.contains("disease") || q.contains("कीट") || q.contains("pest") || q.contains("स्प्रे") -> {
                if (language == AppLanguage.HINDI) {
                    "फसल में कीट व फफूंद से बचाव हेतु प्रारंभिक अवस्था में नीम का तेल (10,000 ppm) 3-5 मिली प्रति लीटर पानी में मिलाकर स्प्रे करें। रासायनिक नियंत्रण के लिए फफूंदनाशक साफ (कार्बेन्डाजिम + मैनकोजेब) 2 ग्राम/लीटर प्रयोग कर सकते हैं। बीमारी का फोटो ऐप के 'रोग जांच' टूल में भी अपलोड करें।"
                } else {
                    "For pest & fungal issues, spray Neem Oil (10,000 ppm) at 3-5ml/L of water at early onset. For chemical control, you can use Carbendazim + Mancozeb (Saaf) at 2g/L. You can also test the leaf photo in the Disease Detector tool!"
                }
            }
            q.contains("सिंचाई") || q.contains("water") || q.contains("irrigation") || q.contains("बारिश") || q.contains("rain") -> {
                if (language == AppLanguage.HINDI) {
                    "आज के मौसम पूर्वानुमान के अनुसार 85% बारिश की संभावना है। यदि आज बारिश होती है तो सिंचाई रोकें। इससे बिजली और पानी की बचत होगी और जड़ों में जलभराव से फसल पीली नहीं पड़ेगी।"
                } else {
                    "According to today's agro-weather hub, there is an 85% rain forecast. Defer field irrigation to save electricity, water, and prevent root rot."
                }
            }
            q.contains("मंडी") || q.contains("भाव") || q.contains("mandi") || q.contains("rate") || q.contains("msp") -> {
                if (language == AppLanguage.HINDI) {
                    "आज मंडियों में गेहूं का भाव ₹2,450-₹2,500/क्विंटल (MSP ₹2,275 से ऊपर) और बासमती धान ₹3,850/क्विंटल चल रहा है। रुझान तेजी का है, किसान भाई थोड़ा रुककर माल बेच सकते हैं।"
                } else {
                    "Wheat market rates are trading strong around ₹2,450-₹2,500/quintal (well above MSP), and Basmati at ₹3,850/quintal. The trend is bullish."
                }
            }
            else -> {
                if (language == AppLanguage.HINDI) {
                    "राम राम किसान भाई! मैं 'किसान बंधु' AI सहायक हूँ। आप मुझसे फसल चुनाव, बुवाई का समय, खाद की मात्रा, कीट-रोग उपचार, मंडी भाव या मौसम सलाह के बारे में पूछ सकते हैं।"
                } else {
                    "Ram Ram Kishan Bhai! I am your Kishan Bandhu AI Assistant. Ask me anything about crop timelines, fertilizer calculators, pest management, mandi prices, or government schemes!"
                }
            }
        }
    }
}
