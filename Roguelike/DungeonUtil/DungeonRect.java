package Roguelike.DungeonUtil;

import java.util.*;

public class DungeonRect {
    // public RectAngle rect;   // 大部屋
    // public RectAngle room;   // 部屋

    public int left;
    public int top;
    public int right;
    public int bottom;
    public DungeonRect room;
    public int roomID;      // 部屋ID
    public boolean isConected;
    public ArrayList<Integer> nextRoomID;      // 部屋をつなげるための部屋ID

    public DungeonRect(int _left, int _top, int _right, int _bottom) {
        set(_left, _top, _right, _bottom);
    }

    public DungeonRect(int _left, int _top, int _right, int _bottom, int _roomID){
        set(_left, _top, _right, _bottom, _roomID);
    }

    public void set(int _left, int _top, int _right, int _bottom) {
        left = _left;
        top = _top;
        right = _right;
        bottom = _bottom;
        isConected = false;
        nextRoomID = new ArrayList<>();
    }

    public void set(int _left, int _top, int _right, int _bottom, int _roomID){
        set(_left, _top, _right, _bottom);
        roomID = _roomID;
    }

    public void setRect(DungeonRect _rect){
        room = _rect;
    }

    public int getWidth() {
        return (right - left);
    }

    public int getHeight() {
        return (bottom - top);
    }

    @Override
    public String toString() {
        return  "left = " + left + 
                ", top = " + top + 
                ", right = " + right + 
                ", bottom = " + bottom + 
                ", width = " + getWidth() + 
                ", height = " + getHeight() +
                ", roomID = " + roomID;
    }
}