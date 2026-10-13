package com.example.data

object AgroRepository {

    val initialTasks = listOf(
        TaskItem(
            id = "t1",
            titleHi = "गेहूं के खेत में पहली सिंचाई (CRI चरण)",
            titleEn = "First irrigation in wheat field (CRI stage)",
            category = TaskCategory.IRRIGATION,
            dueDate = "कल (Tomorrow)",
            isDone = false,
            notes = "बुवाई के 21 दिन बाद / 21 days after sowing"
        ),
        TaskItem(
            id = "t2",
            titleHi = "धान की पौध में जिंक सल्फेट व यूरिया छिड़काव",
            titleEn = "Apply Zinc Sulphate & Urea in Paddy",
            category = TaskCategory.FERTILIZER,
            dueDate = "15 Oct",
            isDone = true,
            notes = "खैरा रोग की रोकथाम हेतु"
        ),
        TaskItem(
            id = "t3",
            titleHi = "टमाटर के पौधों में फल छेदक कीट निरीक्षण",
            titleEn = "Inspect fruit borer pest in Tomato plants",
            category = TaskCategory.PEST_CONTROL,
            dueDate = "16 Oct",
            isDone = false,
            notes = "नीम तेल 5ml/L छिड़काव करें"
        ),
        TaskItem(
            id = "t4",
            titleHi = "सरसों की बुवाई पूर्व बीज शोधन (Bavistin)",
            titleEn = "Mustard seed treatment before sowing",
            category = TaskCategory.SOWING,
            dueDate = "18 Oct",
            isDone = false,
            notes = "2 ग्राम प्रति किलो बीज दर"
        ),
        TaskItem(
            id = "t5",
            titleHi = "कपास की प्रथम तुड़ाई (Picking)",
            titleEn = "First picking of Cotton bolls",
            category = TaskCategory.HARVESTING,
            dueDate = "20 Oct",
            isDone = false,
            notes = "सूखे मौसम में ही तुड़ाई करें"
        ),
        TaskItem(
            id = "t6",
            titleHi = "करनाल मंडी में मक्का बिक्री व पर्ची रसीद",
            titleEn = "Mandi sale of Maize at Karnal market",
            category = TaskCategory.MANDI,
            dueDate = "22 Oct",
            isDone = true,
            notes = "MSP ₹2,225/क्विंटल से अधिक रेट पर बेचना"
        )
    )

