package com.example.data

enum class AppLanguage(
    val code: String,
    val nativeName: String,
    val englishName: String
) {
    HINDI("hi", "हिंदी", "Hindi"),
    ENGLISH("en", "English", "English"),
    PUNJABI("pa", "ਪੰਜਾਬੀ", "Punjabi"),
    BENGALI("bn", "বাংলা", "Bengali"),
    MARATHI("mr", "मराठी", "Marathi"),
    GUJARATI("gu", "ગુજરાતી", "Gujarati"),
    TELUGU("te", "తెలుగు", "Telugu"),
    TAMIL("ta", "தமிழ்", "Tamil"),
    KANNADA("kn", "ಕನ್ನಡ", "Kannada"),
    MALAYALAM("ml", "മലയാളം", "Malayalam"),
    ODIA("or", "ଓଡ଼ିଆ", "Odia"),
    ASSAMESE("as", "অসমীয়া", "Assamese"),
    URDU("ur", "اردو", "Urdu"),
    MAITHILI("mai", "मैथिली", "Maithili"),
    KASHMIRI("ks", "कॉशुर", "Kashmiri"),
    NEPALI("ne", "नेपाली", "Nepali"),
    KONKANI("kok", "कोंकणी", "Konkani"),
    DOGRI("doi", "डोगरी", "Dogri"),
    SINDHI("sd", "सिंधी", "Sindhi"),
    MANIPURI("mni", "মৈতৈলোন্", "Manipuri"),
    BODO("brx", "बर'", "Bodo"),
    SANTALI("sat", "ᱥᱟᱱᱛᱟᱲᱤ", "Santali"),
    SANSKRIT("sa", "संस्कृतम्", "Sanskrit")
}

