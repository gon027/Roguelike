import java.util.*;

class Rect {
    // class R{
    //     public int left;
    //     public int top;
    //     public int right;
    //     public int bottom;
    // }

    // R roomr;
    // R rectr;

    public int left;
    public int top;
    public int right;
    public int bottom;
    public Rect room;
    public int roomID;      // 部屋ID
    public boolean isConected;
    public ArrayList<Integer> nextRoomID;      // 部屋をつなげるための部屋ID

    Rect(int _left, int _top, int _right, int _bottom) {
        set(_left, _top, _right, _bottom);
    }

    Rect(int _left, int _top, int _right, int _bottom, int _roomID){
        set(_left, _top, _right, _bottom, _roomID);
    }

    void set(int _left, int _top, int _right, int _bottom) {
        left = _left;
        top = _top;
        right = _right;
        bottom = _bottom;
        isConected = false;
        nextRoomID = new ArrayList<>();
    }

    void set(int _left, int _top, int _right, int _bottom, int _roomID){
        set(_left, _top, _right, _bottom);
        roomID = _roomID;
    }

    void setRect(Rect _rect){
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