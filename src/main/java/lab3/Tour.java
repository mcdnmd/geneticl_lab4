package lab3;

public class Tour {
    public double distance;
    public int iteration;

    public int dims;

    public Tour(double bestDistance) {
        distance = bestDistance;
    }

    public void setDims(int dims){
        this.dims = dims;
    }

    public void updateDistance(double curDistance, int curIteration){
        if (curDistance < distance) {
            distance = curDistance;
            iteration = curIteration;
        }
    }
}
