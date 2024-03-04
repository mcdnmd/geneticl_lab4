package lab3;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TspFitnessFunction implements FitnessEvaluator<TspSolution> {

    private double bestDistance;


    public static ArrayList<TspPoint> points = new ArrayList<TspPoint>();

    public TspFitnessFunction(ArrayList<TspPoint> newPoints) {
        points = newPoints;
    }

    public double getFitness(TspSolution solution, List<? extends TspSolution> list) {
        double fitness = 0;
        List<Integer> ids = solution.getPermutation();

        for (int i = 0; i < ids.size(); i++) {
            int id1 = ids.get(i);
            int id2 = ids.get((i + 1) % ids.size());
            TspPoint p1 = points.get(id1);
            TspPoint p2 = points.get(id2);
            fitness += calculateDistance(p1, p2);
        }

        updateBestDistance(fitness);

        return fitness;
    }

    private double calculateDistance(TspPoint p1, TspPoint p2) {
        double deltaX = p2.x - p1.x;
        double deltaY = p2.y - p1.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private void updateBestDistance(double currentFitness) {
        bestDistance = Math.min(bestDistance, currentFitness);
    }

    public boolean isNatural() {
        return false;
    }
}