    val crops = listOf(
        CropInfo(
            id = "wheat",
            nameHi = "गेहूं (Wheat)",
            nameEn = "Wheat (गेहूं)",
            seasonHi = "रबी (अक्टूबर - नवंबर बुवाई)",
            seasonEn = "Rabi (Oct - Nov Sowing)",
            durationDays = "120 - 140 दिन",
            soilHi = "दोमट एवं बलुई दोमट मिट्टी (pH 6.0 - 7.5)",
            soilEn = "Loam to Sandy Loam (pH 6.0 - 7.5)",
            rainfallHi = "350 - 450 मिमी (4-6 हल्की सिंचाइयां)",
            rainfallEn = "350 - 450 mm (4-6 light irrigations)",
            idealTemp = "15°C - 25°C",
            stages = listOf(
                CropStage("CRI (मुकुट जड़)", "Crown Root (CRI)", "20-25 दिन", "प्रथम सिंचाई एवं यूरिया की पहली खुराक", "First irrigation and top dressing Urea"),
                CropStage("कल्ले फूटना", "Tillering", "40-45 दिन", "दूसरी सिंचाई, खरपतवार नियंत्रण", "Second irrigation, weed control"),
                CropStage("गाभा व बाली", "Boot & Heading", "70-85 दिन", "तीसरी सिंचाई, यूरिया की बची खुराक", "Third irrigation, moisture check"),
                CropStage("दूधिया व दाना", "Milking & Dough", "100-115 दिन", "चौथी सिंचाई, तेज हवा में न सींचे", "Fourth irrigation, avoid windy days"),
                CropStage("परिपक्वता व कटाई", "Maturity & Harvesting", "125-140 दिन", "दानों में 12% नमी पर कटाई व गहाई", "Harvest when grain moisture is 12%")
            ),
            fertilizerHi = "यूरिया: 110-130 किग्रा/एकड़, डीएपी: 50 किग्रा/एकड़, पोटाश: 20 किग्रा/एकड़",
            fertilizerEn = "Urea: 110-130 kg/acre, DAP: 50 kg/acre, MOP Potash: 20 kg/acre"
        ),
        CropInfo(
            id = "paddy",
            nameHi = "धान (Paddy/Rice)",
            nameEn = "Paddy / Rice (धान)",
            seasonHi = "खरीफ (जून - जुलाई रोपाई)",
            seasonEn = "Kharif (June - July Transplanting)",
            durationDays = "115 - 145 दिन",
            soilHi = "चिकनी दोमट मिट्टी (पानी रोकने वाली)",
            soilEn = "Clay loam soil with high water retention",
            rainfallHi = "1000 - 1500 मिमी (सतत नमी)",
            rainfallEn = "1000 - 1500 mm (consistent moisture)",
            idealTemp = "22°C - 34°C",
            stages = listOf(
                CropStage("नर्सरी तैयारी", "Nursery", "1-25 दिन", "बीजोपचार, हरी खाद डालना", "Seed treatment, nursery raising"),
                CropStage("रोपाई", "Transplanting", "25-30 दिन", "2-3 पौधे प्रति थान, 20x15 सेमी", "2-3 seedlings per hill, 20x15 cm"),
                CropStage("कल्ले फूटना", "Tillering", "40-60 दिन", "जिंक सल्फेट व यूरिया छिड़काव", "Apply Zinc Sulphate and Urea"),
                CropStage("बाली निकलना", "Panicle Initiation", "75-90 दिन", "पर्याप्त पानी रखें (2-3 सेमी)", "Maintain 2-3 cm water layer"),
                CropStage("कटाई", "Harvesting", "120-135 दिन", "बाली पीली पड़ने पर पानी निकालें व कटाई", "Drain field 10 days before harvest")
            ),
            fertilizerHi = "यूरिया: 90 किग्रा/एकड़, डीएपी: 45 किग्रा, जिंक सल्फेट: 10 किग्रा",
            fertilizerEn = "Urea: 90 kg/acre, DAP: 45 kg, Zinc Sulphate: 10 kg"
        ),
        CropInfo(
            id = "tomato",
            nameHi = "टमाटर (Tomato)",
            nameEn = "Tomato (टमाटर)",
            seasonHi = "रबी / जायद / खरीफ (वर्षभर संभव)",
            seasonEn = "All Season (Rabi, Zaid, Kharif)",
            durationDays = "90 - 120 दिन",
            soilHi = "जीवांश युक्त बलुई दोमट (pH 6.0 - 7.0)",
            soilEn = "Rich Sandy Loam with good drainage (pH 6-7)",
            rainfallHi = "ड्रिप सिंचाई श्रेष्ठ (500-600 मिमी)",
            rainfallEn = "Drip irrigation optimal (500-600 mm)",
            idealTemp = "18°C - 28°C",
            stages = listOf(
                CropStage("पौध रोपाई", "Transplanting", "25-30 दिन", "उठी हुई क्यारियों पर 60x45 सेमी पर रोपाई", "Raised bed planting with mulching"),
                CropStage("शाखा वृद्धि व सहारा", "Vegetative & Staking", "40-55 दिन", "बांस व सुतली से सहारा (Staking)", "Stake plants with bamboo sticks"),
                CropStage("फूल व फल लगना", "Flowering & Fruit set", "60-80 दिन", "बोरॉन व कैल्शियम नाइट्रेट छिड़काव", "Spray Boron & Calcium Nitrate"),
                CropStage("तुड़ाई (Harvest)", "Harvesting", "85-120 दिन", "हर 4-5 दिन पर रंग आने पर तुड़ाई", "Pick every 4-5 days when pink/red")
            ),
            fertilizerHi = "सड़ी गोबर खाद: 8-10 टन/एकड़, NPK 19:19:19 ड्रिप द्वारा 3 किग्रा/सप्ताह",
            fertilizerEn = "Organic Compost: 8-10 tons/acre, NPK 19:19:19 via Drip 3 kg/week"
        ),
        CropInfo(
            id = "mustard",
            nameHi = "सरसों (Mustard)",
            nameEn = "Mustard (सरसों)",
            seasonHi = "रबी (सितंबर - अक्टूबर बुवाई)",
            seasonEn = "Rabi (Sep - Oct Sowing)",
            durationDays = "105 - 125 दिन",
            soilHi = "दोमट से हल्की मिट्टी, कम पानी में अनुकूल",
            soilEn = "Loamy to light soil, drought hardy",
            rainfallHi = "250 - 400 मिमी (2-3 सिंचाइयां)",
            rainfallEn = "250 - 400 mm (2-3 irrigations)",
            idealTemp = "12°C - 24°C",
            stages = listOf(
                CropStage("बुवाई", "Sowing", "1-10 दिन", "पंक्ति से पंक्ति 45 सेमी, बीज गहराई 3 सेमी", "Line spacing 45 cm, depth 3 cm"),
                CropStage("छंटाई व पहली सिंचाई", "Thinning & 1st Irrigation", "30-35 दिन", "पौधों की दूरी 15 सेमी करें व पानी दें", "Thin to 15 cm spacing, irrigate"),
                CropStage("फूल व फली", "Flowering & Pods", "60-75 दिन", "माहू (Aphid) कीट नियंत्रण हेतु नीम अर्क", "Spray Neem oil for Aphid protection"),
                CropStage("पकना व कटाई", "Maturity", "110-120 दिन", "फलियां पीली पड़ने पर सुबह कटाई", "Harvest early morning when pods yellow")
            ),
            fertilizerHi = "यूरिया: 65 किग्रा, सिंगल सुपर फॉस्फेट (SSP): 150 किग्रा (सल्फर युक्त)",
            fertilizerEn = "Urea: 65 kg, Single Super Phosphate (SSP): 150 kg with Sulphur"
        ),
        CropInfo(
            id = "cotton",
            nameHi = "कपास (Cotton)",
            nameEn = "Cotton (कपास)",
            seasonHi = "खरीफ (अप्रैल - मई बुवाई)",
            seasonEn = "Kharif (April - May Sowing)",
            durationDays = "150 - 180 दिन",
            soilHi = "काली मिट्टी या गहरी दोमट मिट्टी",
            soilEn = "Deep Black Cotton Soil or alluvial loam",
            rainfallHi = "600 - 900 मिमी",
            rainfallEn = "600 - 900 mm",
            idealTemp = "21°C - 35°C",
            stages = listOf(
                CropStage("अंकुरण व वृद्धि", "Vegetative", "1-45 दिन", "निराई-गुड़ाई व यूरिया की पहली मात्रा", "Inter-cultivation & first Urea dose"),
                CropStage("फूल व टिंडे", "Flowering & Boll formation", "60-110 दिन", "गुलाबी सुंडी (PBW) फेरोमोन ट्रैप लगाएं", "Install Pheromone traps for PBW"),
                CropStage("टिंडे खिलना व तुड़ाई", "Boll Bursting & Picking", "130-170 दिन", "साफ धूप में 3-4 चक्रों में तुड़ाई", "Pick clean bolls in 3-4 flushes")
            ),
            fertilizerHi = "यूरिया: 90 किग्रा, डीएपी: 50 किग्रा, पोटाश: 30 किग्रा",
            fertilizerEn = "Urea: 90 kg, DAP: 50 kg, MOP: 30 kg"
        )
    )

