import com.teamdev.jxbrowser.engine.Engine
import com.teamdev.jxbrowser.engine.EngineOptions
import com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED
import com.teamdev.jxbrowser.view.javafx.BrowserView
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

/**
 * This example demonstrates how to initialize Chromium, create a browser instance
 * (equivalent of the Chromium tab), embed a JavaFX BrowserView component into JavaFX
 * scene to display content of the loaded web page, load the required web page.
 */
class HelloFxKt : Application() {
    override fun start(primaryStage: Stage) {
        // Initialize Chromium.
        val options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
            .licenseKey("your license key")
            .build()
        val engine = Engine.newInstance(options)

        // Create a Browser instance.
        val browser = engine.newBrowser()

        // Load the required web page.
        browser.navigation().loadUrl("https://html5test.teamdev.com")

        // Create and embed JavaFX BrowserView component to display web content.
        val view = BrowserView.newInstance(browser)

        val scene = Scene(BorderPane(view), 1280.0, 800.0)
        primaryStage.title = "JxBrowser JavaFX"
        primaryStage.scene = scene
        primaryStage.show()

        // Shutdown Chromium and release allocated resources.
        primaryStage.setOnCloseRequest { engine.close() }

    }

    fun run() {
        launch()
    }
}

fun main() {
    HelloFxKt().run()
}
