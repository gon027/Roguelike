import java.util.*;

class Dungeon{
    private Random rand = new Random();
    
    private int[][] map;
    private ArrayList<Rect> mapList = new ArrayList<Rect>();
    private ArrayList<Vec2> points = new ArrayList<Vec2>();

    private final int WIDTH;
    private final int HEIGHT;

    // 区間を分ける時の最大値と最小値
    private final int MAXRECT = 13;
    private final int MINRECT = 7;

    // 部屋の大きさの最大値と最小値
    private final int MINROOM = 3;
    private final int MAXROOM = 10;

    private final int PADDING = 3;
    private final int MARGIN = 2;

    private final int WALL = 0;

    private int roomDebugCount = 2;

    Dungeon(int _width, int _height){
        WIDTH = _width;
        HEIGHT = _height;
        map = new int[HEIGHT][WIDTH];

        createDungeon();
    }

    Dungeon(){
        this(54, 30);
    }

    void createDungeon(){
        init();
        divisionMap();
        createRoom();
        rectShow();
        createRoad();
    }

    void init() {
        fillValue(0, 0, WIDTH, HEIGHT, WALL);
        mapList.add(new Rect(0, 0, WIDTH, HEIGHT));
    }

    void divisionMap(){
        boolean hvFrag = rand.nextBoolean();
        // boolean hvFrag = true;
        // boolean hvFrag = false;
        
        if(hvFrag){
            // 縦に分割
            verticalSplit();
        }
        else{
            // 横に分割
            horizontalSplit();
        }
    }

    boolean divisionCheck(int _range){
        if(_range - 4 <= MAXRECT){
            return true;
        }

        return false;
    }

    // 縦
    void verticalSplit(){
        Rect r = mapList.get(mapList.size() - 1);

        int split_w = (r.right - r.left);
        if (split_w - 4 <= MAXRECT){
            return;
        }

        int left = r.left + 2;
        int right = r.right - 2;

        int width = right - left;

        int mid = width / 2;

        left = left + RandomUtil.getRandomRange(MINRECT + 2, mid + 2);

        Rect child = new Rect(left, r.top, r.right, r.bottom); 
        mapList.add(child);

        r.right = left + 1;

        swap();
        divisionMap();
    }

    // 横
    void horizontalSplit() {
        Rect r = mapList.get(mapList.size() - 1);

        int split_h = (r.bottom - r.top);
        if (split_h - 4 < MAXRECT){
            return;
        }

        int top = r.top + 2;
        int bottom = r.bottom - 2;

        int height = bottom - top;

        int mid = height / 2;

        top = top + RandomUtil.getRandomRange(MINRECT + 2, mid + 2);
        Rect child = new Rect(r.left, top, r.right, r.bottom);
        mapList.add(child);
        r.bottom = top + 1;

        swap();
        divisionMap();
    }

    void createRoom(){
        for(var e : mapList){
            // 部屋の大きさを求める
            // 壁, 空き, 分割戦(1, 1, 1)
            int w = e.getWidth() - PADDING;
            int h = e.getHeight() - PADDING;

            // System.out.println("w = " + w + ", h = " + h);

            // 部屋の大きさを[MIN, w / h]の範囲で決める
            int rx = RandomUtil.getRandomRange(MINROOM, w);
            int ry = RandomUtil.getRandomRange(MINROOM, h);

            // 部屋の区間が最大値を超えないようにする
            rx = Math.min(rx, MAXROOM);
            ry = Math.min(ry, MAXROOM);
            // System.out.println("部屋の大きさ rx = " + rx + ", ry = " + ry);

            // 空きサイズ(区間 - 部屋)
            int fx = (w - rx);
            int fy = (h - ry);

            // 部屋の左上の位置
            // 分割戦１ますを除いた２マス分の補正
            int lux = RandomUtil.getRandomRange(0, fx) + 2;
            int luy = RandomUtil.getRandomRange(0, fy) + 2;

            int sx = e.left + lux;
            int gx = sx + rx;
            int sy = e.top + luy;
            int gy = sy + ry;

            e.setRect(new Rect(sx, sy, gx, gy));

            fillValue(sx, sy, gx, gy, roomDebugCount);
        }
    }

