package dev.syamsu.projects.bench

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import leakcanary.LeakAssertions

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun listItemThenDetailThenBack() {
        // navigate to list item
        // navigate to detail
        LeakAssertions.assertNoLeaks(tag = "leak_list_item")
        // navigate back to list item
        LeakAssertions.assertNoLeaks(tag = "leak_detail_page")
    }
}
