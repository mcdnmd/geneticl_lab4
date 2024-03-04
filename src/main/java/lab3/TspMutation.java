package lab3;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static lab3.CustomRandomGenerator.generateRandomIndices;

public class TspMutation implements EvolutionaryOperator<TspSolution> {
    private int generationNumber;

    public void setGenerationNumber(int generationNumber) {
        this.generationNumber = generationNumber;
    }

    public double calculateDecayCoefficient() {
        return (generationNumber * 1.) / TspAlg.generations;
    }

    public List<TspSolution> apply(List<TspSolution> population, Random random) {
        for (TspSolution specimen : population) {
            List<Integer> tour = specimen.getPermutation();
            scrambleMutation(tour, random);
        }
        return population;
    }

    private void scrambleMutation(List<Integer> tour, Random random) {
        TspPoint twoRandomPoints = generateRandomIndices(tour.size(), random);
        int start = Math.min(twoRandomPoints.x, twoRandomPoints.y);
        int end = Math.max(twoRandomPoints.x, twoRandomPoints.y) + 1;
        Collections.shuffle(tour.subList(start, end), random);
    }
}
