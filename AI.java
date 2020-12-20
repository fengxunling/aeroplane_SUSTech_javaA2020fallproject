package sample;

import java.util.ArrayList;
import java.util.List;

public class AI implements Score{
    final private int colorIdx;
    private int[] situationOfPlanes = new int[17];
    private int[][] distanceBetweenPlanes = new int[5][17];
    private int [] diceNumber = {16,13,11,9,8,9,6,7,4,4,2,5};
    public static List<Integer> can_go = new ArrayList<>();


    public AI(String color)
    {
        int colorIdx1 = switch (color) {
            case "RED" -> 0;
            case "YELLOW" -> 1;
            case "BLUE" -> 2;
            case "GREEN" -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
        this.colorIdx = colorIdx1;
    }

    private int evaluateFunction()
    {
        int choice = 0;
        double[] evaluation = new double[17];

        return choice;
    }

    public void play() throws Exception {
        int cnt = 1;
        for(Plane plane : ChessBoardExecute.Planes)
        {
            String planeColor = plane.getColor();
            int planeColorIdx = switch (planeColor) {
                case "RED" -> 0;
                case "YELLOW" -> 1;
                case "BLUE" -> 2;
                case "GREEN" -> 3;
                default -> throw new IllegalStateException("Unexpected value: " + planeColor);
            };
            situationOfPlanes[cnt++] = ChessBoardExecute.new_path[planeColorIdx][plane.on_node_idx];
        }//获得位置
        for(int i=1; i<=4; i++)
        {
            for(int j=1; j<=17; j++)
            {
                int distance = 0x3f3f3f;
                if(situationOfPlanes[j]<52 && situationOfPlanes[this.colorIdx*4+1]<52)
                {
                    if(situationOfPlanes[this.colorIdx*4+1] > situationOfPlanes[j])
                        distance = (52 - situationOfPlanes[this.colorIdx*4+1])+situationOfPlanes[j];
                    else
                        distance = situationOfPlanes[j] - situationOfPlanes[this.colorIdx*4+1];
                }
                distanceBetweenPlanes[i][j] =distance;
            }
        }
        //计算距离
        can_go = AboutRoll.TakeRandom();
    }

    private int softMax(double[] evaluations)
    {
        double MAX = -1;
        for(int i=1; i <= 16; i++)
            if(evaluations[i] > MAX)
                MAX = evaluations[i];

        double SUM = 0;
        for(int i=1; i <= 16; i++)
            SUM += Math.exp(evaluations[i]-MAX);

        for(int i=1; i <= 16; i++)
            evaluations[i] = Math.exp(evaluations[i]-MAX)/SUM;

        MAX = -1;
        int solution = 0;
        for(int i=1; i <= 16; i++)
            if(evaluations[i] > MAX)
            {
                MAX = evaluations[i];
                solution = i;
            }

        return solution;
    }

}