    val diseases = listOf(
        DiseaseInfo(
            id = "early_blight",
            nameHi = "टमाटर का अगेती झुलसा (Early Blight)",
            nameEn = "Tomato Early Blight (Alternaria)",
            cropHi = "टमाटर / आलू",
            cropEn = "Tomato / Potato",
            severityHi = "मध्यम (Medium)",
            severityEn = "Medium",
            symptomsHi = "पुरानी पत्तियों पर भूरे-काले संकेंद्रित छल्ले (Target spots) बनते हैं। पत्तियां सूखकर गिरने लगती हैं।",
            symptomsEn = "Brown-black concentric rings (bull's-eye target spots) on lower leaves, leading to leaf drop.",
            organicTreatmentHi = "ट्राइकोडर्मा विरिडी 5 ग्राम/लीटर या नीम का तेल (10,000 ppm) 3 मिली/लीटर पानी में घोलकर छिड़कें।",
            organicTreatmentEn = "Trichoderma viride @ 5g/L or Neem oil (10,000 ppm) @ 3ml/L water spray.",
            chemicalTreatmentHi = "मैनकोजेब 75% WP (2.5 ग्राम/लीटर) या कॉपर ऑक्सीक्लोराइड 50% WP (3 ग्राम/लीटर) का छिड़काव करें।",
            chemicalTreatmentEn = "Mancozeb 75% WP (2.5g/L) or Copper Oxychloride 50% WP (3g/L) spray."
        ),
        DiseaseInfo(
            id = "yellow_rust",
            nameHi = "गेहूं का पीला रतुआ (Yellow Stripe Rust)",
            nameEn = "Wheat Yellow / Stripe Rust (Puccinia)",
            cropHi = "गेहूं (Wheat)",
            cropEn = "Wheat",
            severityHi = "गंभीर (High Alert)",
            severityEn = "High Alert",
            symptomsHi = "पत्तियों पर पीली हल्दी जैसी धारियां उभरती हैं। छूने पर अंगुलियों पर पीला पाउडर लग जाता है।",
            symptomsEn = "Bright yellow stripe pustules forming powdery lines along leaf veins; stains fingers yellow.",
            organicTreatmentHi = "छाछ (खट्टी लस्सी) 5 लीटर + हींग 50 ग्राम प्रति 100 लीटर पानी में मिलाकर छिड़कें।",
            organicTreatmentEn = "Sour buttermilk (5L) + Asafoetida (50g) in 100L water spray.",
            chemicalTreatmentHi = "प्रोपिकोनाजोल 25% EC (टिल्ट) 1 मिली प्रति लीटर पानी की दर से तुरंत स्प्रे करें।",
            chemicalTreatmentEn = "Propiconazole 25% EC (Tilt) @ 1 ml/litre water immediate spray."
        ),
        DiseaseInfo(
            id = "powdery_mildew",
            nameHi = "चूर्णी फफूंद (Powdery Mildew)",
            nameEn = "Powdery Mildew (Erysiphe)",
            cropHi = "सब्जियां / मटर / सरसों",
            cropEn = "Vegetables / Peas / Mustard",
            severityHi = "हल्का से मध्यम (Mild to Medium)",
            severityEn = "Mild to Medium",
            symptomsHi = "पत्तियों की ऊपरी व निचली सतह पर सफेद आटे जैसा चूर्ण जम जाता है। प्रकाश संश्लेषण रुक जाता है।",
            symptomsEn = "White flour-like powdery growth coating upper and lower leaf surfaces, reducing photosynthesis.",
            organicTreatmentHi = "घुलनशील सल्फर (80% WDG) 2.5 ग्राम/लीटर या बेकिंग सोडा (5 ग्राम/लीटर) का छिड़काव करें।",
            organicTreatmentEn = "Wettable Sulphur 80% WDG @ 2.5g/L or Baking Soda @ 5g/L water.",
            chemicalTreatmentHi = "हेक्साकोनाजोल 5% EC (2 मिली/लीटर) या डिनोकैप (1 मिली/लीटर) का छिड़काव करें।",
            chemicalTreatmentEn = "Hexaconazole 5% EC @ 2ml/L or Dinocap @ 1ml/L spray."
        ),
        DiseaseInfo(
            id = "leaf_curl",
            nameHi = "मिर्च व टमाटर का पर्ण कुंचन (Leaf Curl Virus)",
            nameEn = "Chilli / Tomato Leaf Curl Virus",
            cropHi = "मिर्च, टमाटर, पपीता",
            cropEn = "Chilli, Tomato, Papaya",
            severityHi = "गंभीर (Severe)",
            severityEn = "Severe",
            symptomsHi = "पत्तियां ऊपर या नीचे की ओर मुड़ जाती हैं, नाव जैसी आकृति बन जाती है और पौधा बौना रह जाता है। सफेद मक्खी द्वारा फैलता है।",
            symptomsEn = "Leaves curl upwards into boat shapes, become thick and brittle; stunted growth spread by whiteflies.",
            organicTreatmentHi = "पीले चिपचिपे कार्ड (Yellow Sticky Traps) 15-20 प्रति एकड़ लगाएं और नीम तेल 5 मिली/लीटर छिड़कें।",
            organicTreatmentEn = "Install 15-20 Yellow Sticky Traps per acre and spray Neem Oil @ 5ml/L.",
            chemicalTreatmentHi = "सफेद मक्खी नियंत्रण हेतु डायफेन्थियूरॉन 50% WP (1.2 ग्राम/लीटर) या एसिटामिप्रिड 20% SP (0.5 ग्राम/लीटर)।",
            chemicalTreatmentEn = "Control whitefly vector with Diafenthiuron 50% WP (1.2g/L) or Acetamiprid 20% SP (0.5g/L)."
        ),
        DiseaseInfo(
            id = "healthy_crop",
            nameHi = "स्वस्थ फसल (Healthy Crop)",
            nameEn = "Healthy Crop (No Disease Detected)",
            cropHi = "सामान्य (General)",
            cropEn = "General",
            severityHi = "सुरक्षित (Normal)",
            severityEn = "Safe",
            symptomsHi = "पत्तियां हरी-भरी, चमकदार और रोगमुक्त हैं। पौधे की वृद्धि सामान्य और मजबूत है।",
            symptomsEn = "Leaves are lush green, vigorous, and free from pathogenic lesions or discoloration.",
            organicTreatmentHi = "नियमित अंतराल पर जीवामृत या वर्मीवाश का छिड़काव जारी रखें ताकि रोग प्रतिरोधक क्षमता बनी रहे।",
            organicTreatmentEn = "Maintain vigor with regular Jeevamrut or Vermiwash foliar sprays.",
            chemicalTreatmentHi = "किसी रासायनिक दवा की आवश्यकता नहीं है। संतुलित सिंचाई जारी रखें।",
            chemicalTreatmentEn = "No chemical intervention needed. Maintain balanced moisture and nutrients."
        )
    )

