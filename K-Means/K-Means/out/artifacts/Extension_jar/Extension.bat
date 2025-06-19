@ECHO OFF
java --module-path %~dp0\javafx-sdk-21.0.1\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base,javafx.media,javafx.swing,javafx.web -jar Extension.jar %*
PAUSE