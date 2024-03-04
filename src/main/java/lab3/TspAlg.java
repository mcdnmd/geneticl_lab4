package lab3;

import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.util.*;

public class TspAlg {

    public static int populationSize = 10;
    public static int generations = 1_000;
    public static double scrambleThreshold = 0.5;
    public static int amountIterations = 20;


    public static void main(String[] args) {
        ArrayList<Double> fits = new ArrayList<>();
        ArrayList<Integer> iters = new ArrayList<>();
        int dims = 0;
        for (int i = 0; i < amountIterations; i++) {
            Tour tour = evolve();
            fits.add(tour.distance);
            iters.add(tour.iteration);
            dims = tour.dims;
        }

        System.out.println("Mean distance: " + meanDouble(fits));
        System.out.println("Mean iter: " + meanInt(iters));
        System.out.println("Min distance: " + getMinimum(fits));
        System.out.println("Dimensions: " + dims);

    }

    public static Tour evolve() {
        String problem = "xqf131.tsp"; // name of problem or path to input file
//        String problem = "xqg237.tsp"; // name of problem or path to input file
//        String problem = "bcl380.tsp"; // name of problem or path to input file

        Random random = new Random(); // random

        TspFileReader tspFileReader = new TspFileReader(problem);

        CandidateFactory<TspSolution> factory = new TspFactory(tspFileReader.getDims()-1); // generation of solutions

        // Создание операторов и пайплайна эволюции
        TspMutation mutation = new TspMutation();
        ArrayList<EvolutionaryOperator<TspSolution>> operators = createOperators(mutation);
        EvolutionPipeline<TspSolution> pipeline = new EvolutionPipeline<TspSolution>(operators);

        // Fitness function
        FitnessEvaluator<TspSolution> evaluator = new TspFitnessFunction(tspFileReader.getPoints()); // Fitness function

        // Selection operator
        SelectionStrategy<Object> selection = new RouletteWheelSelection();

        EvolutionEngine<TspSolution> algorithm = new SteadyStateEvolutionEngine<TspSolution>(
                factory, pipeline, evaluator, selection, populationSize, false, random);

        Tour tour = execute(algorithm, mutation, false);
        tour.setDims(tspFileReader.getDims());
        return tour;
    }

    private static ArrayList<EvolutionaryOperator<TspSolution>> createOperators(TspMutation mutation) {
        ArrayList<EvolutionaryOperator<TspSolution>> operators = new ArrayList<EvolutionaryOperator<TspSolution>>();
        operators.add(new TspCrossover()); // Crossover
        operators.add(mutation); // Mutation
        return operators;
    }

    public static Tour execute(EvolutionEngine<TspSolution> algorithm, TspMutation mutation, boolean verbose) {

        Tour tour = new Tour(Double.MAX_VALUE);
        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                int curIteration = populationData.getGenerationNumber();

                mutation.setGenerationNumber(curIteration + 1);

                if (verbose) {
                    System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                    TspSolution best = (TspSolution) populationData.getBestCandidate();
                    System.out.println("\tBest solution = " + best.toString());
                }

                tour.updateDistance(bestFit, curIteration);
            }
        });

        TerminationCondition terminate = new GenerationCount(generations);
        algorithm.evolve(populationSize, 1, terminate);

        return tour;
    }

    public static double meanDouble(List<Double> values) {
        double sum = 0.0;
        for (Double value : values) {
            sum += value;
        }

        return sum / values.size();
    }

    public static double meanInt(List<Integer> values) {
        double sum = 0.0;
        for (Integer value : values) {
            sum += value;
        }

        return sum / values.size();
    }

    public static double getMinimum(List<Double> array) {
        double min = Double.MAX_VALUE;
        for (Double val: array) {
            if (val < min) {
                min = val;
            }
        }
        return min;
    }

}
