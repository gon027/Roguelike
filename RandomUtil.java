import java.util.*;

class RandomUtil{
    public static Random rand = new Random();

    // [min, max)の範囲のランダムな数値を取得
    public static int getRandomRange(final int _min, final int _max){
        final int rand = (int) (Math.random() * (_max - _min)) + _min;
        return rand;
    }
}