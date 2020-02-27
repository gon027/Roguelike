import java.util.*;

class Dungeon{
    private int[][] map;
    private ArrayList<Rect> mapList;

    private final int WIDTH;
    private final int HEIGHT;

    // 部屋の大きさの最大値と最小値
    private final int MINROOMSIZE = 4;
    private final int MAXROOMSIZE = 6;

    private final int MINROOMSIZEX = 4;
    private final int MAXROOMSIZEX = 6;

    private final int MINROOMSIZEY = 4;
    private final int MAXROOMSIZEY = 5;
    private final int PADDING = 2;

    Dungeon() {
        this(54, 30);
    }

    Dungeon(int _width, int _height){
        WIDTH = _width;
        HEIGHT = _height;
        map = new int[HEIGHT][WIDTH];
        mapList = new ArrayList<Rect>();

        createDungeon();
    }

    void createDungeon(){
        init();
        divisionMap(mapList.get(0));
        rectRangeShow();
        createRoom();
        createRoad();
    }

    void init() {
        fillValue(0, 0, WIDTH, HEIGHT, MapChip.MAP_WALL);
        mapList.clear();
        mapList.add(new Rect(0, 0, WIDTH, HEIGHT));
    }

    // 分割できるか判定
    boolean divisionCheck(int _range) {
        // 最小の部屋: 5
        // 外の空白: 2マス
        // 余裕の空白: 1マス
        // (3 + 3) * 2 + 1 = 6 * 2 = 12
        if (_range <= (MINROOMSIZE + 3) * 2 + 5) {
            return true;
        }
        return false;
    }

    void divisionMap(Rect _r){
        boolean hvFrag = RandomUtil.rand.nextBoolean();
        
        if(hvFrag){
            verticalSplit(_r);    // 縦に分割
        }
        else{
            horizontalSplit(_r);  // 横に分割
        }
    }

    // 縦
    void verticalSplit(Rect _r){
        if(_r.getWidth() <= (MINROOMSIZE + 3) * 2 + 5){
            return;
        }

        // 分割点
        int div = _r.getWidth() - (MINROOMSIZE * 2) - (PADDING * 2);
        div = Math.min(div, MAXROOMSIZE);
        int left = (_r.left + 8) + RandomUtil.getRandomRange(0, div);

        Rect child = new Rect(left, _r.top, _r.right, _r.bottom);
        mapList.add(child);
        _r.set(_r.left, _r.top, left + 1, _r.bottom);

        divisionMap(mapList.get(mapList.size() - 1));
        divisionMap(_r);
    }

    // 横
    void horizontalSplit(Rect _r) {
        // (3 + 3) * 2 + 3 = 6 * 2 + 3 = 12 + 3 = 15
        if (_r.getHeight() <= (MINROOMSIZEY + 3) * 2 + 1){
            return;
        }

        int div = _r.getHeight() - (MINROOMSIZE * 2) - 4;
        div = Math.min(div, MAXROOMSIZE);

        System.out.println(div);
        int top = (_r.top + 7) + RandomUtil.getRandomRange(0, div);

        Rect child = new Rect(_r.left, top, _r.right, _r.bottom);
        mapList.add(child);
        _r.set(_r.left, _r.top, _r.right, top + 1);

        divisionMap(mapList.get(mapList.size() - 1));
        divisionMap(_r);
    }

    void createRoom(){
        for(var e : mapList){
            // 部屋の大きさを求める
            // 壁, 空き, 分割戦(1, 1, 1)
            int width = e.getWidth() - 2;
            int height = e.getHeight() - 2;

            // 部屋の大きさを[MIN, w / h]の範囲で決める
            int rx = RandomUtil.getRandomRange(MINROOMSIZE, width - 1);
            int ry = RandomUtil.getRandomRange(MINROOMSIZE, height) - 1;

            // 空きサイズ(区間 - 部屋)
            int fx = (width - rx);
            int fy = (height - ry);

            // 部屋の左上の位置
            int lux = RandomUtil.getRandomRange(1, fx) + 1;
            int luy = RandomUtil.getRandomRange(1, fy) + 1;

            int sx = e.left + lux;
            int gx = sx + rx;
            int sy = e.top + luy;
            int gy = sy + ry;

            e.setRect(new Rect(sx, sy, gx, gy));

            fillValue(sx, sy, gx, gy, MapChip.MAP_NONE);
        }
    }

