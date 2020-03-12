package Roguelike.DungeonUtil;

import java.util.*;

public class DungeonRect {
    public RectAngle area;   // 大部屋
    public RectAngle room;   // 部屋
    public int roomID;      // 部屋ID
    public boolean isConected;
    public ArrayList<Integer> nextRoomID;      // 部屋をつなげるための部屋ID

    public DungeonRect(){
        area = new RectAngle();
        room = new RectAngle();
        isConected = false;
        nextRoomID = new ArrayList<>();
    }

    public void setRect(int _left, int _top, int _right, int _bottom, int _roomID){
        area.setRectAngle(_left, _top, _right, _bottom);
        roomID = _roomID;
    }

    public void setRoom(int _left, int _top, int _right, int _bottom){
        room.setRectAngle(_left, _top, _right, _bottom);
    }

    @Override
    public String toString() {
        return  "area = " + area +
                " room = " + room;
    }
}