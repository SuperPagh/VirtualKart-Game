import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;


public class Window extends JPanel {

    private static Ellipse2D.Double circle;
    private JFrame frame;

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
        super.paintComponents(g);
        Graphics2D brush = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        g.clearRect(0, 0, width, height);
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
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
                    circle.y -= 5;
                    break;
                case KeyEvent.VK_LEFT:
                    circle.x -= 5;
                    break;
                case KeyEvent.VK_RIGHT:
                    circle.x += 5;
                    break;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Window window = new Window();
            window.createAndDisplayGUI(window);
        });
    }
}