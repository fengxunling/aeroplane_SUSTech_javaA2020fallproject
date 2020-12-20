package sample;

import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AboutRoll {
    public static List<Integer> TakeRandomByManual(int randSum1, int randSum2) throws Exception{
        List<Integer> cur_ans = new ArrayList<>();
        int cnt = 0;
        if(randSum1 >= randSum2){
            int curcur = randSum1;
            randSum2 = randSum1;
            randSum1 = curcur;
        }

        // 将合法的步数加到cur_ans数组中返回
        if(randSum2 % randSum1 == 0) cnt = 6;
        else cnt = 5;

        cur_ans.add(cnt);
        cur_ans.add(randSum1);
        cur_ans.add(randSum2);
        cur_ans.add(randSum1 + randSum2);
        cur_ans.add(randSum2 - randSum1);
        cur_ans.add(Math.min(12, randSum1 * randSum2));
        if(randSum2 % randSum1 == 0) cur_ans.add(randSum2 / randSum1);

        return cur_ans;
    }

    //处理一次掷骰子的过程，返回的数组表示可以走的步数的选择
    public static List<Integer> TakeRandom() throws Exception{
        Random r = new Random();
        List<Integer> cur_ans = new ArrayList<>();
        int cnt = 0;

        int randSum1 = Math.abs(r.nextInt() % 6) + 1;
        int randSum2 = Math.abs(r.nextInt() % 6) + 1;

        // 令 randSum1 为较小数
        if(randSum1 >= randSum2){
            int curcur = randSum1;
            randSum2 = randSum1;
            randSum1 = curcur;
        }

        // 将合法的步数加到cur_ans数组中返回
        if(randSum2 % randSum1 == 0) cnt = 6;
        else cnt = 5;

        cur_ans.add(cnt);
        cur_ans.add(randSum1);
        cur_ans.add(randSum2);
        cur_ans.add(randSum1 + randSum2);
        cur_ans.add(randSum2 - randSum1);
        cur_ans.add(Math.min(12, randSum1 * randSum2));
        if(randSum2 % randSum1 == 0) cur_ans.add(randSum2 / randSum1);

        return cur_ans;
    }

    public static void battleAndUpdate(int idx, List<Integer> new_plane) throws Exception{
        // idx表示格子编号
        // pre_planes 表示格子上原来有的飞机的编号
        // new_planes 表示现在新飞过来的飞机

        // 该函数表示的是一个飞机群（或者一个飞机）移动到新位置触发的事件
        // 如果新位置没有飞机 就直接将该飞机（群）移动到该位置
        // 如果该新位置有飞机 就进行battle battle完按照结果进行飞机移动（+解散飞机群）操作
        List<Integer> pre_planes = new ArrayList<>();
        pre_planes = ChessBoardExecute.Nodes[idx].getPlanes();

        if(pre_planes.size() == 0){
            return;
        }

        int i = 0;
        int j = 0;
        while(i < new_plane.size() && j < pre_planes.size()){
            String cur_content = "";

            int new_number = new_plane.get(i);
            String new_color = ChessBoardExecute.Planes[new_number].getColor();

            int pre_number = pre_planes.get(j);
            String pre_color = ChessBoardExecute.Planes[pre_number].getColor();

            cur_content += "现在是" + new_color + "方玩家" + new_number + "号飞机 ";
            cur_content += "challenge" + pre_color + "方玩家" + pre_number + "号飞机";

            int cur1 = randomForSingle(); // new_plane
            cur_content += new_color + "方玩家掷出了" + cur1 + "点\n";
            int cur2 = randomForSingle(); // 被挑战的原先在该格子的飞机
            while (cur2 == cur1) {
                cur2 = randomForSingle();
                cur_content += pre_color + "方玩家掷出了" + cur2 + "点\n";
            }

            // 将battle失败的飞机移动回机场 + 移除他所在的飞机群
            if (cur1 > cur2) {
                j++;
                Move.move(pre_number, ChessBoardExecute.airport_x[pre_number], ChessBoardExecute.airport_y[pre_number]);
                ConnectPlanesAbout.removeAPlane(pre_number);
            }
            else{
                i++;
                Move.move(new_number, ChessBoardExecute.airport_x[new_number], ChessBoardExecute.airport_y[new_number]);
                ConnectPlanesAbout.removeAPlane(new_number);
            }

            ChessBoardExecute.print_roll_result.setText(cur_content);
        }

    }


    private static int randomForSingle() {
        Random r = new Random(19260817);
        int cur = r.nextInt() % 6 + 1;
        return cur;
    }


}
