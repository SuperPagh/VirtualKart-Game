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
        super.paintComponents(g);
        int width = getWidth();
        int height = getHeight();
        g.clearRect(0, 0, width, height);

        double dAngle = state.getCurrentDirection().getAngle() - state.getDirs().get(state.getCurrentPos()).getAngle();
        double beta = 1;
        double alpha = 1;


        if(dAngle > 0) {
            beta = 1;
            alpha = 0.5 + (1 - dAngle) / 2;
        } else {
            beta = 1 + dAngle / 2;
            alpha = 1;
        }
        int imageWidth = state.getMapImage().getWidth(this);
        int imageHeight = state.getMapImage().getHeight(this);
        double imageSplits = 200;
        for(int i = 0; i < imageSplits; i++) {
            int minX = (int) (width / imageSplits * i);
            int maxX = (int) (width / imageSplits * (i+1));
            System.out.println((int)(imageWidth/4*(dAngle+(i-imageSplits/2)/imageSplits)+imageWidth/2));
            System.out.println((int)(imageWidth/4*(dAngle+(i-(imageSplits/2-1))/imageSplits)+imageWidth/2));

            g.drawImage(
                    state.getMapImage(),
                    minX,
                    0,
                    maxX,
                    height,
                    (int)(imageWidth/4*(dAngle+(i-imageSplits/2)/imageSplits)+imageWidth/2),
                    (int) ((alpha*i+beta*(imageSplits-i))/imageSplits*(imageHeight/2)-imageHeight/4),
                    (int)(imageWidth/4*(dAngle+(i-(imageSplits/2-1))/imageSplits)+imageWidth/2),
                    (int) ((beta*i+alpha*(imageSplits-i))/imageSplits*(imageHeight/2)),
                    this);
        }



        for(int j = -1; j <= 1; j+=2) {
            if (j == 1) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLUE);
            }
            List<Vector2D> lines = state.pointsToDraw(j*2.76);
            for(int i = 1; i < lines.size(); i++) {
                int x1 = (int) ((-lines.get(i-1).getX()+1) * (width/2));
                int x2 = (int) ((-lines.get(i).getX()+1) * (width/2));
                int y1 = (int) ((1-lines.get(i-1).getY()) * height);
                int y2 = (int) ((1-lines.get(i).getY()) * height);
                g.drawLine(x1, y1, x2, y2);
            }
        }
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
                    try {
                        state.setCurrentPos(state.getCurrentPos()+1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case KeyEvent.VK_LEFT: {
                    double newAngle = state.getCurrentDirection().getAngle() - 0.1;
                    state.setCurrentDirection(Vector2D.getVector2DFromAngleAndMagnitude(newAngle,1));
                    break; }
                case KeyEvent.VK_RIGHT: {
                    double newAngle = state.getCurrentDirection().getAngle() + 0.1;
                    state.setCurrentDirection(Vector2D.getVector2DFromAngleAndMagnitude(newAngle,1));
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

    public static void main(String[] args) throws IOException {

        state = Parser.getListsOfVectors();
        SwingUtilities.invokeLater(() -> {
            Window window = new Window();
            window.createAndDisplayGUI(window);
        });
    }
}