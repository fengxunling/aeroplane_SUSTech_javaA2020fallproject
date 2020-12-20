package sample;//package sample.ChessBoardExecute;
//import javafx.*;
import com.sun.javafx.image.BytePixelSetter;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

import java.io.*;
import java.util.*;
import java.util.List;

public class ChessBoardExecute
{
    public static int numAi; //添加AI的个数
    public static boolean[] isAI = new boolean[4];
    public static AI[] AIs = new AI[4];//新建4个AI分别对应四个颜色

    public static double radius = 17.0;// 格子的半径
    public static int remain_can_go_cnt = 1;
    public static int now_color = 0;//红0 黄1 蓝2 绿3
    public static Plane[] Planes = new Plane[123];//以红黄蓝绿的顺序从一号位置开始
    public static Node[] Nodes = new Node[123];
    static int[] airport_x = new int[17];
    static int[] airport_y = new int[17];
    static int[] can_fly_cnt = new int[4];
    public static List<Integer> can_go = new ArrayList<>();
    public static Text print_title = new Text(950, 100, "状态栏\n");
    public static Text round_status = new Text(920, 130, "现在是 RED round  请投掷骰子\n");
    public static Text print_roll_result = new Text(920, 150, "");
    public static Text select_status = new Text(920, 200, "");
    public static Text mouse_click_status = new Text(920, 400, "");

    public static int[][] new_path = new int[4][123];// 对于每一种颜色的飞机新的飞行路径

    public static Pane pane = new AnchorPane();
    public static Scene scene = new Scene(pane, 1200, 600);

    public static Image roll_button = new Image("./pictures/qwq1.png");
    public static ImageView roll_button_view = new ImageView(roll_button);

    public static Ellipse[] connect_sign = new Ellipse[123];

    public static Image highlight = new Image("./pictures/图层2.png");
    public static ImageView[] high_light = new ImageView[123];


    public static Image background = new Image("./pictures/ChessBoard.png");
    public static Image background2 = new Image("./pictures/白色背景.png");
    public static Image next_button = new Image("./pictures/next_button.png");
    public static ImageView next_button_view = new ImageView(next_button);
    public static ImageView imageView1 = new ImageView(background);
    public static ImageView imageView2 = new ImageView(background2);
    public static ImageView imageView3 = new ImageView(background2);

    public static String[] now_color_tostring = new String[4];

    public static TextField cheat_cnt = new TextField();

    public static Image plane_red = new Image("./pictures/red_version_upd.png");
    public static Image plane_yellow = new Image("./pictures/yellow_version_upd.png");
    public static Image plane_blue = new Image("./pictures/blue_version_upd.png");
    public static Image plane_green = new Image("./pictures/green_version_upd.png");

    public static ImageView[] plane_View = new ImageView[17];
    public static ImageView[] back_image_view = new ImageView[17];

    public static void clear() throws IOException{
        for(int i = 0; i < 4; i++) can_fly_cnt[i] = 0;
        File input_position = new File("./src/sample/load_positions.data");
        PrintWriter output = new PrintWriter(input_position);
        System.out.println(input_position.exists());
        for(int i = 1; i <= 16; i++){
            output.print(airport_x[i] + " " + airport_y[i] + "\n");
//            System.out.printf("%d %d\n", airport_x[i], airport_y[i]);
        }
        output.close();
    }