    val mandiPrices = listOf(
        MandiPrice(
            id = "m1",
            cropHi = "गेहूं (Wheat - शरबती/HD-2967)",
            cropEn = "Wheat (Sharbati / HD-2967)",
            mandiHi = "करनाल मंडी (हरियाणा)",
            mandiEn = "Karnal Mandi (Haryana)",
            currentPrice = 2480,
            mspPrice = 2275,
            changePercent = +3.4,
            isBullish = true,
            adviceHi = "तेजी का रुझान! MSP से ₹205 ऊपर मिल रहा है। रोककर बेचने पर और लाभ संभव।",
            adviceEn = "Bullish trend! ₹205 above MSP. Holding may yield higher returns.",
            category = "अनाज",
            minPrice = 2390,
            maxPrice = 2540,
            arrivalsTons = 420,
            weeklyTrend = listOf(2360, 2390, 2410, 2435, 2450, 2480)
        ),
        MandiPrice(
            id = "m2",
            cropHi = "धान (Paddy - बासमती 1121)",
            cropEn = "Paddy (Basmati 1121)",
            mandiHi = "अमृतसर मंडी (पंजाब)",
            mandiEn = "Amritsar Mandi (Punjab)",
            currentPrice = 3850,
            mspPrice = 2300,
            changePercent = +5.1,
            isBullish = true,
            adviceHi = "मजबूत मांग! निर्यातकों द्वारा अच्छी खरीद, भाव में और तेजी के आसार।",
            adviceEn = "Strong export demand! Favorable time to sell in staggered lots.",
            category = "अनाज",
            minPrice = 3680,
            maxPrice = 3920,
            arrivalsTons = 680,
            weeklyTrend = listOf(3600, 3650, 3710, 3740, 3800, 3850)
        ),
        MandiPrice(
            id = "m3",
            cropHi = "सरसों (Mustard Seed / राई)",
            cropEn = "Mustard Seed (Rai)",
            mandiHi = "भरतपुर मंडी (राजस्थान)",
            mandiEn = "Bharatpur Mandi (Rajasthan)",
            currentPrice = 5620,
            mspPrice = 5650,
            changePercent = -0.8,
            isBullish = false,
            adviceHi = "MSP के करीब स्थिर। तेल मिलों की आवक बढ़ने से 1-2 दिन रुकना बेहतर रहेगा।",
            adviceEn = "Stable near MSP. Better to hold 1-2 days before unloading.",
            category = "तिलहन",
            minPrice = 5510,
            maxPrice = 5700,
            arrivalsTons = 310,
            weeklyTrend = listOf(5720, 5690, 5680, 5650, 5640, 5620)
        ),
        MandiPrice(
            id = "m4",
            cropHi = "चना (Gram / Desi Chana)",
            cropEn = "Gram / Desi Chana",
            mandiHi = "बीकानेर मंडी (राजस्थान)",
            mandiEn = "Bikaner Mandi (Rajasthan)",
            currentPrice = 5980,
            mspPrice = 5440,
            changePercent = +2.7,
            isBullish = true,
            adviceHi = "दाल मिलों की भारी लिवाली। भाव MSP से ₹540 अधिक पर चल रहे हैं।",
            adviceEn = "Heavy mill demand. Trading ₹540 higher than MSP.",
            category = "दलहन",
            minPrice = 5820,
            maxPrice = 6050,
            arrivalsTons = 240,
            weeklyTrend = listOf(5750, 5810, 5860, 5900, 5940, 5980)
        ),
        MandiPrice(
            id = "m5",
            cropHi = "सोयाबीन (Soybean Yellow)",
            cropEn = "Soybean Yellow",
            mandiHi = "इंदौर मंडी (मध्य प्रदेश)",
            mandiEn = "Indore Mandi (Madhya Pradesh)",
            currentPrice = 4510,
            mspPrice = 4892,
            changePercent = -1.5,
            isBullish = false,
            adviceHi = "MSP से नीचे व्यापार। NAFED सरकारी खरीद केंद्रों पर MSP पर बेचना श्रेयस्कर।",
            adviceEn = "Trading below MSP. Prefer selling at government NAFED centers at MSP.",
            category = "तिलहन",
            minPrice = 4420,
            maxPrice = 4600,
            arrivalsTons = 520,
            weeklyTrend = listOf(4680, 4630, 4590, 4560, 4540, 4510)
        ),
        MandiPrice(
            id = "m6",
            cropHi = "मक्का (Maize / Hybrid Corn)",
            cropEn = "Maize (Hybrid Corn)",
            mandiHi = "दावणगेरे मंडी (कर्नाटक)",
            mandiEn = "Davanagere Mandi (Karnataka)",
            currentPrice = 2340,
            mspPrice = 2090,
            changePercent = +4.2,
            isBullish = true,
            adviceHi = "पोल्ट्री व स्टार्च उद्योगों से मजबूत मांग। भाव में मजबूती जारी रहेगी।",
            adviceEn = "Firm poultry & starch feed demand. Bullish trend likely to sustain.",
            category = "अनाज",
            minPrice = 2230,
            maxPrice = 2390,
            arrivalsTons = 380,
            weeklyTrend = listOf(2200, 2240, 2260, 2290, 2310, 2340)
        ),
        MandiPrice(
            id = "m7",
            cropHi = "कपास (Cotton - Medium Staple)",
            cropEn = "Cotton (Medium Staple)",
            mandiHi = "राजकोट मंडी (गुजरात)",
            mandiEn = "Rajkot Mandi (Gujarat)",
            currentPrice = 7120,
            mspPrice = 6620,
            changePercent = +1.9,
            isBullish = true,
            adviceHi = "CCI सरकारी खरीद सक्रिय। भाव MSP से ₹500 ऊपर, अच्छी गुणवत्ता पर प्रीमियम।",
            adviceEn = "CCI procurement active. Prices ₹500 above MSP with quality premium.",
            category = "नकदी",
            minPrice = 6950,
            maxPrice = 7250,
            arrivalsTons = 490,
            weeklyTrend = listOf(6900, 6980, 7020, 7080, 7100, 7120)
        ),
        MandiPrice(
            id = "m8",
            cropHi = "गुड़ (Jaggery / Sugarcane Gur)",
            cropEn = "Jaggery / Sugarcane Gur",
            mandiHi = "मुजफ्फरनगर मंडी (उत्तर प्रदेश)",
            mandiEn = "Muzaffarnagar Mandi (Uttar Pradesh)",
            currentPrice = 3680,
            mspPrice = 0,
            changePercent = +2.4,
            isBullish = true,
            adviceHi = "त्योहारी मांग से कोल्हू आवक पर तेज भाव। तैयार माल समय पर निकालें।",
            adviceEn = "Festive demand pushing jaggery prices up. Good window for selling.",
            category = "नकदी",
            minPrice = 3520,
            maxPrice = 3750,
            arrivalsTons = 190,
            weeklyTrend = listOf(3500, 3540, 3590, 3620, 3650, 3680)
        ),
        MandiPrice(
            id = "m9",
            cropHi = "टमाटर (Tomato Hybrid)",
            cropEn = "Tomato Hybrid",
            mandiHi = "आजादपुर मंडी (दिल्ली)",
            mandiEn = "Azadpur Mandi (Delhi)",
            currentPrice = 2150,
            mspPrice = 0,
            changePercent = +12.5,
            isBullish = true,
            adviceHi = "आवक कम होने से भारी तेजी! तुरंत मंडी में माल निकालना लाभदायक है।",
            adviceEn = "High demand due to limited supply. Recommend selling immediately.",
            category = "सब्जियां",
            minPrice = 1850,
            maxPrice = 2300,
            arrivalsTons = 850,
            weeklyTrend = listOf(1650, 1720, 1850, 1920, 2040, 2150)
        ),
        MandiPrice(
            id = "m10",
            cropHi = "आलू (Potato - कुफरी पुखराज)",
            cropEn = "Potato (Kufri Pukhraj)",
            mandiHi = "आगरा मंडी (उत्तर प्रदेश)",
            mandiEn = "Agra Mandi (Uttar Pradesh)",
            currentPrice = 1420,
            mspPrice = 0,
            changePercent = -3.2,
            isBullish = false,
            adviceHi = "कोल्ड स्टोरेज से भारी आवक। ग्रेडिंग कर के ही बेचें ताकि बेहतर भाव मिले।",
            adviceEn = "High cold storage arrivals. Grade produce to secure premium pricing.",
            category = "सब्जियां",
            minPrice = 1320,
            maxPrice = 1510,
            arrivalsTons = 1100,
            weeklyTrend = listOf(1520, 1490, 1470, 1450, 1440, 1420)
        ),
        MandiPrice(
            id = "m11",
            cropHi = "प्याज (Onion Red Nashik)",
            cropEn = "Onion Red Nashik",
            mandiHi = "लासलगांव मंडी (महाराष्ट्र)",
            mandiEn = "Lasalgaon Mandi (Maharashtra)",
            currentPrice = 1850,
            mspPrice = 0,
            changePercent = -2.1,
            isBullish = false,
            adviceHi = "नई लाल प्याज की आवक तेज। अच्छी गुणवत्ता का सूखा प्याज रोककर बेचें।",
            adviceEn = "Fresh red onion arrivals peaking. Store well-cured onions for later.",
            category = "सब्जियां",
            minPrice = 1680,
            maxPrice = 1980,
            arrivalsTons = 940,
            weeklyTrend = listOf(1980, 1940, 1900, 1880, 1860, 1850)
        ),
        MandiPrice(
            id = "m12",
            cropHi = "लहसुन (Garlic - देशी बोल्ड)",
            cropEn = "Garlic (Desi Bold)",
            mandiHi = "मंदसौर मंडी (मध्य प्रदेश)",
            mandiEn = "Mandsaur Mandi (Madhya Pradesh)",
            currentPrice = 14200,
            mspPrice = 0,
            changePercent = +8.5,
            isBullish = true,
            adviceHi = "सफेद सोना! रिकॉर्ड मांग से भाव ऐतिहासिक स्तर पर। ऊंचे भाव का लाभ उठाएं।",
            adviceEn = "Record demand pushing prices to highs. Outstanding selling opportunity.",
            category = "मसाले",
            minPrice = 13400,
            maxPrice = 14800,
            arrivalsTons = 160,
            weeklyTrend = listOf(12800, 13100, 13500, 13800, 14000, 14200)
        )
    )

