# JxBrowser in JavaFX Maven Project

This example demonstrates how to configure a Maven project with JxBrowser to embed a JavaFX `BrowserView` widget into
a JavaFX desktop application to display web pages.

## Prerequisites

To compile and run this example please make sure you use Java 17 or higher.

## Download the Project

Clone this repository using the following command:

 ```bash
 git clone https://github.com/TeamDev-IP/JxBrowser-QuickStart
 cd JxBrowser-QuickStart/Maven/JavaFX
 ```

## Get License

Download a free 30-day trial key by sending a request via
the [web form](https://www.teamdev.com/jxbrowser#evaluate).

## Run the JavaFX Application

Use the following command:

```bash
mvn clean compile exec:java -Djxbrowser.license.key=<your_license_key>
```

It will build and start a JavaFX desktop application with JavaFX `BrowserView` inside that
displays https://html5test.teamdev.com as shown below:

![JavaFX BrowserView](https://jxbrowser-support.teamdev.com/img/articles/javafx-view.png)
