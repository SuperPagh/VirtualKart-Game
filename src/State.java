import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class State {

    private List<Vector2D> pointVectors;
    private List<Vector2D> directionVectors;
    private Image mapImage;
    private int currentPos;
    private Vector2D currentDirection;

    public State(List<Vector2D> pointVectors, List<Vector2D> directionVectors) throws IOException {
        setCurrentPos(0);
        setPointVectors(pointVectors);
        setDirectionVectors(directionVectors);
        setCurrentDirection(getDirs().get(getCurrentPos()).unitVector());
    }

    public void setDirectionVectors(List<Vector2D> directionVectors) {
        this.directionVectors = directionVectors;
    }

    public void setPointVectors(List<Vector2D> pointVectors) {
        this.pointVectors = pointVectors;
    }

    private void setMapImage(Image mapImage) {
        this.mapImage = mapImage;
    }

    public Image getMapImage() {
        return mapImage;
    }

    public void setCurrentPos(int currentPos) throws IOException {
        this.currentPos = currentPos;

        setMapImage(ImageIO.read(new File("resources/map/frame"+getCurrentPos()+".jpg")));
    }

    public void incrementCurrentPos() throws IOException {
        setCurrentPos(this.getCurrentPos()+1);
    }

    public void setCurrentDirection(Vector2D currentDirection) {
        this.currentDirection = currentDirection;
    }

    public List<Vector2D> pointsToDraw(double dy) {
        List<Vector2D> result = new ArrayList<>();
        for(int i = getCurrentPos(); i < currentPos+200; i++) {
            Vector2D temp = getPoints().get(i).sub(getPoints().get(getCurrentPos()));
            if (!temp.isZero()) {
                temp = Vector2D.getVector2DFromAngleAndMagnitude(
                        temp.getAngle() - getCurrentDirection().getAngle(),
                        temp.getMagnitude());
                double x = temp.getX();
                double y = temp.getY() + dy;

                double newX = y/x * 0.2;
                double newY = 0.5 / (x+0.2)*x;

                newY = newY * 10 - 4;

                result.add(new Vector2D(newX, newY));
            }
        }
        return result;
    }

    public List<Vector2D> getPoints() {
        return pointVectors;
    }

    public Vector2D getCurrentDirection() {
        return currentDirection;
    }

    public List<Vector2D> getDirs() {
        return directionVectors;
    }

    public int getCurrentPos() {
        return currentPos;
    }
}
