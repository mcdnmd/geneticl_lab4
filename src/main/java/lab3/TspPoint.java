package lab3;

public class TspPoint {
    public int idx;
    public int x;
    public int y;

    public TspPoint(int x, int y){
        this.x = x;
        this.y = y;

    }

    public TspPoint(String input){
        String[] parts = input.split("\\s+");
        this.idx = Integer.parseInt(parts[0]);
        this.x = Integer.parseInt(parts[1]);
        this.y = Integer.parseInt(parts[2]);
    }

    public void printPoint() {
        System.out.println("Index: " + idx + ", X: " + x + ", Y: " + y);
    }
}
