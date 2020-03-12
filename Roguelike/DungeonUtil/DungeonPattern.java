package Roguelike.DungeonUtil;

public abstract class DungeonPattern{
    // 作成する
    public void create(DungeonMap _dmap){
        aaa(_dmap);
    }

    protected abstract void aaa(DungeonMap _dmap);
}

class Test extends DungeonPattern{
    @Override
    protected void aaa(DungeonMap _dmap){

    }
}


class AAA{
    DungeonMap dmap;
    DungeonPattern dpa;

    void aaaaa(){
        dpa = new Test();
        dpa.create(dmap);
    }
}
