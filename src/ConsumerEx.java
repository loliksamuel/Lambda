/**
 * Created by lolik on 2017-05-23.
 */
import java.util.function.Consumer;
/*from  w  w  w  . ja  v a 2s  .co m*/

public class ConsumerEx {
    public static void main(String[] args) {
        int x = 99;

        Consumer<Integer> myConsumer = (y) ->
        {
            System.out.println("x = " + x); // Statement A
            System.out.println("y = " + y);
        };

        myConsumer.accept(x);
    }
}
