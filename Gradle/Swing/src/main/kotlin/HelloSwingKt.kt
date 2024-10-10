import com.teamdev.jxbrowser.engine.Engine
import com.teamdev.jxbrowser.engine.EngineOptions
import com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED
import com.teamdev.jxbrowser.view.swing.BrowserView
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.SwingUtilities

/**
 * This example demonstrates how to initialize Chromium, create a browser instance
 * (equivalent of the Chromium tab), embed a Swing BrowserView component into Java Swing
 * frame to display content of the loaded web page, load the required web page.
 */
class HelloSwingKt {
    fun main() {
        // Initialize Chromium.
        val options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
            .licenseKey("your license key")
            .build()
        val engine = Engine.newInstance(options)

        // Create a Browser instance.
        val browser = engine.newBrowser()

        SwingUtilities.invokeLater {
            val frame = JFrame("JxBrowser AWT/Swing")
            frame.addWindowListener(object : WindowAdapter() {
                override fun windowClosing(e: WindowEvent) {
                    // Shutdown Chromium and release allocated resources.
                    engine.close()
                }
            })
            // Create and embed Swing BrowserView component to display web content.
            frame.add(BrowserView.newInstance(browser))
            frame.setSize(1280, 800)
            frame.setLocationRelativeTo(null)
            frame.isVisible = true

            // Load the required web page.
            browser.navigation().loadUrl("https://html5test.teamdev.com/")
        }
    }
}
