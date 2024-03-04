package lab3;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class TspFactory extends AbstractCandidateFactory<TspSolution> {
    public int dims;

    public TspFactory(int dims) {
        this.dims = dims;
    }

    public TspSolution generateRandomCandidate(Random random) {
        return TspSolution.scramble(dims);
    }
}