    // 设置停机场点和每个格子
    public static void pre_solve(){
        now_color_tostring[0] = "RED";
        now_color_tostring[1] = "YELLOW";
        now_color_tostring[2] = "BLUE";
        now_color_tostring[3] = "GREEN";

        // 对于每一个玩家都抽离出来一条新路
        for(int i = 1; i <= 50; i++) new_path[0][i] = i;
        new_path[0][51] = 71;
        new_path[0][52] = 72;
        new_path[0][53] = 73;
        new_path[0][54] = 74;
        new_path[0][55] = 75;
        new_path[0][56] = 76;

        for(int i = 1; i <= 50; i++){
            int cur = 14 + i - 1;
            if(cur > 52) cur = cur - 52;
            new_path[1][i] = cur;
        }
        new_path[1][51] = 53;
        new_path[1][52] = 54;
        new_path[1][53] = 55;
        new_path[1][54] = 56;
        new_path[1][55] = 57;
        new_path[1][56] = 58;

        for(int i = 1; i <= 50; i++){
            int cur = 27 + i - 1;
            if(cur > 52) cur = cur - 52;
            new_path[2][i] = cur;
        }
        new_path[2][51] = 59;
        new_path[2][52] = 60;
        new_path[2][53] = 61;
        new_path[2][54] = 62;
        new_path[2][55] = 63;
        new_path[2][56] = 64;

        for(int i = 1; i <= 50; i++){
            int cur = 40 + i - 1;
            if(cur > 52) cur = cur - 52;
            new_path[3][i] = cur;
        }
        new_path[3][51] = 65;
        new_path[3][52] = 66;
        new_path[3][53] = 67;
        new_path[3][54] = 68;
        new_path[3][55] = 69;
        new_path[3][56] = 70;


        airport_x[1] = 529; airport_y[1] = 83;
        airport_x[2] = 620; airport_y[2] = 83;
        airport_x[3] = 529; airport_y[3] = 165;
        airport_x[4] = 620; airport_y[4] = 165;

        airport_x[5] = 1186; airport_y[5] = 80;
        airport_x[6] = 1268; airport_y[6] = 80;
        airport_x[7] = 1186; airport_y[7] = 171;
        airport_x[8] = 1268; airport_y[8] = 171;

        airport_x[9] = 1180; airport_y[9] = 735;
        airport_x[10] = 1271; airport_y[10] = 735;
        airport_x[11] = 1180; airport_y[11] = 818;
        airport_x[12] = 1271; airport_y[12] = 818;

        airport_x[13] = 534; airport_y[13] = 729;
        airport_x[14] = 616; airport_y[14] = 729;
        airport_x[15] = 534; airport_y[15] = 820;
        airport_x[16] = 616; airport_y[16] = 820;

        for(int i = 1; i <= 16; i++){
            airport_x[i] = airport_x[i] * 1200 / 1800 - 28;
            airport_y[i] = airport_y[i] * 600 / 900 - 20;
        }

        //        以下为每个点，从红色飞机出发点为1进行编号
        Nodes = new Node[100];
        Nodes[1] = new Node("Triangular", "GREEN", 1,1, -1, 5, 544, 292); //红色飞机出发点
        Nodes[2] = new Node("Rectangular", "RED", 2, 1,6, 599, 274);
        Nodes[3] = new Node("Rectangular", "YELLOW", 3, 1, 7, 650,274);
        Nodes[4] = new Node("Triangular",  "BLUE", 4, 1, -1, 8, 705, 292); //蓝色近路的终点
        //
        Nodes[5] = new Node("Triangular", "GREEN",5,2,17,9,745,254); //绿色近路的起点
        Nodes[6] = new Node("Rectangular", "RED", 6, 2,10, 725, 198);
        Nodes[7] = new Node("Rectangular", "YELLOW", 7, 2, 11, 725,150);
        Nodes[8] = new Node("Triangular",  "BLUE", 8, 2, -1, 12, 743, 94);
        //
        Nodes[9] = new Node("Rectangular", "GREEN",9,3,13,800,73);
        Nodes[10] = new Node("Rectangular", "RED", 10, 3,14, 850, 73);
        Nodes[11] = new Node("Node", "YELLOW", 11, 3, 15, true, 901,73); //黄色路起点
        Nodes[12] = new Node("Rectangular",  "BLUE", 12, 3, 16, 951, 73);
        //
        Nodes[13] = new Node("Rectangular", "GREEN",13,4,17,1003,73);
        Nodes[14] = new Node("Triangular",  "RED", 14, 4, -1, 18, 1059, 94); //黄色飞机出发点
        Nodes[15] = new Node("Rectangular", "YELLOW", 15, 4, 19, 1077,151);
        Nodes[16] = new Node("Rectangular", "BLUE", 16, 4, 20, 1077,202);
        //
        Nodes[17] = new Node("Triangular", "GREEN", 17,5, -1, 21, 1058, 255); //绿色近路的终点
        Nodes[18] = new Node("Triangular",  "RED", 18, 5, 30, 22, 1097, 295); //红色近路的起点
        Nodes[19] = new Node("Rectangular", "YELLOW", 19, 5, 23, 1151,277);
        Nodes[20] = new Node("Rectangular", "BLUE", 20, 5, 24, 1201,275);
        //
        Nodes[21] = new Node("Triangular", "GREEN", 21,6, -1, 26, 1256, 294);
        Nodes[22] = new Node("Rectangular", "RED", 22, 6,25, 1276, 351);
        Nodes[23] = new Node("Rectangular", "YELLOW", 23, 6, 27, 1276,402);
        Nodes[24] = new Node("Node", "BLUE", 24, 6, 28, true, 1276,452); //蓝色路起点
        //
        Nodes[25] = new Node("Rectangular", "RED", 25, 7,30, 1276, 502);
        Nodes[26] = new Node("Rectangular", "GREEN",26,7,29,1276,552);
        Nodes[27] = new Node("Triangular", "YELLOW", 27, 7,-1, 31, 1257,609); //蓝色飞机出发点
        Nodes[28] = new Node("Rectangular", "BLUE", 28, 7, 32, 1201,627);
        //
        Nodes[29] = new Node("Rectangular", "GREEN",29,8,33,1150,627);
        Nodes[30] = new Node("Triangular",  "RED", 30, 8, -1, 34, 1095, 607); //红色近路的起点
        Nodes[31] = new Node("Triangular", "YELLOW", 31, 8, 43,23, 1056,647);
        Nodes[32] = new Node("Rectangular", "BLUE", 32, 8, 36, 1076,702);
        //
        Nodes[33] = new Node("Rectangular", "GREEN",33,9,37,1076,751);
        Nodes[34] = new Node("Triangular",  "RED", 34, 9, -1, 38, 1057, 806); //黄色飞机出发点
        Nodes[35] = new Node("Rectangular", "YELLOW", 35, 9, 39, 1000,827);
        Nodes[36] = new Node("Rectangular", "BLUE", 36, 9, 40, 950,827);
        //
        Nodes[37] = new Node("Node", "GREEN", 37, 10, 41, true, 900,827); //绿色路起点
        Nodes[38] = new Node("Rectangular", "RED", 38, 10,42, 850, 827);
        Nodes[39] = new Node("Rectangular", "YELLOW", 39, 10, 43, 800,827);
        Nodes[40] = new Node("Triangular",  "BLUE", 40, 10, -1, 44, 743, 806); //绿色飞机起点
        //
        Nodes[41] = new Node("Rectangular", "GREEN",41,11,45,724,750);
        Nodes[42] = new Node("Rectangular", "RED", 42, 11,46, 724, 700);
        Nodes[43] = new Node("Triangular", "YELLOW", 43, 11, -1,47, 743,645); //黄色近路的终点
        Nodes[44] = new Node("Triangular",  "BLUE", 44, 11, 4, 48, 705, 605); //蓝色近路的起点
        //
        Nodes[45] = new Node("Rectangular", "GREEN",45,12,49,650,624);
        Nodes[46] = new Node("Rectangular", "RED", 46, 12,50, 600, 624);
        Nodes[47] = new Node("Triangular", "YELLOW", 47, 12,-1, 51, 546,607);
        Nodes[48] = new Node("Rectangular",  "BLUE", 48, 12, 52, 525, 549);
        //
        Nodes[49] = new Node("Rectangular", "GREEN",49,13,1,525,499);
        Nodes[50] = new Node("Node", "RED", 50, 13, 2, true, 523,448); //红色路起点
        Nodes[51] = new Node("Rectangular", "YELLOW", 51, 13, 3, 523,399);
        Nodes[52] = new Node("Rectangular",  "BLUE", 52, 13, 4, 523, 349);// 周围棋盘的最后一个点
        //
        //以下为通往终点的路的节点，每个颜色有6个点，每个颜色以该条路上编号为二的点开始，对应编号2-7，7为终点，以黄色路为起始（黄蓝绿红）
        Nodes[53] = new Node("Node", "YELLOW", 2, 901, 153);
        Nodes[54] = new Node("Node", "YELLOW", 3, 901, 203);
        Nodes[55] = new Node("Node", "YELLOW", 4, 901, 253);
        Nodes[56] = new Node("Node", "YELLOW", 5, 901, 303);
        Nodes[57] = new Node("Node", "YELLOW", 6, 901, 353);
        Nodes[58] = new Node("Node", "YELLOW", 7, 901, 403);
        //以上为黄色路
        Nodes[59] = new Node("Node", "BLUE", 2, 1198, 450);
        Nodes[60] = new Node("Node", "BLUE", 3, 1148, 450);
        Nodes[61] = new Node("Node", "BLUE", 4, 1098, 450);
        Nodes[62] = new Node("Node", "BLUE", 5, 1048, 450);
        Nodes[63] = new Node("Node", "BLUE", 6, 998, 450);
        Nodes[64] = new Node("Node", "BLUE", 7, 948, 450);
        //以上为蓝色路
        Nodes[65] = new Node("Node", "GREEN", 2, 900, 747);
        Nodes[66] = new Node("Node", "GREEN", 3, 900, 697);
        Nodes[67] = new Node("Node", "GREEN", 4, 900, 647);
        Nodes[68] = new Node("Node", "GREEN", 5, 900, 597);
        Nodes[69] = new Node("Node", "GREEN", 6, 900, 547);
        Nodes[70] = new Node("Node", "GREEN", 7, 900, 497);
        //以上为绿色路
        Nodes[71] = new Node("Node", "RED", 2, 601, 450);
        Nodes[72] = new Node("Node", "RED", 3, 651, 450);
        Nodes[73] = new Node("Node", "RED", 4, 701, 450);
        Nodes[74] = new Node("Node", "RED", 5, 751, 450);
        Nodes[75] = new Node("Node", "RED", 6, 801, 450);
        Nodes[76] = new Node("Node", "RED", 7, 851, 450);
        //以上为红色路

        for(int i = 1; i <= 76; i++){
            high_light[i] = new ImageView(highlight);
            high_light[i].setFitWidth(30);
            high_light[i].setFitHeight(30);
//            high_light[i].setRadiusX(radius);
//            high_light[i].setRadiusY(radius);
            System.out.println(i);
//            System.out.printf("node[%d].getx=%.2f\n",i,Nodes[i].getX());
//            high_light[i].setCenterX(Nodes[i].getX());
//            high_light[i].setCenterY(Nodes[i].getY());
//            high_light[i].setStroke(Color.YELLOW);
            high_light[i].setOpacity(0.0);
        }



        //以下为飞机初始位置

        Planes = new Plane[17]; //以红黄蓝绿的顺序从一号位置开始
        Planes[1] = new Plane("RED",1,529,83);
        Planes[2] = new Plane("RED",2,620,83);
        Planes[3] = new Plane("RED",3,529,165);
        Planes[4] = new Plane("RED",4,620,165);
//       红色停机坪
        Planes[5] = new Plane("YELLOW",5,1186,80);
        Planes[6] = new Plane("YELLOW",6,1268,80);
        Planes[7] = new Plane("YELLOW",7,1186,171);
        Planes[8] = new Plane("YELLOW",8,1268,171);
//        黄色停机坪
        Planes[9] = new Plane("BLUE",9,1180,735);
        Planes[10] = new Plane("BLUE",10,1271,735);
        Planes[11] = new Plane("BLUE",11,1180,818);
        Planes[12] = new Plane("BLUE",12,1271,818);
//        蓝色停机坪
        Planes[13] = new Plane("GREEN",13,534,729);
        Planes[14] = new Plane("GREEN",14,616,729);
        Planes[15] = new Plane("GREEN",15,534,820);
        Planes[16] = new Plane("GREEN",16,616,820);
//        黄色停机坪
        for(int i=0; i<=3; i++)
            AIs[i] = new AI(now_color_tostring[i]);
        //新建4个AI，分别对应4个颜色
    }

