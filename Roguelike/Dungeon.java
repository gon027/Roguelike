package Roguelike;

import java.util.*;
import Roguelike.DungeonUtil.*;

public class Dungeon{
    private final DungeonMap dmap;
    public final ArrayList<DungeonRect> mapList;
    private final DungeonRoad dungeonRoad;

    // 部屋の大きさの最大値と最小値
    private final int MINROOMSIZE = 4;
    private final int MAXROOMSIZE = 6;
    private final int MINROOMSIZEY = 4;

    private final int PADDING = 2;

    // 部屋の数
    private int roomCount = 0;

    public Dungeon() {
        this(54, 30);
    }

    public Dungeon(final int _width, final int _height) {
        dmap = new DungeonMap(_width, _height);
        mapList = new ArrayList<DungeonRect>();
        dungeonRoad = new DungeonRoad();
        createDungeon();
    }

    void createDungeon() {
        init();
        divisionMap(mapList.get(0));
        createRoom();
        dungeonRoad.digRoad(this, dmap);
        // fillOutSideRect();
    }

    void init() {
        roomCount = 0;
        dmap.mapClear();
        mapList.clear();
        mapList.add(new DungeonRect());
        mapList.get(0).setRect(0, 0, dmap.WIDTH, dmap.HEIGHT, roomCount);
    }

    void divisionMap(final DungeonRect _r) {
        final boolean hvFrag = RandomUtil.getFrag();

        if (hvFrag) {
            verticalSplit(_r); // 縦に分割
        } else {
            horizontalSplit(_r); // 横に分割
        }
    }

    // 縦
    void verticalSplit(final DungeonRect _r) {
        if (_r.area.getWidth() <= (MINROOMSIZE + 3) * 2 + 5) {
            return;
        }

        // 分割点
        int div = _r.area.getWidth() - (MINROOMSIZE * 2) - (PADDING * 2);
        div = Math.min(div, MAXROOMSIZE);
        final int left = (_r.area.left + 8) + RandomUtil.getRandomRange(0, div);

        roomCount++;
        final DungeonRect child = new DungeonRect();
        child.setRect(left, _r.area.top, _r.area.right, _r.area.bottom, roomCount);
        mapList.add(child);

        _r.area.setRectAngle(_r.area.left, _r.area.top, left + 1, _r.area.bottom);

        divisionMap(mapList.get(mapList.size() - 1));
        divisionMap(_r);
    }

    // 横
    void horizontalSplit(final DungeonRect _r) {
        // (3 + 3) * 2 + 3 = 6 * 2 + 3 = 12 + 3 = 15
        if (_r.area.getHeight() <= (MINROOMSIZEY + 3) * 2 + 1) {
            return;
        }

        int div = _r.area.getHeight() - (MINROOMSIZE * 2) - 4;
        div = Math.min(div, MAXROOMSIZE);

        final int top = (_r.area.top + 7) + RandomUtil.getRandomRange(0, div);

        roomCount++;
        final DungeonRect child = new DungeonRect();
        child.setRect(_r.area.left, top, _r.area.right, _r.area.bottom, roomCount);
        mapList.add(child);

        _r.area.setRectAngle(_r.area.left, _r.area.top, _r.area.right, top + 1);

        divisionMap(mapList.get(mapList.size() - 1));
        divisionMap(_r);
    }

    void createRoom() {
        for (final var e : mapList) {
            // 部屋の大きさを求める
            final int width = e.area.getWidth() - 2;
            final int height = e.area.getHeight() - 2;

            // 部屋の大きさを[MIN, w / h]の範囲で決める
            final int rx = RandomUtil.getRandomRange(MINROOMSIZE, width - 1);
            final int ry = RandomUtil.getRandomRange(MINROOMSIZE, height) - 1;

            // 空きサイズ(区間 - 部屋)
            final int fx = (width - rx);
            final int fy = (height - ry);

            // 部屋の左上の位置
            final int lux = RandomUtil.getRandomRange(1, fx) + 1;
            final int luy = RandomUtil.getRandomRange(1, fy) + 1;

            final int sx = e.area.left + lux;
            final int gx = sx + rx;
            final int sy = e.area.top + luy;
            final int gy = sy + ry;

            e.setRoom(sx, sy, gx, gy);
            dmap.fillValue(sx, sy, gx, gy, MapChip.MAP_NONE);
        }
    }

    public void mapPrint() {
        System.out.println();
        for (int y = 0; y < dmap.HEIGHT; y++) {
            for (int x = 0; x < dmap.WIDTH; x++) {
                switch (dmap.getMapChip(x, y)) {
                    case MapChip.MAP_WALL: {
                        System.out.print("  ");
                        break;
                    }
                    case MapChip.MAP_NONE: {
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
                    case MapChip.MAP_DEBUG: {
                        System.out.print("O ");
                        break;
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void fillHLine(int _left, int _right, final int _y, final int _value) {
        if (_left > _right) {
            final int t = _left;
            _left = _right;
            _right = t;
        }

        dmap.fillValue(_left, _y, _right, _y + 1, _value);
    }

    public void fillVLine(int _top, int _bottom, final int _x, final int _value) {
        if (_top > _bottom) {
            final int t = _top;
            _top = _bottom;
            _bottom = t;
        }

        dmap.fillValue(_x, _top, _x + 1, _bottom, _value);
    }

    void fillOutSideRect() {
        for (var rect : mapList) {
            fillHLine(rect.area.left, rect.area.right - 1, rect.area.top, MapChip.MAP_DEBUG);
            fillHLine(rect.area.left, rect.area.right - 1, rect.area.bottom - 1, MapChip.MAP_DEBUG);
            fillVLine(rect.area.top, rect.area.bottom - 1, rect.area.left, MapChip.MAP_DEBUG);
            fillVLine(rect.area.top, rect.area.bottom - 1, rect.area.right - 1, MapChip.MAP_DEBUG);
        }
    }
}