import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; // Para manejar la lista de segmentos de la serpiente
import java.util.Random; // Para generar posiciones aleatorias de la comida
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener{

    private class Tile {
        int x;
        int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25; // Tamaño de cada segmento de la serpiente y la comida

// ------ Cabeza de la serpiente
    Tile snakeHead; 

// ------ Comida
    Tile food;
    Random random;

// ------ Logica del juego
    Timer gameLoop;
    
    public SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);

        snakeHead = new Tile(5, 5);

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        gameLoop = new Timer(100, this);
        gameLoop.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

// ------- Funcion para dibujar los elementos del juego
    public void draw(Graphics g) { 

        // Grilla
        for (int i = 0; i < boardWidth / tileSize; i++) {
            // Dibujar líneas verticales y horizontales (x1, y1, x2, y2)
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }


        // Dibujar la comida
        g.setColor(Color.RED);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        // Dibujar la cabeza de la serpiente
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);


        
    }
// ------- Funcion para colocar la comida en una posicion aleatoria
    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize); // Numero aleatorio entre 0 y el numero de losetas en el ancho (24)
        food.y = random.nextInt(boardHeight / tileSize); // Numero aleatorio entre 0 y el numero de losetas en el alto (24)
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); // Llama a paintComponent para redibujar el juego
    }

}