    // 设置背景
    public static void pre_solve1(){
        pane.getChildren().add(imageView1);
        pane.getChildren().add(imageView2);
        pane.getChildren().add(imageView3);

        imageView2.setFitHeight(458.0);
        imageView2.setFitWidth(254.0);
        imageView3.setFitHeight(160.0);
        imageView3.setFitWidth(200.0);
        AnchorPane.setTopAnchor(imageView2, 100.0);
        AnchorPane.setLeftAnchor(imageView2, 914.0);
        AnchorPane.setTopAnchor(imageView3, 155.0);
        AnchorPane.setLeftAnchor(imageView3, 50.0);
        imageView2.setOpacity(0.4);
        imageView3.setOpacity(0.4);
    }

    // 放置停机场飞机、状态栏飞机、飞机的连接标识
    public static void pre_solve2(){
        // 停机场飞机
        for(int i = 1; i <= 4; i++) plane_View[i] = new ImageView(plane_red);
        for(int i = 5; i <= 8; i++) plane_View[i] = new ImageView(plane_yellow);
        for(int i = 9; i <= 12; i++) plane_View[i] = new ImageView(plane_blue);
        for(int i = 13; i <= 16; i++) plane_View[i] = new ImageView(plane_green);

        for(int i = 1; i <= 16; i++) pane.getChildren().add(plane_View[i]);

        for(int i = 1; i <= 16; i++) {
            AnchorPane.setTopAnchor(plane_View[i], ChessBoardExecute.Planes[i].getY() * 1.0);
            AnchorPane.setLeftAnchor(plane_View[i], ChessBoardExecute.Planes[i].getX() * 1.0);
        }

        // 状态栏飞机
        for(int i = 1; i <= 4; i++) back_image_view[i] = new ImageView(plane_red);
        for(int i = 5; i <= 8; i++) back_image_view[i] = new ImageView(plane_yellow);
        for(int i = 9; i <= 12; i++) back_image_view[i] = new ImageView(plane_blue);
        for(int i = 13; i <= 16; i++) back_image_view[i] = new ImageView(plane_green);

        for(int i = 1; i <= 16; i++){
            back_image_view[i].setFitWidth(30.0);
            back_image_view[i].setFitHeight(30.0);
            back_image_view[i].setOpacity(0.4);
        }

        for(int i  = 1; i <= 4; i++){
            pane.getChildren().add(back_image_view[i]);
            AnchorPane.setTopAnchor(back_image_view[i], 160.0);
            AnchorPane.setLeftAnchor(back_image_view[i],  20.0 + i * 45.0);
        }
        for(int i  = 5; i <= 8; i++){
            pane.getChildren().add(back_image_view[i]);
            AnchorPane.setTopAnchor(back_image_view[i], 160.0 + 40.0);
            AnchorPane.setLeftAnchor(back_image_view[i],  20.0 + (i - 4) * 45.0);
        }
        for(int i  = 9; i <= 12; i++){
            pane.getChildren().add(back_image_view[i]);
            AnchorPane.setTopAnchor(back_image_view[i], 160.0 + 40.0 * 2);
            AnchorPane.setLeftAnchor(back_image_view[i],  20.0 + (i - 8) * 45.0);
        }
        for(int i  = 13; i <= 16; i++){
            pane.getChildren().add(back_image_view[i]);
            AnchorPane.setTopAnchor(back_image_view[i], 160.0 + 40.0 * 3);
            AnchorPane.setLeftAnchor(back_image_view[i],  20.0 + (i - 12) * 45.0);
        }
    }

