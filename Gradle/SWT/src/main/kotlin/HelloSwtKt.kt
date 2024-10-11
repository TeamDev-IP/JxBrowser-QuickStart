
import com.teamdev.jxbrowser.dsl.Engine
import com.teamdev.jxbrowser.dsl.JxBrowserLicense
import com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED
import com.teamdev.jxbrowser.view.swt.BrowserView
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

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

    // Load the required web page.
    browser.navigation().loadUrl("https://html5test.teamdev.com")

    val display = Display()
    val shell = Shell(display)
    shell.text = "JxBrowser SWT"
    shell.layout = FillLayout()

    // Create and embed SWT BrowserView widget to display web content.
    val view = BrowserView.newInstance(shell, browser)
    view.setSize(1280, 800)

    shell.pack()
    shell.open()

    while (!shell.isDisposed) {
        if (!display.readAndDispatch()) {
            display.sleep()
        }
    }
    // Shutdown Chromium and release allocated resources.
    engine.close()

    display.dispose()
}
