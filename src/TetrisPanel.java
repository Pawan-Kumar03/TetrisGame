import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TetrisPanel extends Pane {
    private final Canvas canvas;
    public static int numOfPlayers = 1;
    private Tetris[] screens;
    private int[][] key;
    private boolean[] isGameStopped;

    TetrisPanel(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        key = new int[numOfPlayers][6];
        screens = new Tetris[numOfPlayers];
        isGameStopped = new boolean[numOfPlayers];
        canvas = new Canvas(400 * numOfPlayers, 600);
        getChildren().add(canvas);
        setFocusTraversable(true);
        setOnKeyPressed(this::keyPressed);
        setOnKeyReleased(this::keyReleased);

        for (int i = 0; i < numOfPlayers; i++) {
            screens[i] = new Tetris(400 * i, 0, this, i);
            isGameStopped[i] = false;
        }
        setFocusTraversable(true);
        requestFocus();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        canvas.setWidth(getWidth());
        canvas.setHeight(getHeight());
        redraw();
    }

    public void togglePauseResume() {
        if (numOfPlayers == 1)
            screens[0].togglePauseResume();
        else {
            screens[0].togglePauseResume();
            screens[1].togglePauseResume();
        }
    }

    public void stopGame() {
        for (int i = 0; i < numOfPlayers; i++) {
            screens[i].isPaused = true; // Pause the game
            screens[i].isGameOver[0] = true; // Set game over
            screens[i].timer.stop(); // Stop the timer
            isGameStopped[i] = true;
        }
    }

    public void startGame() {
        for (int i = 0; i < numOfPlayers; i++) {
            if (isGameStopped[i]) {
                screens[i].isPaused = false; // Resume the game
                screens[i].isGameOver[0] = false; // Reset game over
                screens[i].timer.start(); // Start the timer
                isGameStopped[i] = false;
            }
        }
    }
    protected void sendGarbage(int id, int send) {
        if (numOfPlayers == 1) {
            return;
        }
        int rand = (int) (Math.random() * (numOfPlayers - 1));
        if (rand >= id) {
            rand++;
        }
        screens[rand].addGarbage(send);
    }
    public void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < numOfPlayers; i++) {
            if (screens[i] == null) {
                continue;
            }
            screens[i].displayGrid(gc);
            screens[i].displayPieces(gc);
            screens[i].displayUI(gc);
        }
    }
/*    todo: please modify case values here. I used of my pc keyboard which is not same as General keycode */
    private void keyPressed(KeyEvent e) {
        int keyCode = e.getCode().ordinal();
        for (int i = 0; i < numOfPlayers; i++) {
            if (screens[i].curr == null) {
                continue;
            }
            switch (i) {
                case 1: // First player (arrow keys)
                    switch (keyCode) {
                        case 16: // Left Arrow Key
                            screens[i].movePiece(0, -1); // Move left
                            redraw();
                            break;
                        case 18: // Right Arrow Key
                            screens[i].movePiece(0, 1); // Move right
                            redraw();
                            break;
                        case 19: // Down Arrow Key
                            screens[i].movePiece(1, 0); // Move down
                            redraw();
                            break;
                        case 17:
                            screens[i].rotateRight();
                            redraw();
                            break;
                    }
                    break;
                case 0: // Second player (A, D, W, S keys)
                    switch (keyCode) {
                        case 36: // A key
                            screens[i].movePiece(0, -1); // Move left
                            redraw();
                            break;
                        case 39: // D key
                            screens[i].movePiece(0, 1); // Move right
                            redraw();
                            break;
                        case 58: // W key
                            screens[i].rotateRight(); // Rotate clockwise
                            redraw();
                            break;
                        case 54: // S key
                            screens[i].movePiece(1, 0); // Move down
                            redraw();
                            break;
                        default:
//                            System.out.println(keyCode);
                    }
                    break;
            }
        }
    }

    private void keyReleased(KeyEvent e) {
        for (int i = 0; i < numOfPlayers; i++) {
            for (int j = 0; j < 6; j++) {
                if (e.getCode().ordinal() == key[i][j]) {
                    if (screens[i].curr == null) {
                        break;
                    }
                }
            }
        }
    }

    protected void setGameOver() {
        for (int i = 0; i < numOfPlayers; i++) {
            if (numOfPlayers==1)
            screens[i].isGameOver[0] = true;
            else if (numOfPlayers==2) {
                screens[i].isGameOver[0] = true;
                screens[i].isGameOver[0] = true;
            }
        }
    }
    public void restart() {
        // Reset game state
        for (int i = 0; i < numOfPlayers; i++) {
            screens[i].resetGame();  // Example: Reset game board, scores, and variables
        }
        redraw();  // Redraw the game panel
    }
}
