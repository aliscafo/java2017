package ru.spbau.erokhina.ftp;

/**
 * Class for constants like style of buttons.
 */
class Constants {
    static final Constants instance = new Constants();

    private final String directoryButtonStyle = "-fx-background-color: transparent; -fx-alignment: center-left; ";
    private final String fileButtonStyle = "-fx-background-color: transparent; -fx-alignment: center-left; -fx-text-fill: CornflowerBlue;";
    private final String highlightedButtonStyle = "-fx-background-color: blue; -fx-alignment: center-left; -fx-text-fill: white;";

    /**
     * Style for button that allows to go to the directory.
     * @return style in CSS
     */
    String getDirectoryButtonStyle() {
        return directoryButtonStyle;
    }

    /**
     * Style for button that allows to save file.
     * @return style in CSS
     */
    String getFileButtonStyle() {
        return fileButtonStyle;
    }

    /**
     * Style for button that is highlighted.
     * @return style in CSS
     */
    String getHighlightedButtonStyle() {
        return highlightedButtonStyle;
    }
}
