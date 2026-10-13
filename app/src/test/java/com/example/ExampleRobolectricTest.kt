package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AgroRepository
import com.example.data.AppLanguage
import com.example.data.StringsProvider
import com.example.data.WeatherInfo
import com.example.ui.components.createSampleLeafBitmap
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context verifies Kishan Bandhu Pro name`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Kishan Bandhu Pro", appName)
  }

  @Test
  fun `verify repository initial data`() {
    assertTrue(AgroRepository.crops.isNotEmpty())
    assertTrue(AgroRepository.mandiPrices.isNotEmpty())
    assertTrue(AgroRepository.schemes.isNotEmpty())
    assertTrue(AgroRepository.youtubeCreators.isNotEmpty())
    assertTrue(AgroRepository.nearbySeedStores.isNotEmpty())
  }

  @Test
  fun `verify strings provider 22 languages and pro titles`() {
    assertEquals("किसान बंधु प्रो", StringsProvider.appTitle(AppLanguage.HINDI))
    assertEquals("Kishan Bandhu Pro", StringsProvider.appTitle(AppLanguage.ENGLISH))
    assertEquals("ਕਿਸਾਨ ਬੰਧੂ ਪ੍ਰੋ", StringsProvider.appTitle(AppLanguage.PUNJABI))
    assertEquals(23, AppLanguage.values().size)
    assertFalse(StringsProvider.smartIrrigationAlertDesc(AppLanguage.HINDI).isEmpty())
    assertFalse(StringsProvider.smartIrrigationAlertDesc(AppLanguage.ENGLISH).isEmpty())
  }

  @Test
  fun `verify disease diagnosis results with causes and solutions`() {
    val diagnosis = AgroRepository.getDiseaseDiagnosis("early_blight")
    assertNotNull(diagnosis)
    assertTrue(diagnosis.confidencePercent > 90.0)
    assertTrue(diagnosis.whyHappeningHi.isNotBlank())
    assertTrue(diagnosis.howToSolveOrganicHi.isNotBlank())
    assertTrue(diagnosis.howToSolveChemicalHi.isNotBlank())
    assertTrue(diagnosis.preventionHi.isNotBlank())
  }

  @Test
  fun `verify CameraX sample leaf bitmap creation`() {
    val leafBitmap = createSampleLeafBitmap()
    assertNotNull(leafBitmap)
    assertEquals(400, leafBitmap.width)
    assertEquals(400, leafBitmap.height)
  }

  @Test
  fun `verify weather and crop data models`() {
    val weather = WeatherInfo()
    assertTrue(weather.temperature > 0)
    assertTrue(weather.rainChance >= 0)
    val wheat = AgroRepository.crops.first()
    assertEquals("wheat", wheat.id)
    assertTrue(wheat.stages.isNotEmpty())
  }

  @Test
  fun `verify Mandi Rates and MSP tracker simulated list and trends`() {
    val prices = AgroRepository.mandiPrices
    assertTrue(prices.size >= 10)

    val wheat = prices.first { it.id == "m1" }
    assertEquals("अनाज", wheat.category)
    assertTrue(wheat.currentPrice > wheat.mspPrice)
    assertTrue(wheat.isBullish)
    assertTrue(wheat.changePercent > 0.0)
    assertEquals(6, wheat.weeklyTrend.size)

    val soybean = prices.first { it.id == "m5" }
    assertFalse(soybean.isBullish)
    assertTrue(soybean.changePercent < 0.0)

    val openMarketVeg = prices.first { it.id == "m9" }
    assertEquals(0, openMarketVeg.mspPrice)
    assertEquals("सब्जियां", openMarketVeg.category)
    assertTrue(openMarketVeg.arrivalsTons > 0)
  }
}
