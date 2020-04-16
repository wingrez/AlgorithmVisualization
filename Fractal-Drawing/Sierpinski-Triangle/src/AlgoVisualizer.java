import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AlgoVisualizer {

    private static int DELAY = 40;

    private FractalData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int maxDepth){

        data = new FractalData(maxDepth);
        int sceneWidth = (int)Math.pow(2, maxDepth);
        int sceneHeight = (int)Math.pow(2, maxDepth);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Fractal Visualizer", sceneWidth,sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){

        setData(data.depth);
    }

    private void setData(int depth){
        data.depth = depth;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public void addAlgoKeyListener(){
        frame.addKeyListener(new AlgoKeyListener());
    }

    private class AlgoKeyListener extends KeyAdapter{

        @Override
        public void keyReleased(KeyEvent event){
            if(event.getKeyChar() >= '0' && event.getKeyChar() <= '9'){
                int depth = event.getKeyChar() - '0';
                setData(depth);
            }
        }
    }

    public static void main(String[] args) {

        int maxDepth = 9;
        AlgoVisualizer vis = new AlgoVisualizer(maxDepth);
    }
}