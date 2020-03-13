package Roguelike.DungeonUtil;

import Roguelike.*;
import Roguelike.RandomUtil;

public class DungeonRoad{
    public void digRoad(Dungeon _dungeon, DungeonMap _dungeonMap){
        createDungeon(_dungeon, _dungeonMap);
    }

    private void createDungeon(Dungeon _dungeon, DungeonMap _dungeonMap){
        // 部屋から４方向へ道を伸ばす
        for (var rect : _dungeon.mapList) {
            if(rect.area.left != 0){
                int gy = RandomUtil.getRandomRange(rect.room.top, rect.room.bottom);
                _dungeon.fillHLine(rect.area.left, rect.room.left, gy, MapChip.MAP_NONE);
                _dungeonMap.setValue(rect.room.left - 1, gy, MapChip.MAP_GATE);
            }

            if (rect.area.right != 54) {
                int gy = RandomUtil.getRandomRange(rect.room.top, rect.room.bottom);
                _dungeon.fillHLine(rect.room.right, rect.area.right, gy, MapChip.MAP_NONE);
                _dungeonMap.setValue(rect.room.right, gy, MapChip.MAP_GATE);
            }

            if(rect.area.top != 0){
                int gx = RandomUtil.getRandomRange(rect.room.left, rect.room.right);
                _dungeon.fillVLine(rect.area.top, rect.room.top, gx, MapChip.MAP_NONE);
                _dungeonMap.setValue(gx, rect.room.top - 1, MapChip.MAP_GATE);
            }

            if (rect.area.bottom != 30) {
                int gx = RandomUtil.getRandomRange(rect.room.left, rect.room.right);
                _dungeon.fillVLine(rect.room.bottom, rect.area.bottom, gx, MapChip.MAP_NONE);
                _dungeonMap.setValue(gx, rect.room.bottom, MapChip.MAP_GATE);
            }
        }

        // 道をつなげる
        for (var rect : _dungeon.mapList) {
            if (rect.area.right != 54) {
                int sy = rect.area.top + 1;
                int gy = rect.area.bottom;
                int x = rect.area.right - 1;

                for(int i = sy; i < gy; i++){
                    var value = _dungeonMap.getMapChip(x, i);
                    if(value == MapChip.MAP_NONE){
                        sy = i;
                        break;
                    }
                }

                for(int i = gy; i >= sy; i--){
                    var value = _dungeonMap.getMapChip(x, i);
                    if(value == MapChip.MAP_NONE){
                        gy = i;
                        break;
                    }
                }

                _dungeon.fillVLine(sy, gy, x, MapChip.MAP_NONE);
            }

            if (rect.area.bottom != 30) {
                int sx = rect.area.left + 1;
                int gx = rect.area.right;
                int y = rect.area.bottom - 1;

                for(int i = sx; i < gx; i++){
                    var value = _dungeonMap.getMapChip(i, y);
                    if(value == MapChip.MAP_NONE){
                        sx = i;
                        break;
                    }
                }

                for(int i = gx; i >= sx; i--){
                    var value = _dungeonMap.getMapChip(i, y);
                    if (value == MapChip.MAP_NONE) {
                        gx = i;
                        break;
                    }
                }

                _dungeon.fillHLine(sx, gx, y, MapChip.MAP_NONE);

            }
        }
    }
}