    val schemes = listOf(
        SchemeInfo(
            id = "pm_kisan",
            titleHi = "प्रधानमंत्री किसान सम्मान निधि (PM-KISAN)",
            titleEn = "PM-Kisan Samman Nidhi Yojana",
            benefitHi = "प्रति वर्ष ₹6,000 की प्रत्यक्ष आर्थिक सहायता (₹2,000 की 3 समान किस्तों में)",
            benefitEn = "Direct income support of ₹6,000 per year in 3 equal installments",
            eligibilityHi = "सभी भूमिधारक किसान परिवार जिनके नाम खेती योग्य भूमि है। आधार e-KYC अनिवार्य।",
            eligibilityEn = "All landholder farmer families with cultivable land. Aadhaar e-KYC mandatory.",
            docsHi = "आधार कार्ड, खसरा-खतौनी (जमीन के कागजात), बैंक पासबुक, मोबाइल नंबर",
            docsEn = "Aadhaar Card, Land Record (Khasra-Khatauni), Bank Passbook, Mobile No",
            applyUrl = "https://pmkisan.gov.in"
        ),
        SchemeInfo(
            id = "pm_fby",
            titleHi = "प्रधानमंत्री फसल बीमा योजना (PMFBY)",
            titleEn = "Pradhan Mantri Fasal Bima Yojana",
            benefitHi = "प्राकृतिक आपदा, सूखा, बाढ़ व कीटों से फसल नष्ट होने पर न्यूनतम प्रीमियम पर पूर्ण सुरक्षा।",
            benefitEn = "Comprehensive crop insurance against natural hazards at minimal premium (1.5-2%).",
            eligibilityHi = "अधिसूचित क्षेत्रों में अधिसूचित फसल उगाने वाले सभी ऋणी व गैर-ऋणी किसान।",
            eligibilityEn = "All loanee and non-loanee farmers growing notified crops in notified areas.",
            docsHi = "फसल बुवाई स्व-घोषणा पत्र, भूमि जमाबंदी, बैंक खाता, आधार कार्ड",
            docsEn = "Sowing Certificate, Land Title, Bank Account, Aadhaar Card",
            applyUrl = "https://pmfby.gov.in"
        ),
        SchemeInfo(
            id = "soil_health",
            titleHi = "मृदा स्वास्थ्य कार्ड योजना (Soil Health Card)",
            titleEn = "Soil Health Card Scheme",
            benefitHi = "खेत की मिट्टी का 12 मानकों पर निःशुल्क परीक्षण व संतुलित उर्वरक सिफारिश रिपोर्ट।",
            benefitEn = "Free testing of 12 soil parameters with customized nutrient dosage advice.",
            eligibilityHi = "देश का प्रत्येक किसान अपने नजदीकी कृषि विज्ञान केंद्र (KVK) से प्राप्त कर सकता है।",
            eligibilityEn = "Open to all farmers via nearest Krishi Vigyan Kendra (KVK).",
            docsHi = "खेत का खसरा नंबर, आधार कार्ड, मिट्टी नमूना संग्रह फॉर्म",
            docsEn = "Land Survey/Khasra Number, Aadhaar Card, Soil Sample Form",
            applyUrl = "https://soilhealth.dac.gov.in"
        ),
        SchemeInfo(
            id = "pm_kusum",
            titleHi = "पीएम-कुसुम सोलर पंप सब्सिडी (PM-KUSUM)",
            titleEn = "PM-Kusum Solar Pump Subsidy Scheme",
            benefitHi = "खेत में सौर ऊर्जा पंप लगाने पर 60% तक सरकारी अनुदान (सब्सिडी) व 30% बैंक ऋण।",
            benefitEn = "Up to 60% subsidy for installing off-grid solar agricultural irrigation pumps.",
            eligibilityHi = "कृषि भूमि मालिक, जिनके पास सिंचाई का जल स्रोत (बोरवेल/कुआं) उपलब्ध हो।",
            eligibilityEn = "Agricultural landowners with an available irrigation water source.",
            docsHi = "जमीन की फर्द, बिजली कनेक्शन अनापत्ति, आधार कार्ड, बैंक स्टेटमेंट",
            docsEn = "Land Title, Electricity NOC, Aadhaar Card, Bank Statement",
            applyUrl = "https://pmkusum.mnre.gov.in"
        ),
        SchemeInfo(
            id = "kcc",
            titleHi = "किसान क्रेडिट कार्ड (Kisan Credit Card - KCC)",
            titleEn = "Kisan Credit Card (KCC) Low-Interest Loan",
            benefitHi = "मात्र 4% ब्याज दर पर ₹3 लाख तक का अल्पकालिक कृषि ऋण व बिना गारंटी ₹1.60 लाख।",
            benefitEn = "Short-term agricultural credit up to ₹3 Lakh at an effective 4% interest rate.",
            eligibilityHi = "सभी व्यक्तिगत/संयुक्त किसान, काश्तकार, पट्टेदार व बटाईदार।",
            eligibilityEn = "All farmers, tenants, sharecroppers, and self-help groups.",
            docsHi = "खेती के दस्तावेज, भूमि नक्शा, आधार कार्ड, पासपोर्ट फोटो",
            docsEn = "Land Record Documents, Aadhaar, Passport Photo",
            applyUrl = "https://www.myscheme.gov.in/schemes/kcc"
        )
    )

