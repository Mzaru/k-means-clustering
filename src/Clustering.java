import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Clustering {

    private List<Cluster> clusters;

    public Clustering (List<Case> initCases, int n) {
        List<List<Case>> cases = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            cases.add(new ArrayList<>());
        }
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        for (Case c : initCases) {
            cases.get(rand.nextInt(n)).add(c);
        }
        clusters = new ArrayList<>();
        for (List<Case> list : cases) {
            clusters.add(new Cluster(list));
        }
    }

    public void group() {
//        for (Cluster c : clusters) {
//            c.calculateCentroid();
//        }
//        for (Cluster c : clusters) {
//            for (Case obj : c.getObjects()) {
//                List<Double> distances = new ArrayList<>();
//                for (int i = 0; i < clusters.size(); i++) {
//                    distances.add(obj.calcDistance(clusters.get(i).getCentroid()));
//                }
//
//            }
//        }
        for (Cluster c : clusters) {
            c.calculateCentroid();
        }
        List<Case> objects = new ArrayList<>();
        clusters.stream().forEach(cluster -> cluster.getObjects().stream().forEach(aCase -> objects.add(aCase)));
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Case obj : objects) {
                Cluster in = getCluster(obj);
                Cluster to = null;
                double distance = obj.calcDistance(in.getCentroid());
                for (Cluster cluster : clusters) {
                    double currentDis = obj.calcDistance(cluster.getCentroid());
                    if (currentDis < distance) {
                        to = cluster;
                        distance = currentDis;
                    }
                }
                if (to != null) {
                    in.removeObject(obj);
                    to.addObject(obj);
                    changed = true;
                }
            }
            clusters.stream().forEach(Cluster::calculateCentroid);
            System.out.printf("Sum of distances: %f%n", getDistanceSum(objects));
            clusters.stream().forEach(cluster -> System.out.println(cluster.getPurity()));
            System.out.println();
        }

    }

    private double getDistanceSum(List<Case> objects) {
        double sum = 0;
        for (Case obj : objects) {
            sum += obj.calcDistance(getCluster(obj).getCentroid());
        }
        return sum;
    }

    private Cluster getCluster(Case c) {
        for (Cluster cluster : clusters) {
            if (cluster.getObjects().contains(c)) {
                return cluster;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return clusters.toString();
    }

    public static void main(String[] args) {
        List<Case> cases = Case.readCases("train.txt");
        Clustering c = new Clustering(cases, 3);
        c.group();
    }
}
