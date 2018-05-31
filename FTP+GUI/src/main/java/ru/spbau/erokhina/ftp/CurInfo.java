package ru.spbau.erokhina.ftp;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores current useful information.
 */
class CurInfo {
    static final CurInfo instance = new CurInfo();

    private String host = "127.0.0.1";
    private int port = 7777;
    private Button highlighted;
    private String prevStyle;
    private List<String> curDirectory;
    private Scene curScene;
    private Client curClient;

    /**
     * Returns host name.
     * @return host name
     */
    String getHost() {
        return host;
    }

    /**
     * Sets host name.
     * @param host given host name
     */
    void setHost(String host) {
        this.host = host;
    }

    /**
     * Returns port name.
     * @return port name
     */
    int getPort() {
        return port;
    }

    /**
     * Sets port name.
     * @param port given port
     */
    void setPort(int port) {
        this.port = port;
    }

    /**
     * Returns current highlighted button.
     * @return current highlighted button
     */
    Button getHighlighted() {
        return highlighted;
    }

    /**
     * Sets highlighted button.
     * @param highlighted given button
     */
    void setHighlighted(Button highlighted) {
        this.highlighted = highlighted;
    }

    /**
     * Gets previous style of highlighted button.
     * @return previous style of highlighted button
     */
    String getPrevStyle() {
        return prevStyle;
    }

    /**
     * Sets the previous style of highlighted button.
     * @param prevStyle given style
     */
    void setPrevStyle(String prevStyle) {
        this.prevStyle = prevStyle;
    }

    /**
     * Gets the array of parts of current path.
     * @return the array of parts of current path
     */
    List<String> getCurDirectory() {
        return curDirectory;
    }

    /**
     * Sets the array of parts of current path.
     * @param curDirectory the array of parts of current path
     */
    void setCurDirectory(List<String> curDirectory) {
        this.curDirectory = curDirectory;
    }

    /**
     * Gets current scene.
     * @return current scene
     */
    Scene getCurScene() {
        return curScene;
    }

    /**
     * Sets current scene.
     * @param curScene given scene
     */
    void setCurScene(Scene curScene) {
        this.curScene = curScene;
    }

    /**
     * Gets current client.
     * @return current client
     */
    Client getCurClient() {
        return curClient;
    }

    /**
     * Sets current client.
     * @param curClient given client
     */
    void setCurClient(Client curClient) {
        this.curClient = curClient;
    }
}