    void createRoad(){
        /*for(int i = 0; i < mapList.size(); i++){

            int index_left = i;
            int index_right = (i + 1) % mapList.size();
            Rect r1 = mapList.get(index_left);
            Rect r2 = mapList.get(index_right);

            // 場合分け
            if(r1.top == r2.bottom || r1.bottom == r2.top){
                int x1 = 0, x2 = 0, y = 0, p = 0;
                
                x1 = RandomUtil.getRandomRange(r1.room.left, r1.room.right);
                x2 = RandomUtil.getRandomRange(r2.room.left, r2.room.right);
                // p = RandomUtil.getRandomRange(, _max)
                if(r1.top > r2.top){
                    y = r1.top;
                    fillValue(x1, y + 1, x1 + 1, r1.room.top, roomDebugCount);
                    fillValue(x2, r2.room.bottom, x2 + 1, y, roomDebugCount);
                    // linkPointVertical(x1, y + 1, x1, r1.room.top);
                    // linkPointVertical(x2, r2.room.bottom, x2 + 1, y);
                }else{
                    y = r2.top;
                    fillValue(x1, r1.room.bottom, x1 + 1, y, roomDebugCount);
                    fillValue(x2, y, x2 + 1, r2.room.top, roomDebugCount);
                    // linkPointVertical(x1, r1.room.bottom, x1 + 1, y);
                    // linkPointVertical(x2, y, x2 + 1, r2.room.top);
                }

                fillHLine(x1, x2, y, roomDebugCount);

                

            }else if(r1.left == r2.right || r1.right == r2.left){
                int y1 = 0, y2 = 0, x = 0;
                y1 = RandomUtil.getRandomRange(r1.room.top, r1.room.bottom);
                y2 = RandomUtil.getRandomRange(r2.room.top, r2.room.bottom);

                if(r1.left > r2.left){
                    x = r1.left;
                    fillValue(r2.room.right, y2, x, y2 + 1, roomDebugCount);
                    fillValue(x + 1, y1, r1.room.left, y1 + 1, roomDebugCount);
                }
                else{
                    x = r2.left;
                    fillValue(r1.room.right, y1, x, y1 + 1, roomDebugCount);
                    fillValue(x, y2, r2.room.left, y2 + 1, roomDebugCount);
                }
                fillVLine(y1, y2, x, roomDebugCount);
            }
        }*/

        for (var r : mapList) {
            

            // 上下にランダムな道を作る
            int rtx = RandomUtil.getRandomRange(r.room.left, r.room.right);
            if(outOfArrayY(r.top, r.room.top)){
                fillHLine(r.left + 1, r.right - 1, r.top, roomDebugCount);
                fillVLine(r.top, r.room.top, rtx, roomDebugCount);
                points.add(new Vec2(rtx, r.top));
            }

            rtx = RandomUtil.getRandomRange(r.room.left, r.room.right);
            if(outOfArrayY(r.room.bottom, r.bottom)){
                // fillHLine(r.left + 1, r.right - 1, r.bottom, roomDebugCount);
                fillVLine(r.room.bottom, r.bottom, rtx, roomDebugCount);
                points.add(new Vec2(rtx, r.bottom));
            }

            int rty = RandomUtil.getRandomRange(r.room.top, r.room.bottom);
            if(outOfArrayX(r.left, r.room.left)){
                fillVLine(r.top + 1, r.bottom - 1, r.left, roomDebugCount);

                fillHLine(r.left, r.room.left, rty, roomDebugCount);
                points.add(new Vec2(r.left, rty));
            }
            

            rty = RandomUtil.getRandomRange(r.room.top, r.room.bottom);
            if(outOfArrayX(r.room.right, r.right)){
                fillHLine(r.room.right, r.right, rty, roomDebugCount);
                points.add(new Vec2(r.right, rty));
            }
        }

        // System.out.println(mapList.get(0).point.size());

        // for(int i = 0; i < mapList.size(); i++){
        //     int index_left = i;
        //     int index_right = (i + 1) % mapList.size();
        //     Rect r1 = mapList.get(index_left);
        //     Rect r2 = mapList.get(index_right);


        // }
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
        setValue(_x1, _y1, 3);
        setValue(_x2, _y2, 3);
        int h = Math.abs(_y2 - _y1);

        int hheight = RandomUtil.getRandomRange(2, h - 1);
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
                if(map[y][x] == 0){
                    System.out.print("  ");
                }
                else if(map[y][x] == 1){
                    System.out.print("  ");
                }
                else if(map[y][x] == 2){
                    System.out.print("+ ");
                }else{
                    System.out.print("[ ");
                }
            }
            System.out.println();
        }
    }

    void rectShow(){
        for (var e : mapList) {
            for(int y = e.top; y < e.bottom; y++){
                for(int x = e.left; x < e.right; x++){
                    if(y == e.top || y == e.bottom - 1 || x == e.left || x == e.right - 1){
                        setValue(x, y, 1);
                    }
                }
            }
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

    int getTileData(int _x, int _y){
        System.out.println("_x = " + _x + ", _y = " + _y);
        return map[_y][_x];
    }

    boolean outOfArray(final int _x, final int _y) {
        if (_x < 0 || _x >= WIDTH || _y < 0 || _y >= HEIGHT) {
            return true;
        }
        return false;
    }

    void swap(){
        // listの最後と2番目の要素を入れ替える
        boolean swapFlag = rand.nextBoolean();
        if(swapFlag){
            var temp = mapList.get(mapList.size() - 1);
            mapList.set(mapList.size() - 1, mapList.get(mapList.size() - 2));
            mapList.set(mapList.size() - 2, temp);
        }
    }
}