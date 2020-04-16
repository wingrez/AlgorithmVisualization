import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AlgoFrame extends JFrame {

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight) {

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        setResizable(false);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public AlgoFrame(String title) {

        this(title, 1024, 768);
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    private GameData data;

    public void render(GameData data) {
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel {

        private ArrayList<Color> colorList;
        private HashMap<Character, Color> colorMap;

        public AlgoCanvas() {
            // 双缓存
            super(true);

            colorList = new ArrayList<Color>();
            colorList.add(AlgoVisHelper.Red);
            colorList.add(AlgoVisHelper.Purple);
            colorList.add(AlgoVisHelper.Blue);
            colorList.add(AlgoVisHelper.Teal);
            colorList.add(AlgoVisHelper.LightGreen);
            colorList.add(AlgoVisHelper.Lime);
            colorList.add(AlgoVisHelper.Amber);
            colorList.add(AlgoVisHelper.DeepOrange);
            colorList.add(AlgoVisHelper.Brown);
            colorList.add(AlgoVisHelper.BlueGrey);

            colorMap = new HashMap<Character, Color>();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            // 绘制
            int w = canvasWidth / data.M();
            int h = canvasHeight / data.N();

            Board showBoard = data.getShowBoard();

            for (int i = 0; i < showBoard.N(); i++)
                for (int j = 0; j < showBoard.M(); j++) {
                    char c = showBoard.getData(i, j);
                    if (c != Board.EMPTY) {

                        if (!colorMap.containsKey(c)) {
                            int sz = colorMap.size();
                            colorMap.put(c, colorList.get(sz));
                        }

                        Color color = colorMap.get(c);
                        AlgoVisHelper.setColor(g2d, color);
                        AlgoVisHelper.fillRectangle(g2d, j * w + 2, i * h + 2, w - 4, h - 4);

                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
                        String text = String.format("( %d , %d )", i, j);
                        AlgoVisHelper.drawText(g2d, text, j * h + h / 2, i * w + w / 2);
                    }
                }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}