import java.awt.*;
import java.util.LinkedList;

public class AlgoVisualizer {

    private static int DELAY = 15;
    private AlgoFrame frame;
    private static int blockSide = 8;
    private MazeData data;
    private static final int d[][] = {{-1,0},{0,1},{1,0},{0,-1}};

    public AlgoVisualizer(String mazeFile) {
        data = new MazeData(mazeFile);
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneWidth, sceneHeight);
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    public void run(){
        setData(-1, -1, false);

        LinkedList<Position> queue = new LinkedList<Position>();
        Position entrance = new Position(data.getEntranceX(), data.getEntranceY());
        queue.addLast(entrance);
        data.visited[entrance.getX()][entrance.getY()] = true;

        boolean isSolved = false;

        while(queue.size() != 0){
            Position curPos = queue.pop();
            setData(curPos.getX(), curPos.getY(), true);

            if(curPos.getX() == data.getExitX() && curPos.getY() == data.getExitY()){
                isSolved = true;
                findPath(curPos);
                break;
            }

            for(int i = 0 ; i < 4  ; i ++){
                int newX = curPos.getX() + d[i][0];
                int newY = curPos.getY() + d[i][1];

                if(data.inArea(newX, newY)
                        && !data.visited[newX][newY]
                        && data.getMaze(newX, newY) == MazeData.ROAD){
                    queue.addLast(new Position(newX, newY, curPos));
                    data.visited[newX][newY] = true;
                }
            }

        }

        if(!isSolved)
            System.out.println("The maze has no Solution!");

        setData(-1, -1, false);
    }

    private void findPath(Position des){
        Position cur = des;
        while(cur != null){
            data.result[cur.getX()][cur.getY()] = true;
            cur = cur.getPrev();
        }
    }

    // 从(x,y)的位置开始求解迷宫，如果求解成功，返回true；否则返回false
    private boolean go(int x, int y){
        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index in go function!");

        data.visited[x][y] = true;
        setData(x, y, true);

        if(x == data.getExitX() && y == data.getExitY())
            return true;

        for(int i = 0 ; i < 4 ; i ++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if(data.inArea(newX, newY) &&
                    data.getMaze(newX,newY) == MazeData.ROAD &&
                    !data.visited[newX][newY])
                if(go(newX, newY))
                    return true;
        }

        // 回溯
        setData(x, y, false);

        return false;
    }

    private void setData(int x, int y, boolean isPath){
        if(data.inArea(x, y))
            data.path[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {
        String mazeFile = "maze_101_101.txt";

        AlgoVisualizer vis = new AlgoVisualizer(mazeFile);
    }
}
