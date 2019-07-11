package xuanhe.limerence.music;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Rainbow(JavaMusic)
 *
 * @author liuxuanhe
 * @version 1.0
 */

public class Music extends Application {

    private static final String MUSIC_WEB = "https://music.yiroote.com";

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        //enableTray(primaryStage);
        /*
         * 初始化primaryStage
         * iPhone X dp 375 x 800
         * */
        Platform.setImplicitExit(false);
//    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//      public void handle(WindowEvent event) {
//        event.consume();
//      }
//    });
        primaryStage.getIcons().add(new Image("https://music.yiroote.com/images/priview/st32.png"));
        primaryStage.setMaxWidth(420);
        primaryStage.setMaxHeight(620);
        primaryStage.setResizable(false);
//    primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0.98);
        primaryStage.show();


    }

    private void init(final Stage primaryStage) throws Exception {
        Group group = new Group();
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        SystemTray tray = SystemTray.getSystemTray();
        PopupMenu pm = new PopupMenu();

        BufferedImage image = ImageIO.read(Objects.requireNonNull(getPicByUrl("https://music.yiroote.com/images/priview/st32.png")));

        MenuItem mi = new MenuItem("exit");
        MenuItem openItem = new MenuItem("open");
        mi.addActionListener(e -> {
            //点击右键菜单退出程序
            System.exit(0);

        });


        pm.add(mi);
        pm.add(openItem);

        KeyCombination kc = new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN);
        scene.getAccelerators().put(kc, new Runnable() {
            @Override
            public void run() {
                System.out.println("ctrl+E已和登录按钮绑定");
                Platform.runLater(primaryStage::hide);
            }
        });


        MouseListener sj = new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
                Platform.setImplicitExit(false); //多次使用显示和隐藏设置false
                if (e.getClickCount() == 2) {
                    if (primaryStage.isShowing()) {
                        Platform.runLater(primaryStage::hide);
                    } else {
                        Platform.runLater(primaryStage::show);
                    }
                }
            }
        };

        pm.add(openItem);

        //设置悬停提示信息
        TrayIcon trayIcon = new TrayIcon(image, "music", pm);
        tray.add(trayIcon);
        trayIcon.addMouseListener(sj);


        /*
         * 初始化WebView
         * iPhone X dp 375 x 800
         * */
        WebView webView = new WebView();
        webView.setMaxWidth(420);
        webView.setMaxHeight(600);
        /*
         * WebEngine 加载页面
         * https://music.yiroote.com
         * */
        final WebEngine engine = webView.getEngine();
        engine.load(MUSIC_WEB);
        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                primaryStage.setTitle(engine.getTitle());
            }
        });
        HBox hbox = new HBox();
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hbox, webView);
        VBox.setVgrow(webView, Priority.ALWAYS);
        group.getChildren().add(vBox);


    }


    public static void main(String[] args) {
        launch(args);


    }

    private InputStream getPicByUrl(String urls) {
        URL url;
        try {
            url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            return conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}