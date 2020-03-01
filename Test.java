class Test{
    Dungeon map;

    Test(){
        map = new Dungeon();
        map.dmap.mapPrint();
    }

    public static void main(String[] args){
        new Test();
    }
}