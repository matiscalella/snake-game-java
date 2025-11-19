import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600; // Ancho del juego
        int boardHeight = boardWidth; // Alto del juego

        JFrame frame = new JFrame("Snake Game"); // Ventana principal
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la aplicación al cerrar la ventana

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight); // Crear una instancia del juego
        frame.add(snakeGame); // Agregar el panel del juego a la ventana
        frame.pack(); // Ajustar el tamaño de la ventana al contenido
    }
}
