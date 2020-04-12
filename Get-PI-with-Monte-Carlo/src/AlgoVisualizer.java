import java.awt.*;

public class AlgoVisualizer {

    private static int DELAY = 30;
    private int N;
    private AlgoFrame frame;
    private MonteCarloPiData data;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N) {
        if (sceneWidth != sceneHeight)
            throw new IllegalArgumentException("This demo must be run in a square window!");

        this.N = N;
        Circle circle = new Circle(sceneWidth / 2, sceneHeight / 2, sceneWidth / 2);
        data = new MonteCarloPiData(circle);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneWidth, sceneHeight);
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run() {
        for (int i = 0; i < N; i++) {
            if (i % 100 == 0) {
                frame.render(data);
                AlgoVisHelper.pause(DELAY);
                System.out.println(data.estimatePi());
            }

            int x = (int) (Math.random() * frame.getCanvasWidth());
            int y = (int) (Math.random() * frame.getCanvasHeight());
            data.addPoint(new Point(x, y));
        }
    }

    public static void main(String[] args) {
        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 10000;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}
