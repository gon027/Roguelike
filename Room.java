class Room{
    public int left;
    public int top;
    public int right;
    public int bottom;

    Room(int _left, int _top, int _right, int _bottom){
        left = _left;
        top = _top;
        right = _right;
        bottom = _bottom;
    }

    @Override
    public String toString(){
        return "left = " + left + ", top = " + top + ", right = " + right + ", bottom = " + bottom;
    }
}