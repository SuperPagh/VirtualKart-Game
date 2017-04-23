import java.util.ArrayList;
import java.util.List;

public class State {

    private List<Vector2D> pointVectors;
    private List<Vector2D> directionVectors;
    private int currentPos;
    private Vector2D currentDirection;

    public State(List<Vector2D> pointVectors, List<Vector2D> directionVectors) {
        this.pointVectors = pointVectors;
        this.directionVectors = directionVectors;
        this.currentPos = 0;
        this.currentDirection = getDirs().get(0).unitVector();
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
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
                System.out.println("old = (" + x + "," + y + ")");
                System.out.println("new = (" + newX + "," + newY + ")");

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
