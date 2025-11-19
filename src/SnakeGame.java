import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList; // Para manejar la lista de segmentos de la serpiente
import java.util.Random; // Para generar posiciones aleatorias de la comida
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

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
    ArrayList<Tile> snakeBody = new ArrayList<>(); // Lista para los segmentos del cuerpo de la serpiente

// ------ Comida
    Tile food;
    Random random;

// ------ Logica del juego
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;
    
    public SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>(); // Inicializar la lista del cuerpo de la serpiente

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

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

        // Dibujar el cuerpo de la serpiente
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }        
    }
// ------- Funcion para colocar la comida en una posicion aleatoria
    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize); // Numero aleatorio entre 0 y el numero de losetas en el ancho (24)
        food.y = random.nextInt(boardHeight / tileSize); // Numero aleatorio entre 0 y el numero de losetas en el alto (24)
    }

// ------ Funcion para detectar colisiones entre dos losetas
    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y)); // Agregar un nuevo segmento al cuerpo de la serpiente
            placeFood(); // Colocar nueva comida
        }

        // Mover el cuerpo de la serpiente para seguir a la cabeza
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        // Actualizar la posicion de la cabeza de la serpiente
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Verificar colision con el cuerpo de la serpiente
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
        // Verificar colision con los bordes del tablero
        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth ||
            snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // Mover la serpiente
        repaint(); // Llama a paintComponent para redibujar el juego
        if (gameOver) {
            gameLoop.stop();
        }

    }

// ------ Metodo para manejar las teclas presionadas
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) { // Evitar que la serpiente se mueva en la direccion opuesta
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    // No se usan
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