    void createRoad(){
        for (var r : mapList) {
            // 上下にランダムな道を作る
            int rtx = RandomUtil.getRandomRange(r.room.left, r.room.right);
            if(outOfArrayY(r.top, r.room.top)){
                fillHLine(r.left + 1, r.right - 1, r.top, MapChip.MAP_NONE);
                fillVLine(r.top, r.room.top, rtx, MapChip.MAP_NONE);
            }

            rtx = RandomUtil.getRandomRange(r.room.left, r.room.right);
            if(outOfArrayY(r.room.bottom, r.bottom)){
                fillVLine(r.room.bottom, r.bottom, rtx, MapChip.MAP_NONE);
            }

            int rty = RandomUtil.getRandomRange(r.room.top, r.room.bottom);
            if(outOfArrayX(r.left, r.room.left)){
                fillVLine(r.top + 1, r.bottom - 1, r.left, MapChip.MAP_NONE);
                fillHLine(r.left, r.room.left, rty, MapChip.MAP_NONE);
            }
            

            rty = RandomUtil.getRandomRange(r.room.top, r.room.bottom);
            if(outOfArrayX(r.room.right, r.right)){
                fillHLine(r.room.right, r.right, rty, MapChip.MAP_NONE);
            }
        }
    }

    void fillHLine(int _left, int _right, int _y, int _value){
        if(_left > _right){
            int t = _left;
            _left = _right;
            _right = t;
        }

        fillValue(_left, _y, _right, _y + 1, _value);
    }

    void fillVLine(int _top, int _bottom, int _x, int _value){
        if(_top > _bottom){
            int t = _top;
            _top = _bottom;
            _bottom = t;
        }

        fillValue(_x, _top, _x + 1, _bottom, _value);
    }

    boolean outOfArrayY(int _top, int _bottom){
        if(_top <= 0 || _bottom >= HEIGHT){
            return false;
        }
        return true;
    }

    boolean outOfArrayX(int _left, int _right) {
        if (_left <= 0 || _right >= WIDTH) {
            return false;
        }
        return true;
    }

    void linkPointVertical(int _x1, int _y1, int _x2, int _y2, int _value) {
        int height = Math.abs(_y2 - _y1);

        int hheight = RandomUtil.getRandomRange(2, height - 1);
        int dist = _y1 + hheight;

        fillValue(_x1, _y1, _x1 + 1, dist, _value);
        fillValue(_x2, dist, _x2 + 1, _y2 + 1, _value);

        if (_x1 > _x2) {
            int t = _x1;
            _x1 = _x2;
            _x2 = t;
        }

        fillValue(_x1, dist, _x2 + 1, dist + 1, _value);
    }

    void linkPointHorizontal(int _x1, int _y1, int _x2, int _y2, int _value) {
        int width = Math.abs(_x2 - _x1);

        int hwidth = RandomUtil.getRandomRange(2, width - 1);
        int dist = _x1 + hwidth;

        fillValue(_x1, _y1, dist, _y1 + 1, _value);
        fillValue(dist, _y2, _x2 + 1, _y2 + 1, _value);

        if (_y1 > _y2) {
            int t = _y1;
            _y1 = _y2;
            _y2 = t;
        }

        fillValue(dist, _y1, dist + 1, _y2 + 1, _value);
    }

    public void mapPrint(){
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if(getMapChip(x, y) == 0){
                    System.out.print("  ");
                }
                else if(getMapChip(x, y) == 1){
                    System.out.print("+ ");
                }
                else if(getMapChip(x, y) == 2){
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }

    void setValue(final int _x, final int _y, final int _value) {
        map[_y][_x] = _value;
    }

    void fillValue(final int _sx, final int _sy, final int _gx, final int _gy, final int _value) {
        for (int y = _sy; y < _gy; y++) {
            for (int x = _sx; x < _gx; x++) {
                setValue(x, y, _value);
            }
        }
    }

    int getMapChip(int _x, int _y){
        return map[_y][_x];
    }

    boolean outOfArray(final int _x, final int _y) {
        if (_x < 0 || _x >= WIDTH || _y < 0 || _y >= HEIGHT) {
            return true;
        }
        return false;
    }

    void rectRangeShow(){
        for (var e : mapList) {
            for(int y = e.top; y < e.bottom; y++){
                for(int x = e.left; x < e.right; x++){
                    if(y == e.top || y == e.bottom - 1 || x == e.left || x == e.right - 1){
                        setValue(x, y, MapChip.MAP_DEBUG);
                    }
                }
            }
        }
    }
}