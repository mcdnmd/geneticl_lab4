package lab3;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static lab3.CustomRandomGenerator.generateRandomIndices;

public class TspCrossover extends AbstractCrossover<TspSolution> {

    public TspCrossover() {
        super(1);
    }

    @Override
    protected List<TspSolution> mate(TspSolution parent1, TspSolution parent2, int index, Random random) {
        List<TspSolution> children = new ArrayList<>();
        children.add(orderCrossover(parent1, parent2, random));
        children.add(orderCrossover(parent2, parent1, random));
        return children;
    }

    private TspSolution orderCrossover(TspSolution parent1, TspSolution parent2, Random random) {
        List<Integer> tourParent1 = parent1.getPermutation();
        List<Integer> tourParent2 = parent2.getPermutation();
        int dimension = tourParent1.size();
        TspPoint twoRandomIndices = generateRandomIndices(dimension, random);
        List<Integer> child = performOrderCrossover(tourParent1, tourParent2, twoRandomIndices.x, twoRandomIndices.y);
        return new TspSolution(child);
    }

    private static List<Integer> performOrderCrossover(List<Integer> baseParent, List<Integer> traverseParent, int indexA, int indexB) {
        int dimension = baseParent.size();
        List<Integer> child = new ArrayList<>(Collections.nCopies(dimension, 0));
        Set<Integer> used = new HashSet<>();

        for (int i = indexA; i <= indexB; i++) {
            int currentAllele = baseParent.get(i);
            used.add(currentAllele);
            child.set(i, currentAllele);
        }

        int insertIndex = (indexB + 1) % dimension;
        for (int i = 0; i < dimension; i++) {
            int currentIndex = (indexB + 1 + i) % dimension;
            int currentAllele = traverseParent.get(currentIndex);
            if (!used.contains(currentAllele)) {
                child.set(insertIndex, currentAllele);
                insertIndex = (insertIndex + 1) % dimension;
            }
        }

        return child;
    }
}
