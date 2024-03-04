package lab3;

import java.util.Random;

public class CustomRandomGenerator {
    public static TspPoint generateRandomIndices(int dimension, Random random) {
        int firstIndex = random.nextInt(dimension);
        int secondIndex = (firstIndex + 1 + random.nextInt(dimension - 1)) % dimension;

        return new TspPoint(firstIndex, secondIndex);
    }
}
