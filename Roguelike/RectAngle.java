package Roguelike;

public class RectAngle{
    public int left;
    public int top;
    public int right;
    public int bottom;

    RectAngle(int _left, int _top, int _right, int _bottom){
        setRectAngle(_left, _top, _right, _bottom);
    }

    void setRectAngle(int _left, int _top, int _right, int _bottom){
        left = _left;
        top = _top;
        right = _right;
        bottom = _bottom;
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
                ", height = " + getHeight();
    }
}