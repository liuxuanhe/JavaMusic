package xuanhe.limerence.music;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Rainbow(JavaMusic)
 *
 * @author liuxuanhe
 * @version 1.0
 */

public class Music extends Application {

  private static final String MUSIC_WEB = "https://music.yiroote.com";

  @Override
  public void start(Stage primaryStage) {
    init(primaryStage);
    /*
     * 初始化primaryStage
     * iPhone X dp 375 x 800
     * */
//    Platform.setImplicitExit(false);
    primaryStage.getIcons().add(new Image("https://music.yiroote.com/favicon.jpg"));
    primaryStage.setMaxWidth(420);
    primaryStage.setMaxHeight(620);
    primaryStage.setResizable(false);
//    primaryStage.initStyle(StageStyle.UTILITY);
    primaryStage.setOpacity(0.98);
    primaryStage.show();
  }

  private void init(final Stage primaryStage) {
    final Stage stage = primaryStage;
    Group group = new Group();
    primaryStage.setScene(new Scene(group));
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
    engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
      public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue,
          Worker.State newValue) {
        if (newValue == Worker.State.SUCCEEDED) {
          stage.setTitle(engine.getTitle());
        }
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

}