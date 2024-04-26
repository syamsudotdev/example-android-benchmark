package dev.syamsu.projects.benchmark

import android.widget.EditText
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiSelector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "dev.syamsu.projects.bench",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun scrollRv() = benchmarkRule.measureRepeated(
        packageName = "dev.syamsu.projects.bench",
        metrics = listOf(
            FrameTimingMetric(),
            TraceSectionMetric("MainActivity InflateView"),
            TraceSectionMetric("MainActivity AttachAdapter"),
            TraceSectionMetric("RV CreateView"),
            TraceSectionMetric("RV OnBindView"),
        ),
        iterations = 3,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
        }
    ) {
        startActivityAndWait()
        val scrollable = device.findObject(By.scrollable(true))
        device.findObject(By.text("Login"))
        device.findObject(By.text("Next"))
        device.findObject(By.clazz(EditText::class.java))
        // Set gesture margin to avoid triggering gesture navigation
        // with input events from automation.
        scrollable.setGestureMargin(device.displayWidth / 5)
        repeat(5) { scrollable.fling(Direction.DOWN) }
    }
}