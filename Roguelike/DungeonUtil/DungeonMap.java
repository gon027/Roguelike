package Roguelike.DungeonUtil;

public class DungeonMap{
    private int[][] map;

    public final int WIDTH;
    public final int HEIGHT;

    public DungeonMap(int _width, int _height){
        WIDTH = _width;
        HEIGHT = _height;
        map = new int[_height][_width];
    }

    public void setValue(final int _x, final int _y, final int _value) {
        map[_y][_x] = _value;
    }

    public void fillValue(final int _sx, final int _sy, final int _gx, final int _gy, final int _value) {
        for (int y = _sy; y < _gy; y++) {
            for (int x = _sx; x < _gx; x++) {
                setValue(x, y, _value);
            }
        }
    }

    public int getMapChip(int _x, int _y) {
        if(outOfArray(_x, _y)){
            return -1;
        }

        return map[_y][_x];
    }

    public void mapClear(){
        fillValue(0, 0, WIDTH, HEIGHT, MapChip.MAP_WALL);
    }

    public boolean outOfArray(final int _x, final int _y) {
        if (_x < 0 || _x >= WIDTH || _y < 0 || _y >= HEIGHT) {
            return true;
        }
        return false;
    }

    public void mapPrint() {
        System.out.println();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                switch(getMapChip(x, y)){
                    case MapChip.MAP_WALL:{
                        System.out.print("  ");
                        break;
                    }
                    case MapChip.MAP_NONE:{
                        System.out.print(". ");
                        break;
                    }
                    case MapChip.MAP_GATE: {
                        System.out.print("+ ");
                        break;
                    }
                    case MapChip.MAP_OUT: {
                        System.out.print("# ");
                        break;
                    }
                    case MapChip.MAP_DEBUG:{
                        System.out.print("O ");
                        break;
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}