object StringsProvider {
    fun appTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.HINDI, AppLanguage.MAITHILI, AppLanguage.DOGRI, AppLanguage.SINDHI -> "किसान बंधु प्रो"
        AppLanguage.PUNJABI -> "ਕਿਸਾਨ ਬੰਧੂ ਪ੍ਰੋ"
        AppLanguage.BENGALI, AppLanguage.ASSAMESE -> "কিষাণ বন্ধু প্রো"
        AppLanguage.MARATHI -> "किसान बंधू प्रो"
        AppLanguage.GUJARATI -> "કિસાન બંધુ પ્રો"
        AppLanguage.TELUGU -> "కిసాన్ బంధు ప్రో"
        AppLanguage.TAMIL -> "கிசான் பந்து புரோ"
        AppLanguage.KANNADA -> "ಕಿಸಾನ್ ಬಂಧು ಪ್ರೊ"
        AppLanguage.MALAYALAM -> "കിസാൻ ബന്ധു പ്രോ"
        AppLanguage.ODIA -> "କିଷାନ ବନ୍ଧୁ ପ୍ରୋ"
        AppLanguage.URDU -> "کسان بندھو پرو"
        AppLanguage.NEPALI -> "किसान बन्धु प्रो"
        AppLanguage.KONKANI -> "किसाण बंधू प्रो"
        AppLanguage.SANSKRIT -> "कृषक बन्धुः प्रो"
        else -> "Kishan Bandhu Pro"
    }

    fun tagline(lang: AppLanguage): String = when (lang) {
        AppLanguage.HINDI -> "किसानों का सच्चा साथी (AI व तकनीक से सशक्त)"
        AppLanguage.PUNJABI -> "ਕਿਸਾਨਾਂ ਦਾ ਸੱਚਾ ਸਾਥੀ (AI ਅਤੇ ਤਕਨਾਲੋਜੀ)"
        AppLanguage.BENGALI -> "কৃষকদের বিশ্বস্ত সঙ্গী (AI ও আধুনিক কৃষি)"
        AppLanguage.MARATHI -> "शेतकऱ्यांचा खरा सोबती (AI आणि तंत्रज्ञान)"
        AppLanguage.GUJARATI -> "ખેડૂતોનો સાચો સાથી (AI અને ટેકનોલોજી)"
        AppLanguage.TELUGU -> "రైతులకు నిజమైన నేస్తం (AI & సాంకేతికత)"
        AppLanguage.TAMIL -> "விவசாயிகளின் உற்ற தோழன் (AI & தொழில்நுட்பம்)"
        AppLanguage.KANNADA -> "ರೈತರ ನಿಜವಾದ ಒಡನಾಡಿ (AI ಮತ್ತು ತಂತ್ರಜ್ಞಾನ)"
        AppLanguage.MALAYALAM -> "കർഷകരുടെ വിശ്വസ്ത കൂട്ടുകാരൻ (AI സാങ്കേതികവിദ്യ)"
        AppLanguage.ODIA -> "କୃଷକଙ୍କ ପ୍ରକୃତ ସାଥୀ (AI ଏବଂ ପ୍ରଯୁକ୍ତିବିଦ୍ୟା)"
        AppLanguage.URDU -> "کسانوں کا سچا ساتھی (اے آئی اور ٹیکنالوجی)"
        else -> "Empowering Farmers with AI & Technology"
    }

    fun ramRamGreeting(lang: AppLanguage): String = when (lang) {
        AppLanguage.HINDI -> "राम राम किसान भाई! किसान बंधु प्रो में आपका स्वागत है।"
        AppLanguage.PUNJABI -> "ਸਤਿ ਸ੍ਰੀ ਅਕਾਲ ਕਿਸਾਨ ਵੀਰੋ! ਕਿਸਾਨ ਬੰਧੂ ਪ੍ਰੋ ਵਿੱਚ ਤੁਹਾਡਾ ਸਵਾਗਤ ਹੈ।"
        AppLanguage.MARATHI -> "राम राम शेतकरी मित्रा! किसान बंधू प्रो मध्ये आपले स्वागत आहे."
        AppLanguage.GUJARATI -> "જય શ્રી કૃષ્ણ ખેડૂત ભાઈ! કિસાન બંધુ પ્રો માં આપનું સ્વાગત છે."
        AppLanguage.BENGALI -> "নমস্কার কৃষক ভাই! কিষাণ বন্ধু প্রো-তে আপনাকে স্বাগত।"
        AppLanguage.TELUGU -> "నమస్కారం రైతు సోదరులారా! కిసాన్ బంధు ప్రోకి స్వాగతం."
        AppLanguage.TAMIL -> "வணக்கம் உழவர் தோழரே! கிசான் பந்து புரோவுக்கு உங்களை வரவேற்கிறோம்."
        AppLanguage.KANNADA -> "ನಮಸ್ಕಾರ ರೈತ ಮಿತ್ರರೇ! ಕಿಸಾನ್ ಬಂಧು ಪ್ರೊಗೆ ಸುಸ್ವಾಗತ."
        AppLanguage.MALAYALAM -> "നമസ്കാരം കർഷക സുഹൃത്തേ! കിസാൻ ബന്ധു പ്രോയിലേക്ക് സ്വാഗതം."
        AppLanguage.ODIA -> "ଜୟ ଜଗନ୍ନାଥ କୃଷକ ଭାଇ! କିଷାନ ବନ୍ଧୁ ପ୍ରୋକୁ ସ୍ଵାଗତ।"
        AppLanguage.URDU -> "آداب کسان بھائی! کسان بندھو پرو میں خوش آمدید۔"
        else -> "Ram Ram Kishan Bhai! Welcome to Kishan Bandhu Pro."
    }

    fun smartIrrigationAlertTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.HINDI -> "🌧️ स्मार्ट सिंचाई परामर्श (मौसम अलर्ट)"
        AppLanguage.PUNJABI -> "🌧️ ਸਮਾਰਟ ਸਿੰਚਾਈ ਸਲਾਹ (ਮੌਸਮ ਅਲਰਟ)"
        AppLanguage.MARATHI -> "🌧️ स्मार्ट सिंचन सल्ला (हवामान अलर्ट)"
        AppLanguage.GUJARATI -> "🌧️ સ્માર્ટ સિંચાઈ સલાહ (હવામાન ચેતવણી)"
        AppLanguage.BENGALI -> "🌧️ স্মার্ট সেচ পরামর্শ (আবহাওয়া সতর্কতা)"
        AppLanguage.TELUGU -> "🌧️ స్మార్ట్ సాగునీటి సలహా (వాతావరణ హెచ్చరిక)"
        AppLanguage.TAMIL -> "🌧️ ஸ்மார்ட் பாசன ஆலோசனை (வானிலை எச்சரிக்கை)"
        AppLanguage.KANNADA -> "🌧️ ಸ್ಮಾರ್ಟ್ ನೀರಾವರಿ ಸಲಹೆ (ಹವಾಮಾನ ಎಚ್ಚರಿಕೆ)"
        else -> "🌧️ Smart Irrigation Advice (Weather Alert)"
    }

    fun smartIrrigationAlertDesc(lang: AppLanguage): String = when (lang) {
        AppLanguage.HINDI -> "आज 85% बारिश की संभावना है। बिजली व पानी की बचत के लिए फसलों में आज पानी न लगाएं!"
        AppLanguage.PUNJABI -> "ਅੱਜ 85% ਮੀਂਹ ਪੈਣ ਦੀ ਸੰਭਾਵਨਾ ਹੈ। ਬਿਜਲੀ ਅਤੇ ਪਾਣੀ ਦੀ ਬਚਤ ਲਈ ਅੱਜ ਫਸਲਾਂ ਨੂੰ ਪਾਣੀ ਨਾ ਲਗਾਓ!"
        AppLanguage.MARATHI -> "आज 85% पावसाची शक्यता आहे. वीज आणि पाण्याची बचत करण्यासाठी पिकांना पाणी देणे टाळा!"
        AppLanguage.GUJARATI -> "આજે 85% વરસાદની સંભાવના છે. વીજળી અને પાણી બચાવવા આજે પાકને પાણી ન આપવું!"
        AppLanguage.BENGALI -> "আজ ৮৫% বৃষ্টির সম্ভাবনা রয়েছে। বিদ্যুৎ ও জল বাঁচাতে আজ ফসলে জল সেচ বন্ধ রাখুন!"
        AppLanguage.TELUGU -> "ఈ రోజు 85% వర్షం పడే అవకాశం ఉంది. విద్యుత్ మరియు నీటిని ఆదా చేయడానికి ఈ రోజు సాగునీరు పెట్టవద్దు!"
        AppLanguage.TAMIL -> "இன்று 85% மழைக்கு வாய்ப்புள்ளது. மின்சாரம் மற்றும் தண்ணீரை சேமிக்க இன்று பாசனம் செய்வதை தவிர்க்கவும்!"
        else -> "High chance of rain today (85%). Avoid watering crops to save electricity & water!"
    }

    fun tabHome(lang: AppLanguage): String = when (lang) {
        AppLanguage.HINDI -> "मुख्य"
        AppLanguage.PUNJABI -> "ਮੁੱਖ"
        AppLanguage.BENGALI -> "হোম"
        AppLanguage.MARATHI -> "मुख्य"
        AppLanguage.GUJARATI -> "મુખ્ય"
        AppLanguage.TELUGU -> "హోమ్"
        AppLanguage.TAMIL -> "முகப்பு"
        AppLanguage.KANNADA -> "ಮುಖಪುಟ"
        else -> "Home"
    }

    fun tabTasks(lang: AppLanguage): String = when (lang) {
        AppLanguage.HINDI -> "कार्य"
        AppLanguage.PUNJABI -> "ਕੰਮ"
        AppLanguage.BENGALI -> "কাজ"
        AppLanguage.MARATHI -> "कामे"
        AppLanguage.GUJARATI -> "કાર્યો"
        AppLanguage.TELUGU -> "పనులు"
        AppLanguage.TAMIL -> "பணிகள்"
        else -> "Tasks"
    }

    fun tabAi(lang: AppLanguage): String = when (lang) {
        AppLanguage.HINDI -> "किसान AI"
        AppLanguage.PUNJABI -> "ਕਿਸਾਨ AI"
        AppLanguage.BENGALI -> "কিষাণ AI"
        AppLanguage.MARATHI -> "किसान AI"
        AppLanguage.GUJARATI -> "કિસાન AI"
        AppLanguage.TELUGU -> "కిసాన్ AI"
        AppLanguage.TAMIL -> "கிசான் AI"
        else -> "Kisan AI"
    }

    fun tabMandi(lang: AppLanguage): String = when (lang) {
        AppLanguage.HINDI -> "मंडी व फसल"
        AppLanguage.PUNJABI -> "ਮੰਡੀ ਤੇ ਫਸਲ"
        AppLanguage.BENGALI -> "মান্ডি ও ফসল"
        AppLanguage.MARATHI -> "बाजार व पीक"
        AppLanguage.GUJARATI -> "માર્કેટ યાર્ડ"
        AppLanguage.TELUGU -> "మార్కెట్ ధరలు"
        AppLanguage.TAMIL -> "சந்தை விலை"
        else -> "Mandi & Crop"
    }

    fun tabTools(lang: AppLanguage): String = when (lang) {
        AppLanguage.HINDI -> "टूल्स व बहीखाता"
        AppLanguage.PUNJABI -> "ਟੂਲਜ਼ ਤੇ ਵਹੀਖਾਤਾ"
        AppLanguage.BENGALI -> "টুলস ও খাতা"
        AppLanguage.MARATHI -> "साधने व वहीखाते"
        AppLanguage.GUJARATI -> "સાધનો અને ચોપડો"
        AppLanguage.TELUGU -> "టూల్స్ & ఖాతా"
        AppLanguage.TAMIL -> "கருவிகள் & கணக்கு"
        else -> "Tools & Ledger"
    }
}