    // 放置其他的按钮什么的
    public static void pre_solve3(){
        pane.getChildren().add(roll_button_view);
        pane.getChildren().add(select_status);
        pane.getChildren().add(next_button_view); // 下一场游戏

        for(int i = 1; i <= 76; i++) {
            pane.getChildren().add(high_light[i]);
            high_light[i].setX(Nodes[i].getX());
            high_light[i].setY(Nodes[i].getY());
            AnchorPane.setLeftAnchor(high_light[i], Nodes[i].getX());
            AnchorPane.setTopAnchor(high_light[i], Nodes[i].getY());
        }

        roll_button_view.setFitHeight(80.0);
        roll_button_view.setFitWidth(250.0);
        AnchorPane.setTopAnchor(roll_button_view, 440.0);
        AnchorPane.setLeftAnchor(roll_button_view, 20.0);
        AnchorPane.setTopAnchor(next_button_view, 480.0);
        AnchorPane.setLeftAnchor(next_button_view, 1080.0);

    }

    // 放置状态栏文字输出
    public static void pre_solve4(){
        pane.getChildren().add(print_title); // "状态栏"
        pane.getChildren().add(print_roll_result); // 骰子扔出来的点数
        pane.getChildren().add(round_status); // 场数状态和现在是那一个玩家
        pane.getChildren().add(mouse_click_status); // 移动的时候显示点击的是哪个格子
        pane.getChildren().add(cheat_cnt); // 手动设置的骰子点数

        AnchorPane.setLeftAnchor(cheat_cnt, 920.0);
        AnchorPane.setTopAnchor(cheat_cnt, 520.0);
    }

