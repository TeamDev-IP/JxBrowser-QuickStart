import com.teamdev.jxbrowser.dsl.Engine
import com.teamdev.jxbrowser.dsl.JxBrowserLicense
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
fun main() {
    // Initialize Chromium.
    val engine = Engine(HARDWARE_ACCELERATED) {
        license = JxBrowserLicense("your license key")
    }

    // Create a Browser instance.
    val browser = engine.newBrowser()

    SwingUtilities.invokeLater {
        JFrame("JxBrowser AWT/Swing").apply {
            // Shutdown Chromium and release allocated resources when the frame closes.
            addWindowListener(object : WindowAdapter() {
                override fun windowClosing(e: WindowEvent) {
                    engine.close()
                }
            })
            // Create and embed Swing BrowserView component to display web content.
            add(BrowserView.newInstance(browser))
            setSize(1280, 800)
            setLocationRelativeTo(null)
            isVisible = true
        }

        // Load the required web page.
        browser.navigation().loadUrl("https://html5test.teamdev.com/")
    }
}
