import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Window extends JPanel {

    private static Ellipse2D.Double circle;
    private JFrame frame;
    private static State state;

    public Window() {
        super();
        int width = 400;
        int height = 400;
        circle = new Ellipse2D.Double(0.5 * width, 0.9 * height,
                0.1 * width, 0.05 * height);
    }

    public Dimension getPreferredSize()
    {
        return (new Dimension(frame.getWidth(), frame.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("resources/road.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.paintComponents(g);
        Graphics2D brush = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        g.clearRect(0, 0, width, height);
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for(int j = -1; j <= 1; j+=2) {
            if (j == 1) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLUE);
            }
            List<Vector2D> lines = state.pointsToDraw(j*2.76);
            for(int i = 1; i < lines.size(); i++) {
                int x1 = (int) ((lines.get(i-1).getX()+1) * (width/2));
                int x2 = (int) ((lines.get(i).getX()+1) * (width/2));
                int y1 = (int) ((1-lines.get(i-1).getY()) * height);
                int y2 = (int) ((1-lines.get(i).getY()) * height);
                g.drawLine(x1, y1, x2, y2);
            }
        }
        //g.drawImage(image, 0, 0, this);
        brush.draw(circle);
    }

    public class MoveCircle implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent evt) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    circle.y += 5;
                    break;
                case KeyEvent.VK_UP:
                    state.setCurrentPos(state.getCurrentPos()+1);
                    circle.y -= 5;
                    break;
                case KeyEvent.VK_LEFT: {
                    double newAngle = state.getCurrentDirection().getAngle() - 0.1;
                    state.setCurrentDirection(Vector2D.getVector2DFromAngleAndMagnitude(newAngle,1));
                    circle.x -= 5;
                    break; }
                case KeyEvent.VK_RIGHT: {
                    double newAngle = state.getCurrentDirection().getAngle() + 0.1;
                    state.setCurrentDirection(Vector2D.getVector2DFromAngleAndMagnitude(newAngle,1));
                    circle.x += 5;
                    break; }
            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private void createAndDisplayGUI(Window window)
    {
        frame = new JFrame();
        Container container = frame.getContentPane();
        container.add(window);
        window.addKeyListener(new MoveCircle());
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        window.requestFocusInWindow();
    }

    public static void main(String[] args) throws FileNotFoundException {

        state = Parser.getListsOfVectors();
        SwingUtilities.invokeLater(() -> {
            Window window = new Window();
            window.createAndDisplayGUI(window);
        });
    }
}