    val youtubeCreators = listOf(
        YoutubeCreator(
            id = "yt1",
            name = "Indian Farmer (संतोष जाधव)",
            channelName = "@IndianFarmer",
            subscribers = "4.2M+",
            topicHi = "आधुनिक कृषि तकनीक, ड्रिप सिंचाई व जुगाड़ मशीनरी",
            topicEn = "Modern agricultural tools, drip irrigation & farm machinery hacks",
            youtubeUrl = "https://www.youtube.com/@IndianFarmer",
            tutorialTitleHi = "ड्रिप सिंचाई व मल्चिंग पेपर लगाने का सही तरीका",
            tutorialTitleEn = "Step-by-step guide to Drip Irrigation & Mulching Sheet"
        ),
        YoutubeCreator(
            id = "yt2",
            name = "Kisan Farming (अमित तोमर)",
            channelName = "@KisanFarming",
            subscribers = "2.1M+",
            topicHi = "कम लागत में गेहूं, सरसों व सब्जियों की बंपर पैदावार",
            topicEn = "High-yield low-cost techniques for Wheat, Mustard & Vegetables",
            youtubeUrl = "https://www.youtube.com/@KisanFarming",
            tutorialTitleHi = "गेहूं में पहली सिंचाई और कल्ले बढ़ाने का अचूक फॉर्मूला",
            tutorialTitleEn = "Best formula for Wheat first irrigation & maximum tillering"
        ),
        YoutubeCreator(
            id = "yt3",
            name = "Organic Bharat (जैविक भारत)",
            channelName = "@OrganicBharat",
            subscribers = "1.5M+",
            topicHi = "जीवामृत, घनजीवामृत व प्राकृतिक कीटनाशक निर्माण",
            topicEn = "Jeevamrut, Zero-Budget Natural Farming & organic bio-pesticides",
            youtubeUrl = "https://www.youtube.com/@OrganicBharat",
            tutorialTitleHi = "घर पर 5 दिन में शक्तिशाली जीवामृत कैसे बनाएं",
            tutorialTitleEn = "How to prepare potent Jeevamrut at home in 5 days"
        ),
        YoutubeCreator(
            id = "yt4",
            name = "Dhaakad Kisan (धाकड़ किसान)",
            channelName = "@DhaakadKisan",
            subscribers = "980K+",
            topicHi = "मंडी भाव विश्लेषण, सरकारी सब्सिडी व सोलर पंप",
            topicEn = "Mandi price analysis, government subsidies & solar irrigation",
            youtubeUrl = "https://www.youtube.com/@DhaakadKisan",
            tutorialTitleHi = "पीएम कुसुम सोलर पंप फॉर्म भरने की पूरी प्रक्रिया",
            tutorialTitleEn = "Complete application walkthrough for PM Kusum Solar Pump"
        )
    )

    val initialLedger = listOf(
        LedgerEntry(
            id = "bill-101",
            date = "10 Oct 2026",
            cropName = "गेहूं (Wheat - शरबती)",
            quantity = 45.0,
            unit = "क्विंटल (Quintal)",
            ratePerUnit = 2450.0,
            totalAmount = 110250.0,
            buyerName = "राजेन्द्र ट्रेडिंग कंपनी, करनाल",
            isPaid = true
        ),
        LedgerEntry(
            id = "bill-102",
            date = "06 Oct 2026",
            cropName = "टमाटर (Tomato Hybrid)",
            quantity = 28.0,
            unit = "क्विंटल (Quintal)",
            ratePerUnit = 2100.0,
            totalAmount = 58800.0,
            buyerName = "किसान सब्जी मंडी आढ़ती, दिल्ली",
            isPaid = false
        )
    )

