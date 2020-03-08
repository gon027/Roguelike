class Test{
    Dungeon map;

    Test(){
        map = new Dungeon();
        map.dmap.mapPrint();
        System.out.println();
    }

    public static void main(String[] args){
        new Test();
    }
}