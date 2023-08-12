import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class window extends Application {
    private int numOfPlayers = 1;
    public static Button pauseButton = new Button("Pause");
    Button restartButton = new Button("Restart");

    public window(Stage primaryStage) {
        start(primaryStage);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tetris");

        // Create title
        Text title = new Text("TETRIS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setStyle("-fx-background-color: #336699; -fx-fill: yellow;");

        // Create buttons
        Button onePlayerButton = new Button("1 Player Mode");
        Button twoPlayersButton = new Button("2 Players Mode");

        // Button event handlers
        onePlayerButton.setOnAction(e -> {
            numOfPlayers = 1;
            showGameWindow(primaryStage);
        });

        twoPlayersButton.setOnAction(e -> {
            numOfPlayers = 2;
            showGameWindow(primaryStage);
        });

        // Apply styles to buttons
        onePlayerButton.setStyle("-fx-background-color: #ff0000;");
        twoPlayersButton.setStyle("-fx-background-color: #00ff00;");
        pauseButton.setStyle("-fx-background-color: #ff0000;");
        restartButton.setStyle("-fx-background-color: #00ff00;");

        // Disable focus traversal for buttons
        pauseButton.setFocusTraversable(false);
        restartButton.setFocusTraversable(false);

        // Layout for buttons
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(onePlayerButton, twoPlayersButton);

        // Layout for the title and buttons
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-image: url('background.jpg');" +
                "-fx-background-size: cover;");
        root.setTop(title);
        root.setAlignment(title, Pos.CENTER);
        root.setCenter(buttonBox);

        Scene scene = new Scene(root, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void showGameWindow(Stage primaryStage) {
        primaryStage.close();

        Stage gameStage = new Stage();
        gameStage.setTitle("Tetris");
        gameStage.setWidth(400 * numOfPlayers);
        gameStage.setHeight(620);
        gameStage.setResizable(false);

        TetrisPanel tetrisPanel = new TetrisPanel(numOfPlayers);

        pauseButton.setOnAction(e -> tetrisPanel.togglePauseResume());
        restartButton.setOnAction(e -> tetrisPanel.restart());

        // Disable focus traversal for buttons
        pauseButton.setFocusTraversable(false);
        restartButton.setFocusTraversable(false);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(pauseButton, restartButton);

        VBox gameBox = new VBox(10);
        gameBox.setAlignment(Pos.CENTER);
        gameBox.setStyle("-fx-background-color: black;");
        gameBox.getChildren().addAll(tetrisPanel, buttonBox);

        StackPane root = new StackPane(gameBox);
        StackPane.setAlignment(buttonBox, Pos.BOTTOM_RIGHT); // Set position of buttonBox to bottom-right corner
        StackPane.setMargin(buttonBox, new javafx.geometry.Insets(0, 10, 70, 0)); // Add margin to buttonBox
        Scene scene = new Scene(root);
        gameStage.setScene(scene);
        gameStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