    val nearbySeedStores = listOf(
        SeedStoreInfo(
            id = "store_1",
            nameHi = "श्री राम बीज एवं कृषि सेवा केंद्र",
            nameEn = "Shri Ram Seeds & Agri Service Store",
            distanceKm = "1.2 km",
            addressHi = "जीटी रोड, अनाज मंडी गेट के सामने, करनाल",
            addressEn = "GT Road, Opposite Mandi Gate, Karnal",
            phone = "+91 98765 43210",
            rating = "4.8 ★",
            certifiedSeeds = listOf("गेहूं DBW-187", "सरसों गिरिराज", "सब्जी हाइब्रिड बीज", "यूरिया व DAP"),
            mapQuery = "seeds+store+near+me"
        ),
        SeedStoreInfo(
            id = "store_2",
            nameHi = "राष्ट्रीय बीज निगम (NSC) अधिकृत बिक्री केंद्र",
            nameEn = "National Seeds Corporation (NSC) Hub",
            distanceKm = "2.8 km",
            addressHi = "कृषि भवन कॉम्प्लेक्स, रेलवे रोड",
            addressEn = "Krishi Bhawan Complex, Railway Road",
            phone = "+91 98123 45678",
            rating = "4.9 ★ (सरकारी)",
            certifiedSeeds = listOf("प्रमाणित गेहूं बीज", "चना व मटर बीज", "बायो-फर्टिलाइजर", "बीज शोधन दवा"),
            mapQuery = "krishi+kendra+seeds+fertilizer"
        ),
        SeedStoreInfo(
            id = "store_3",
            nameHi = "किसान सुविधा खाद-बीज एवं कीटनाशक भंडार",
            nameEn = "Kisan Suvidha Fertilizer & Seed Store",
            distanceKm = "3.5 km",
            addressHi = "न्यू अनाज मंडी, ब्लॉक-बी, दुकान नं. 14",
            addressEn = "New Grain Market, Block-B, Shop 14",
            phone = "+91 94160 11223",
            rating = "4.6 ★",
            certifiedSeeds = listOf("धान बासमती 1121", "टमाटर हाइब्रिड", "नीम तेल 10000ppm", "स्प्रेयर"),
            mapQuery = "fertilizer+and+seeds+store"
        ),
        SeedStoreInfo(
            id = "store_4",
            nameHi = "कृषि विज्ञान केंद्र (KVK) बीज विस्तार पटल",
            nameEn = "Krishi Vigyan Kendra (KVK) Seed Center",
            distanceKm = "4.9 km",
            addressHi = "अनुसंधान फार्म, सेक्टर-3",
            addressEn = "Research Farm, Sector 3",
            phone = "+91 94660 99887",
            rating = "4.9 ★ (विश्वविद्यालय)",
            certifiedSeeds = listOf("मूल बीज (Foundation Seeds)", "ट्राइकोडर्मा कल्चर", "मृदा जांच किट"),
            mapQuery = "krishi+vigyan+kendra"
        )
    )

