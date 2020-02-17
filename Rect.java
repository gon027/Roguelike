class Rect {
    public int left;
    public int top;
    public int right;
    public int bottom;
    public boolean[] conectMap;

    public Rect room;

    Rect(int _left, int _top, int _right, int _bottom) {
        set(_left, _top, _right, _bottom);
    }

    void set(int _left, int _top, int _right, int _bottom) {
        left = _left;
        top = _top;
        right = _right;
        bottom = _bottom;
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
                ", height = " + getHeight();
    }
}