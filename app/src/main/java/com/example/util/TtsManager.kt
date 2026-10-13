package com.example.util

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.data.AppLanguage
import com.example.data.CropInfo
import com.example.data.MandiPrice
import com.example.data.WeatherInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class TtsManager(private val context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _currentUtteranceTag = MutableStateFlow<String?>(null)
    val currentUtteranceTag: StateFlow<String?> = _currentUtteranceTag.asStateFlow()

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val hindiLocale = Locale.forLanguageTag("hi-IN")
            val result = tts?.setLanguage(hindiLocale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.language = Locale.ENGLISH
            }
            tts?.setSpeechRate(0.92f) // clear and comfortable pace for farmers
            tts?.setPitch(1.0f)

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                    _currentUtteranceTag.value = utteranceId
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                    _currentUtteranceTag.value = null
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                    _currentUtteranceTag.value = null
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    _isSpeaking.value = false
                    _currentUtteranceTag.value = null
                    Log.w("TtsManager", "TTS error code $errorCode for utterance $utteranceId")
                }
            })
            isInitialized = true
        } else {
            Log.e("TtsManager", "TTS Initialization failed with status: $status")
        }
    }

    fun speak(text: String, language: AppLanguage, utteranceTag: String = "general_speech") {
        if (!isInitialized || tts == null) return
        try {
            val targetLocale = Locale.forLanguageTag(language.code)
            val availability = tts?.isLanguageAvailable(targetLocale) ?: TextToSpeech.LANG_NOT_SUPPORTED
            if (availability >= TextToSpeech.LANG_AVAILABLE) {
                tts?.language = targetLocale
            } else {
                val hiLocale = Locale.forLanguageTag("hi-IN")
                val hiAvail = tts?.isLanguageAvailable(hiLocale) ?: TextToSpeech.LANG_NOT_SUPPORTED
                if (hiAvail >= TextToSpeech.LANG_AVAILABLE && language != AppLanguage.ENGLISH) {
                    tts?.language = hiLocale
                } else {
                    tts?.language = Locale.ENGLISH
                }
            }
            _currentUtteranceTag.value = utteranceTag
            val params = Bundle().apply {
                putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceTag)
            }
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, utteranceTag)
        } catch (e: Exception) {
            Log.e("TtsManager", "TTS speak error", e)
            _isSpeaking.value = false
            _currentUtteranceTag.value = null
        }
    }

    fun speak(text: String, isHindi: Boolean = true, utteranceTag: String = "general_speech") {
        speak(text, if (isHindi) AppLanguage.HINDI else AppLanguage.ENGLISH, utteranceTag)
    }

    fun speakWeatherUpdate(weather: WeatherInfo, language: AppLanguage) {
        val utteranceTag = "weather_update"
        val text = if (language == AppLanguage.HINDI) {
            "राम राम किसान भाई! आज का मौसम अपडेट। क्षेत्र: करनाल। तापमान ${weather.temperature} डिग्री सेल्सियस है। ${weather.conditionHi}। वर्षा की संभावना ${weather.rainChance} प्रतिशत, हवा में नमी ${weather.humidity} प्रतिशत, तथा हवा की गति ${weather.windSpeedKmh} किलोमीटर प्रति घंटा है। विशेष परामर्श: ${weather.irrigationTipHi}"
        } else {
            "Ram Ram Kishan Bhai! Today's weather update for Karnal region. Temperature is ${weather.temperature} degrees Celsius. ${weather.conditionEn}. Rain probability is ${weather.rainChance} percent, humidity is ${weather.humidity} percent, and wind speed is ${weather.windSpeedKmh} kilometers per hour. Special advice: ${weather.irrigationTipEn}"
        }
        speak(text = text, isHindi = (language == AppLanguage.HINDI), utteranceTag = utteranceTag)
    }

    fun speakSmartIrrigation(weather: WeatherInfo, language: AppLanguage) {
        val utteranceTag = "irrigation_alert"
        val text = if (language == AppLanguage.HINDI) {
            "स्मार्ट सिंचाई परामर्श! ${weather.irrigationTipHi} आज वर्षा की संभावना ${weather.rainChance} प्रतिशत है।"
        } else {
            "Smart irrigation advisory! ${weather.irrigationTipEn} Rain probability is ${weather.rainChance} percent today."
        }
        speak(text = text, isHindi = (language == AppLanguage.HINDI), utteranceTag = utteranceTag)
    }

    fun speakCropAdvice(crop: CropInfo, language: AppLanguage) {
        val utteranceTag = "crop_advice_${crop.id}"
        val text = if (language == AppLanguage.HINDI) {
            "फसल कृषि परामर्श: ${crop.nameHi}। उपयुक्त बुवाई समय: ${crop.seasonHi}। फसल की अवधि: ${crop.durationDays}। उपयुक्त मिट्टी: ${crop.soilHi}। वर्षा व जल आवश्यकता: ${crop.rainfallHi}। अनुशंसित खाद मात्रा: ${crop.fertilizerHi}।"
        } else {
            "Crop cultivation advice for ${crop.nameEn}. Recommended season is ${crop.seasonEn}. Crop duration is ${crop.durationDays}. Ideal soil type: ${crop.soilEn}. Rainfall requirement: ${crop.rainfallEn}. Recommended fertilizer dosage: ${crop.fertilizerEn}."
        }
        speak(text = text, isHindi = (language == AppLanguage.HINDI), utteranceTag = utteranceTag)
    }

    fun speakMandiRate(item: MandiPrice, language: AppLanguage) {
        val utteranceTag = "mandi_rate_${item.id}"
        val trendTextHi = if (item.isBullish) "तेजी का रुझान, भाव ${item.changePercent} प्रतिशत ऊपर है।" else "मंदी का रुझान, भाव ${item.changePercent} प्रतिशत नीचे है।"
        val trendTextEn = if (item.isBullish) "Bullish trend, price is up by ${item.changePercent} percent." else "Bearish trend, price is down by ${item.changePercent} percent."
        val mspTextHi = if (item.mspPrice > 0) "सरकारी एमएसपी ₹${item.mspPrice} प्रति क्विंटल है।" else "यह खुली बाजार सब्जी मंडी दर है।"
        val mspTextEn = if (item.mspPrice > 0) "Government MSP is ₹${item.mspPrice} per quintal." else "This is an open market price."

        val text = if (language == AppLanguage.HINDI) {
            "मंडी भाव रिपोर्ट: ${item.cropHi}। मंडी: ${item.mandiHi}। आज का भाव ₹${item.currentPrice} प्रति क्विंटल। $mspTextHi $trendTextHi व्यापार परामर्श: ${item.adviceHi}"
        } else {
            "Mandi rate report: ${item.cropEn} at ${item.mandiEn}. Today's price is ₹${item.currentPrice} per quintal. $mspTextEn $trendTextEn Market advisory: ${item.adviceEn}"
        }
        speak(text = text, isHindi = (language == AppLanguage.HINDI), utteranceTag = utteranceTag)
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
        _currentUtteranceTag.value = null
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        _isSpeaking.value = false
        _currentUtteranceTag.value = null
    }
}
