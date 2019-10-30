import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Case {

    private String name;
    private List<Double> coords;


    public Case(String name, List<Double> coords) {
        this.name = name;
        this.coords = coords;
    }

    public Case(List<Double> coords) {
        this.coords = coords;
    }

    public static void main(String[] args) {
        List<Case> cases = readCases("train.txt");
        System.out.println(cases);
    }

    public static List<Case> readCases(String file) {
        List<Case> cases = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split(",");
                List<Double> list = new ArrayList<>();
                for (int i = 0; i < strings.length - 1; i++) {
                    list.add(Double.parseDouble(strings[i]));
                }
                cases.add(new Case(strings[strings.length - 1], list));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cases;
    }

    public double calcDistance(List<Double> list) {
        if (list.size() != coords.size()) {
            System.err.println("The number of coordinates in the input data is wrong");
        }
        double distance = 0;
        for (int i = 0; i < coords.size(); i++) {
            distance += Math.pow((coords.get(i) - list.get(i)), 2);
        }
        return distance = Math.sqrt(distance);
    }


    public String getName() {
        return name;
    }

    public List<Double> getCoords() {
        return coords;
    }

    @Override
    public String toString() {
        return String.format("%s %s%n", name, coords);
    }
}
