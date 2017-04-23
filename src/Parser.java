import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    public static State getListsOfVectors() throws IOException {
        List<Vector2D> pointVectors = loadMap();
        List<Vector2D> directionVectors = new ArrayList<>();

        directionVectors.add(pointVectors.get(1));
        for(int i = 1; i < pointVectors.size(); i++) {
            Vector2D currPoint = pointVectors.get(i);
            Vector2D prevPoint = pointVectors.get(i-1);
            directionVectors.add(currPoint.sub(prevPoint));
        }

        return new State(pointVectors,directionVectors);
    }

    public static List<Vector2D> loadMap() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources/mappoints"));

        List<Vector2D> result = new ArrayList<>();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            line = line.replace('[', ' ');
            line = line.replace(']', ' ');
            line = line.replace(" ", "");
            String[] arr = line.split(",");
            double x = Double.parseDouble(arr[0]) * 1000;
            double y = Double.parseDouble(arr[1]) * 1000;
            result.add(new Vector2D(x,y));
        }
        return result;
    }

}
