import com.teamdev.jxbrowser.engine.Engine
import com.teamdev.jxbrowser.engine.EngineOptions
import com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED

/**
 * This example demonstrates how to load a web page, wait until it is loaded
 * completely, and print its HTML without displaying any GUI.
 */
class HelloConsoleKt {

    fun main() {
        // Initialize Chromium.
        val options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                                   .licenseKey("your license key")
                                   .build()
        val engine = Engine.newInstance(options)

        // Create a Browser instance.
        val browser = engine.newBrowser()

        // Load a web page and wait until it is loaded completely.
        browser.navigation().loadUrlAndWait("https://html5test.teamdev.com/")

        // Print HTML of the loaded web page.
        browser.mainFrame().ifPresent { frame -> println(frame.html()) }

        // Shutdown Chromium and release allocated resources.
        engine.close()
    }
}
