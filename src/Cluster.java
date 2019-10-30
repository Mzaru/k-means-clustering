import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cluster {

    private List<Case> objects;
    private List<Double> centroid;

    public Cluster(List<Case> objects) {
        this.objects = objects;
    }

    public void addObject(Case c) {
        objects.add(c);
    }

    public boolean removeObject(Object o) {
        return objects.remove(o);
    }

    public void calculateCentroid() {
        double[] centr = new double[objects.get(0).getCoords().size()];
        for (Case object : objects) {
            for (int i = 0; i < object.getCoords().size(); i++) {
                centr[i] += object.getCoords().get(i);
            }
        }
        centroid = new ArrayList<>();
        for (int i = 0; i < centr.length; i++) {
            centr[i] /= objects.size();
        }
        for (double d : centr) centroid.add(d);
    }

    public List<Double> getCentroid() {
        return centroid;
    }

    public String getPurity() {
        List<String> classes = objects.stream().map(c -> c.getName()).distinct().collect(Collectors.toList());
        double[] purities = new double[classes.size()];
        for (int i = 0; i < classes.size(); i++) {
            for (Case obj : objects) {
                if (obj.getName().equals(classes.get(i))) {
                    ++purities[i];
                }
            }
            purities[i] /= objects.size();
        }
        StringBuilder result = new StringBuilder("Cluster: ");
        for (int i = 0; i < classes.size(); i++) {
            result.append(classes.get(i) + " ");
            result.append(purities[i] * 100 + "% ");
        }
        return result.toString();
    }

    public List<Case> getObjects() {
        return objects;
    }

    @Override
    public String toString() {
        return "Cluster " + objects.toString();
    }

//    public static void main(String[] args) {
//        List<Double> coords = null;
//        List<Case> cases = new ArrayList<>();
//        Case case1 = new Case("Iris", coords);
//        Case case2 = new Case("Iris", coords);
//        Case case3 = new Case("Versi", coords);
//        Case case4 = new Case("Versi", coords);
//        Case case5 = new Case("Johnny", coords);
//        Collections.addAll(cases, case1, case2, case3, case4, case5);
//        Cluster c = new Cluster(cases);
//        System.out.println(c.getPurity());
//    }
}