    fun getDiseaseDiagnosis(diseaseId: String): DiseaseDiagnosisResult {
        return when (diseaseId) {
            "early_blight" -> DiseaseDiagnosisResult(
                diseaseNameHi = "टमाटर का अगेती झुलसा (Early Blight - अल्टरनेरिया)",
                diseaseNameEn = "Tomato Early Blight (Alternaria solani)",
                confidencePercent = 97,
                whyHappeningHi = "यह बीमारी 'अल्टरनेरिया' फंगस के कारण होती है। खेत में अधिक नमी, 24°C से 29°C तापमान और पत्तियों पर लंबे समय तक पानी रुकने से यह फंगस तेजी से फैलता है। पुरानी कमजोर पत्तियों पर सबसे पहले हमला होता है।",
                whyHappeningEn = "Caused by Alternaria solani fungus. High relative humidity (above 80%), warm temperatures (24-29°C), and extended leaf wetness accelerate spore germination, attacking older foliage first.",
                howToSolveOrganicHi = "1. रोगग्रस्त निचली पत्तियों को तोड़कर खेत से दूर नष्ट करें।\n2. 5 मिली नीम तेल (10,000 ppm) + 5 ग्राम ट्राइकोडर्मा विरिडी प्रति लीटर पानी में मिलाकर हर 7 दिन पर छिड़कें।\n3. खट्टी लस्सी (छाछ) 500 मिली प्रति 15 लीटर पंप में मिलाकर स्प्रे करें।",
                howToSolveOrganicEn = "1. Prune and destroy infected lower leaves.\n2. Spray Neem Oil (10,000 ppm) @ 5ml/L + Trichoderma viride @ 5g/L every 7 days.\n3. Foliar spray of fermented buttermilk (sour curd) @ 500ml per 15L spray tank.",
                howToSolveChemicalHi = "1. मैनकोजेब 75% WP (इंडोफिल M-45) 2.5 ग्राम प्रति लीटर पानी का छिड़काव करें।\n2. रोग अधिक होने पर कॉपर ऑक्सीक्लोराइड 50% WP (3 ग्राम/लीटर) या टेबुकोनाज़ोल + ट्राइफ्लॉक्सीस्ट्रोबिन (नेटिवो) 0.5 ग्राम/लीटर का स्प्रे करें।",
                howToSolveChemicalEn = "1. Spray Mancozeb 75% WP @ 2.5g/L water.\n2. In severe cases, apply Tebuconazole + Trifloxystrobin (Nativo) @ 0.5g/L or Copper Oxychloride 50% WP @ 3g/L.",
                preventionHi = "ड्रिप सिंचाई का उपयोग करें ताकि पत्तियां गीली न हों। क्यारियों पर प्लास्टिक मल्चिंग बिछाएं और फसल चक्र अपनाएं।",
                preventionEn = "Use drip irrigation to prevent wet canopy. Adopt crop rotation with non-solanaceous crops and install plastic mulch."
            )
            "yellow_rust" -> DiseaseDiagnosisResult(
                diseaseNameHi = "गेहूं का पीला रतुआ (Yellow Stripe Rust - पक्सीनिया)",
                diseaseNameEn = "Wheat Yellow / Stripe Rust (Puccinia striiformis)",
                confidencePercent = 96,
                whyHappeningHi = "यह फफूंद जनित रोग ठंडे और नम मौसम (10°C - 20°C) में हवा द्वारा फैलता है। दिसंबर-जनवरी में कोहरा व ओस ज्यादा रहने पर फफूंद के बीजाणु पत्तियों पर हल्दी जैसी पीली धारियां बना देते हैं।",
                whyHappeningEn = "Airborne fungal pathogen Puccinia striiformis thriving in cool, humid conditions (10-20°C) with persistent winter fog and dew.",
                howToSolveOrganicHi = "1. 5 लीटर खट्टी लस्सी + 50 ग्राम हींग को 100 लीटर पानी में मिलाकर खेत में छिड़काव करें।\n2. राख और गौमूत्र का अर्क बनाकर सुबह ओस सूखने के बाद स्प्रे करें।",
                howToSolveOrganicEn = "1. Spray fermented buttermilk (5L) + Asafoetida (50g) in 100L water.\n2. Wood ash and aged cow urine foliar application.",
                howToSolveChemicalHi = "1. प्रोपिकोनाजोल 25% EC (टिल्ट) 200 मिली प्रति 200 लीटर पानी प्रति एकड़ तुरंत स्प्रे करें।\n2. 15 दिन बाद आवश्यकता पड़ने पर टेबुकोनाज़ोल 25.9% EC (1 मिली/लीटर) दोहराएं।",
                howToSolveChemicalEn = "1. Spray Propiconazole 25% EC (Tilt) @ 200 ml in 200 L water per acre immediately.\n2. Repeat Tebuconazole 25.9% EC @ 1 ml/L after 14 days if symptoms persist.",
                preventionHi = "प्रतिरोधी किस्में (DBW-187, DBW-303, HD-3226) ही बोएं। खेत में जलभराव न होने दें।",
                preventionEn = "Sow stripe-rust resistant certified seeds (DBW-187, DBW-303). Avoid nitrogen overuse."
            )
            "leaf_curl" -> DiseaseDiagnosisResult(
                diseaseNameHi = "पर्ण कुंचन विषाणु रोग (Leaf Curl Virus)",
                diseaseNameEn = "Chilli & Tomato Leaf Curl Virus",
                confidencePercent = 95,
                whyHappeningHi = "यह एक वायरल रोग है जो 'सफेद मक्खी' (Whitefly कीट) द्वारा एक पौधे से दूसरे पौधे में फैलता है। रस चूसने से पत्तियां नाव के आकार में ऊपर की ओर मुड़ जाती हैं और पौधा बौना रह जाता है।",
                whyHappeningEn = "Geminivirus transmitted by vector insect Whitefly (Bemisia tabaci). Sap sucking causes severe curling, chlorosis, and stunted growth.",
                howToSolveOrganicHi = "1. प्रति एकड़ 15-20 पीले चिपचिपे ट्रैप (Yellow Sticky Cards) लगाएं।\n2. नीम का तेल (10,000 ppm) 5 मिली प्रति लीटर पानी + 1 ग्राम सर्फ मिलाकर सफेद मक्खी को नष्ट करें।",
                howToSolveOrganicEn = "1. Install 15-20 Yellow Sticky Traps per acre at canopy level.\n2. Spray Neem Oil (10,000 ppm) @ 5ml/L to suppress whitefly nymphs.",
                howToSolveChemicalHi = "1. डायफेन्थियूरॉन 50% WP (पेगासस) 1.2 ग्राम/लीटर या एसिटामिप्रिड 20% SP 0.5 ग्राम/लीटर का स्प्रे करें।\n2. स्पाइरोमेसिफेन 22.9% SC (ओबेरॉन) 1 मिली/लीटर पानी में घोलकर छिड़कें।",
                howToSolveChemicalEn = "1. Spray Diafenthiuron 50% WP @ 1.2g/L or Acetamiprid 20% SP @ 0.5g/L.\n2. Apply Spiromesifen 22.9% SC @ 1ml/L for whitefly nymph control.",
                preventionHi = "नर्सरी में 40 मेश की नेट का उपयोग करें। खेत के चारों ओर मक्का या ज्वार की 2 लाइनें अवरोधक के रूप में लगाएं।",
                preventionEn = "Use 40-mesh insect-proof netting in seedling nursery. Plant 2 border rows of maize or sorghum as barrier crops."
            )
            "powdery_mildew" -> DiseaseDiagnosisResult(
                diseaseNameHi = "चूर्णी फफूंद रोग (Powdery Mildew)",
                diseaseNameEn = "Powdery Mildew (Erysiphe cruciferarum)",
                confidencePercent = 98,
                whyHappeningHi = "सूखे और गर्म दिन तथा नम ठंडी रातें इस फफूंद के अनुकूल होती हैं। पत्तियों पर सफेद पाउडर जमने से धूप नहीं मिल पाती और पैदावार 40% तक घट जाती है।",
                whyHappeningEn = "Fungal infection thriving in warm dry days with cool humid nights. White powdery mycelium inhibits photosynthesis.",
                howToSolveOrganicHi = "1. घुलनशील सल्फर (80% WDG) 2.5 ग्राम प्रति लीटर पानी का छिड़काव करें।\n2. बेकिंग सोडा 5 ग्राम + 2 मिली तरल साबुन प्रति लीटर पानी मिलाकर स्प्रे करें।",
                howToSolveOrganicEn = "1. Spray Wettable Sulphur (80% WDG) @ 2.5g/L.\n2. Spray Baking Soda (Sodium Bicarbonate) @ 5g/L with liquid soap adjuvant.",
                howToSolveChemicalHi = "1. हेक्साकोनाजोल 5% EC 2 मिली प्रति लीटर या डिनोकैप 1 मिली प्रति लीटर का छिड़काव करें।",
                howToSolveChemicalEn = "1. Spray Hexaconazole 5% EC @ 2ml/L or Dinocap 48% EC @ 1ml/L.",
                preventionHi = "खेत में पौधों के बीच पर्याप्त दूरी रखें ताकि हवा और धूप का प्रवाह बना रहे।",
                preventionEn = "Ensure optimum plant spacing for adequate air circulation and sunlight penetration."
            )
            else -> DiseaseDiagnosisResult(
                diseaseNameHi = "स्वस्थ फसल पत्ती (Healthy Crop Foliage)",
                diseaseNameEn = "Healthy Vigorous Foliage",
                confidencePercent = 99,
                whyHappeningHi = "पत्ती में किसी भी फफूंद, वायरस या कीट का कोई लक्षण नहीं है। प्रकाश संश्लेषण की दर उत्तम है और क्लोरोफिल की मात्रा संतुलित है।",
                whyHappeningEn = "No fungal lesions, viral symptoms, or insect damage detected. Excellent chlorophyll content and normal vegetative vigor.",
                howToSolveOrganicHi = "जीवामृत या वर्मीवाश (1:10) का 15 दिन के अंतराल पर छिड़काव जारी रखें ताकि रोग प्रतिरोधक क्षमता बनी रहे।",
                howToSolveOrganicEn = "Continue foliar sprays of Jeevamrut or Vermiwash every 15 days to maintain disease resistance.",
                howToSolveChemicalHi = "किसी भी रासायनिक कीटनाशक या फफूंदनाशक की कोई आवश्यकता नहीं है।",
                howToSolveChemicalEn = "No chemical intervention needed. Maintain balanced irrigation.",
                preventionHi = "संतुलित पोषण और खेत की नियमित निगरानी रखें।",
                preventionEn = "Maintain balanced soil fertility and periodic scouting."
            )
        }
    }
}
