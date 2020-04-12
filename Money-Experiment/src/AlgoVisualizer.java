import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class AlgoVisualizer {

    private static int DELAY = 10;
    private int[] money;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight) {
        money = new int[100];

        for (int i = 0; i < money.length; i++) {
            money[i] = 100;
        }

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneWidth, sceneHeight);
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run() {
        while (true) {
            Arrays.sort(money);
            frame.render(money);
            AlgoVisHelper.pause(DELAY);

            for (int k = 0; k < 50; k++) {
                for (int i = 0; i < money.length; i++) {
                    int j = (int) (Math.random() * money.length);
                    money[i] -= 1;
                    money[j] += 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        int sceneWidth = 1000;
        int sceneHeight = 800;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight);
    }
}
