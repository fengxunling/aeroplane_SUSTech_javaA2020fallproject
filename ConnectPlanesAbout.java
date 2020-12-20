package sample;

import java.util.List;

public class ConnectPlanesAbout {
    public static void connect(List<Integer> plane_idx){ // 在这个列表里的飞机都要两两连接
        for(int i = 0; i < plane_idx.size(); i++){
            int now_plane = plane_idx.get(i);
            for(int j = 0; i < plane_idx.size(); j++) {
                if(i == j) continue;
                int to_connect_plane = plane_idx.get(j);
                ChessBoardExecute.Planes[now_plane].connected[to_connect_plane] = true;
            }
        }
    }

    public static void removeAPlane(int idx){
        for(int i = 1; i <= 16; i++){ // 断开其他和它有联系的飞机
            if(ChessBoardExecute.Planes[i].connected[idx] == true){
                ChessBoardExecute.Planes[i].connected[idx] = false;
            }
        }
        for(int i = 1; i <= 16; i++){ // 断开自己有联系的飞机
            if(ChessBoardExecute.Planes[idx].connected[i] == true){
                ChessBoardExecute.Planes[idx].connected[i] = false;
            }
        }
    }

}
