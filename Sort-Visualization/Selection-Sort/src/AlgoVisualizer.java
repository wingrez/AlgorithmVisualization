import java.awt.*;

public class AlgoVisualizer {

    private static int DELAY = 100;
    private int N;
    private AlgoFrame frame;
    private SelectionSortData data;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N) {
        data = new SelectionSortData(N, sceneHeight);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneWidth, sceneHeight);
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run() {
        setData(0, -1, -1);
        for(int i=0; i<data.N(); i++){
            int minIndex=i;
            setData(i, -1, minIndex);
            for(int j=i+1; j<data.N(); j++){
                setData(i, j, minIndex);
                if(data.get(j)<data.get(minIndex)){
                    minIndex=j;
                    setData(i, j, minIndex);
                }
            }
            data.swap(i, minIndex);
            setData(i+1, -1, -1);
        }

        setData(data.N(), -1, -1);
    }

    private void setData(int orderedIndex, int currentCompareIndex, int currentMinIndex){
        data.orderedIndex=orderedIndex;
        data.currentMinIndex=currentMinIndex;
        data.currentCompareIndex=currentCompareIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {
        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 50;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}
