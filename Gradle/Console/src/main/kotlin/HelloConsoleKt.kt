
import com.teamdev.jxbrowser.dsl.Engine
import com.teamdev.jxbrowser.dsl.JxBrowserLicense
import com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED

/**
 * This example demonstrates how to load a web page, wait until it is loaded
 * completely, and print its HTML without displaying any GUI.
 */
fun main() {
    // Initialize Chromium.
    val engine = Engine(HARDWARE_ACCELERATED) {
        license = JxBrowserLicense("your license key")
    }

    // Create a Browser instance.
    val browser = engine.newBrowser()

    // Load a web page and wait until it is loaded completely.
    browser.navigation().loadUrlAndWait("https://html5test.teamdev.com/")

    // Print HTML of the loaded web page.
    browser.mainFrame().ifPresent { frame -> println(frame.html()) }

    // Shutdown Chromium and release allocated resources.
    engine.close()
}