    // 加载pre_solve()并添加监听器
    public static void loadGame() throws Exception{
        pre_solve1();
        pre_solve2();
        pre_solve3();
        pre_solve4();
        File input_position = new File("./src/sample/load_positions.data");
//        System.out.println(input_position.exists());
        Scanner input = new Scanner(input_position);

        // 处理保存数据
        for(int i = 1; i <= 16; i++){
            Planes[i].setX(input.nextInt());
            Planes[i].setY(input.nextInt());
        }

        ChessBoard.stage.setTitle("飞行棋");
        ChessBoard.stage.setScene(scene);
        ChessBoard.stage.show();
        if(!isAI[now_color])
        {
            roll_button_view.setOnMouseClicked(e -> {
                try {
                    takeRollAndShow();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                System.out.println("click_to_roll!");

            });

            next_button_view.setOnMouseClicked(e -> {
                goToNextRound();
            });
        }//人类玩家操控
        else
        {
            AIs[now_color].play();
            goToNextRound();
        }//电脑玩家操控的情况
        //下一轮游戏

        imageView1.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.SPACE)){
                System.out.println("new frame!\n");
            }
        });
    }

    public static void saveGame() throws Exception{
        File input_position = new File("./src/sample/load_positions.data");
        PrintWriter output = new PrintWriter(input_position);
        for(int i = 1; i <= 16; i++){
            output.print(Planes[i].getX() + " " + Planes[i].getY() + "\n");
//            System.out.printf("%d %d\n", Hangers[i].getX(), Hangers[i].getY());
            System.out.println("Save Successfully!");
        }
    }

    public static void showWhereCanGo(int idx) {// idx表示飞机编号
        if(!isAI[now_color])
        {
            select_status.setText(select_status.getText()
                    + "\n你现在选择了" + now_color_tostring[now_color] + "方的第" + (idx % 4 == 0 ? 4 : idx % 4) + "个飞机");
        }
        // 起飞
        if (Planes[idx].on_node_idx == 0 && can_fly_cnt[now_color] > 0) {
            int start_position = 0;
            if (now_color == 0) start_position = 1;
            else if (now_color == 1) start_position = 14;
            else if (now_color == 2) start_position = 27;
            else start_position = 40;
            high_light[start_position].setOpacity(1.0);

            int finalStart_position = start_position;
            if(!isAI[now_color])
            {
                high_light[start_position].setOnMouseClicked(e -> {
                    mouse_click_status.setText("您现在点击的是第 " + finalStart_position + " 号格子\n");
                    if(remain_can_go_cnt > 0) {
                        remain_can_go_cnt--;
                        if (idx == 1 || idx == 11 || idx == 24 || idx == 37) {
                            can_fly_cnt[now_color]--;
                        }
                        Planes[idx].on_node_idx = 1;
//                        high_light[finalStart_position].setOpacity(0.0);

                        Move.move(idx, high_light[finalStart_position].getX() - 10,
                                high_light[finalStart_position].getY());

                        can_fly_cnt[now_color]--;
                        mouse_click_status.setText("您现在点击的是第 " + finalStart_position + " 号格子\n");

                    }
                });
            }
        }
        else if(Planes[idx].on_node_idx != 0){
            List<Integer> cur_list = new ArrayList<>();
            for(int i = 1; i < can_go.size(); i++){
                int cur = can_go.get(i) + Planes[idx].on_node_idx;
                if(cur <= 56){
                    high_light[new_path[now_color][cur]].setOpacity(1.0);
                }
                else{ // 需要返回来
                    cur = 56 - (56 - cur);
                }
                high_light[new_path[now_color][cur]].setOpacity(1.0);
                cur_list.add(cur);
            }

            boolean shortmove_flag = false;

            for(int i = 1; i < cur_list.size(); i++){
                int finalI = i;
                int finalCur = cur_list.get(i);

                if(!isAI[now_color])
                {
                    high_light[new_path[now_color][finalCur]].setOnMouseClicked(e -> {
                        mouse_click_status.setText("您现在点击的是第 " + finalCur + " 号格子\n");
                        if(remain_can_go_cnt > 0) {
                            remain_can_go_cnt--;

                            try {
                                Move.move2(idx, new_path[now_color][Planes[idx].on_node_idx], new_path[now_color][finalCur]);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }

                            can_fly_cnt[now_color]--;

                            Planes[idx].on_node_idx = finalCur;
                            mouse_click_status.setText("您现在点击的是第 " + finalCur + " 号格子\n");
                        }
                    });
                }
                else if(isAI[now_color])
                {

                }

                if(remain_can_go_cnt == 0) break;
            }

            if(remain_can_go_cnt == 0){
                for(int i = 0; i < cur_list.size(); i++){
                    high_light[i].setOpacity(0.0);
                }
            }
        }
    }

    public static void takeRollAndShow() throws Exception{
        if(cheat_cnt.getText().equals("")) {
            can_go = AboutRoll.TakeRandom();
        }
        else {
            String[] cur = cheat_cnt.getText().split(" ");
            can_go = AboutRoll.TakeRandomByManual(Integer.parseInt(cur[0]), Integer.parseInt(cur[1]));
        }
        can_fly_cnt[now_color] = 0;
        if(!isAI[now_color])
        {
            print_roll_result.setText("你投出了 " + can_go.get(1) + "点 和 " + can_go.get(2) + "点\n");
        }
        else
        {
            print_roll_result.setText("电脑投出了 " + can_go.get(1) + "点 和 " + can_go.get(2) + "点\n");
        }

        if(can_go.get(1) == 6) can_fly_cnt[now_color]++;
        if(can_go.get(2) == 6) can_fly_cnt[now_color]++;
        if(!isAI[now_color])
        {
            if(can_fly_cnt[now_color] > 0)
            {
                print_roll_result.setText(print_roll_result.getText() +
                        "\n你现在可以选择" + can_fly_cnt[now_color] + "个飞机起飞\n");

                for(int i = now_color * 4 + 1; i <= now_color * 4 + 4; i++)
                {
                    back_image_view[i].setOpacity(1.0);
                    int finalI = i;
                    back_image_view[i].setOnMouseClicked(e -> {// 点击状态栏坐标
                        try {
                            for(int j = 1; j <= 76; j++) high_light[j].setOpacity(0.0);
                            showWhereCanGo(finalI);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                }
            }
        }
        else
        {

        }

    }

    public static void goToNextRound(){
        // 检测是否需要shortcut
        for(int i = now_color * 4 + 1; i <= now_color * 4 + 4; i++){
            if(Planes[i].on_node_idx % 4 == 2){
                int cur_real = new_path[now_color][Planes[i].on_node_idx];
                try {
                    Move.shortMove(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 检测是否需要battle
        for(int i = now_color * 4 + 1; i <= now_color * 4 + 4; i++){
            back_image_view[i].setOpacity(0.4);
        }
        for(int i = 1; i <= 76; i++) high_light[i].setOpacity(0.0);

        now_color++;
        now_color %= 4;

        print_roll_result.setText("");
        select_status.setText("");
        round_status.setText("现在是 " + now_color_tostring[now_color] + " round  请投掷骰子\n" );

        remain_can_go_cnt = 1;
    }

}
