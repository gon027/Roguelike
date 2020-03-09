package Roguelike;

import java.util.*;

class TestMap{
    final int WIDTH = 20;
    final int HEIGHT = 20;

    int map[][];
    private ArrayList<DungeonRect> mapList = new ArrayList<DungeonRect>();

    TestMap(){
        map = new int[HEIGHT][WIDTH];
        init();
        mapList.add(new DungeonRect(0, 0, WIDTH, HEIGHT));
        // mapList.add(new Rect(0, 0, 10, 10));
        // mapList.add(new Rect(10, 0, WIDTH, 10));
        createRoom();
        createRoad();
        mapPrint();
    }

    void init(){
        fillValue(0, 0, WIDTH, HEIGHT, 0);
    }

    void createRoom(){
        // fillValue(1, 2, 5, 7, 1);
        // mapList.get(0).setRect(new Rect(1, 2, 5, 7));
        // fillValue(15, 3, 18, 9, 1);
        // mapList.get(1).setRect(new Rect(15, 3, 18, 9));

        fillValue(5, 5, 13, 13, 1);
        mapList.get(0).setRect(new DungeonRect(5, 5, 13, 13));

        for (var e : mapList) {
            for (int y = e.top; y < e.bottom; y++) {
                for (int x = e.left; x < e.right; x++) {
                    if (y == e.top || y == e.bottom - 1 || x == e.left || x == e.right - 1) {
                        setValue(x, y, 2);
                    }
                }
            }
        }
    }

    void createRoad(){
        DungeonRect r = mapList.get(0);
        // Rect r2 = mapList.get(1);

        // int ly = RandomUtil.getRandomRange(r1.room.top, r1.room.bottom);
        // int ry = RandomUtil.getRandomRange(r2.room.top, r2.room.bottom);

        // int py = RandomUtil.getRandomRange(r1.top + 1, r1.bottom - 1);
        // createRoad2(r1.room.right, ly, r2.room.left, ry, 3);
        // createRoad2(r1.room.right, ly, r1.right, py);
        // createRoad2(r2.left, py, r2.room.left, ry);

        // 上下にランダムな道を作る
        int tx = RandomUtil.getRandomRange(r.left, r.right);
        int rtx = RandomUtil.getRandomRange(r.room.left, r.room.right);
        createRoad1(tx, r.top, rtx, r.room.top, 1);

        tx = RandomUtil.getRandomRange(r.left, r.right);
        rtx = RandomUtil.getRandomRange(r.room.left, r.room.right);
        createRoad1(rtx, r.room.bottom, tx, r.bottom - 1, 1);

        int ty = RandomUtil.getRandomRange(r.top, r.bottom);
        int rty = RandomUtil.getRandomRange(r.room.top, r.room.bottom);
        createRoad2(r.left, ty, r.room.left, rty, 1);

        ty = RandomUtil.getRandomRange(r.top, r.bottom);
        rty = RandomUtil.getRandomRange(r.room.top, r.room.bottom);
        createRoad2(r.room.right, rty, r.right - 1, ty, 1);
    }

    // 縦
    void createRoad1(int _x1, int _y1, int _x2, int _y2, int _value){
        setValue(_x1, _y1, _value);
        setValue(_x2, _y2, _value);

        int h = Math.abs(_y2 - _y1);

        int hheight = getRandomRange(2, h - 1);
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

    // 横
    void createRoad2(int _x1, int _y1, int _x2, int _y2, int _value) {
        setValue(_x1, _y1, _value);
        setValue(_x2, _y2, _value);

        int width = Math.abs(_x2 - _x1);

        int hwidth = getRandomRange(2, width - 1);
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

    int getRandomRange(final int _min, final int _max) {
        final int rand = (int) (Math.random() * (_max - _min)) + _min;
        return rand;
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

    void mapPrint() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (map[y][x] == 0) {
                    System.out.print("# ");
                } else if (map[y][x] == 1) {
                    System.out.print(". ");
                } else if (map[y][x] == 2) {
                    System.out.print("/ ");
                }else{
                    System.out.print("P ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        new TestMap();
    }
}