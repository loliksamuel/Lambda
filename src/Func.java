import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.*;
import java.util.stream.Collectors;

public class Func {
    public static Integer calc(int value, Function<Integer,Integer> callback) {
        return callback.apply(value);
    }

    public static Integer calc2(int value1, int value2, BiFunction<Integer, Integer, Integer>  callback) {
        return callback.apply(value1, value2);
    }

    public static boolean isEven(int value)  {
            return value % 2 == 0;
    }
    public static boolean isOdd(int value)  {
            return value % 2 != 0;
    }

    public static boolean isGt(int value1){
        return value1 > 3;
    }

    public static boolean isGt2(int n1, int n2) {
        return (n1 > n2);
    }

    public static List<Integer> findInts(List      <Integer> ints,   Predicate <Integer> p)        {
        List<Integer> result = new ArrayList<>();
        for(Integer intgr: ints) {
            if(p.test(intgr)) {
                result.add(intgr);
            }
        }
        return result;
    }

    public static void execute(Consumer<Integer> ex){
        System.out.println("do something 1 ...");
        ex.accept(15);
        System.out.println("do something 3 ...");
    }


    public static void findReceiver()
    {
        System.out.println("finding receiver...");
    }

    public static void sendMsg()
    {
        System.out.println("sending to receiver...");
    }

    public static void notify2()
    {
        System.out.println("notify receiver...");
    }

    public static void main(String[] args) {
        int x =1;
        Function        <String,           String>  concat1   = e -> e + 1;
        Function        <Integer,          Integer> add1      = e -> e + 1;
        Function        <Integer,          Integer> doubleIt  = e -> e * 2;
        Function        <Integer, Function<Integer,Integer>> makeAdder = z -> y -> z + y; // High Order Programming:   The cool thing about functions is that they encapsulate behavior, and now we can take a piece of code, put it into a function and pass it around to other functions or methods for them to use it
        BiFunction      <Integer, Integer, Integer> add       = (a, b)-> Integer.valueOf(a.intValue() + b.intValue());
        BiFunction      <Integer, Integer, Integer> mult      = (a, b)-> Integer.valueOf(a.intValue() * b.intValue());

        UnaryOperator   <Integer> add_1             = n -> n + 1;
        BinaryOperator  <Integer> add2Nmubers       = (a, b) -> a + b;
        Predicate       <Integer> p_isGreaterThan3  = e->e > 3;//e->isGt(3);
        Predicate       <Integer> p_isEven          = Func::isEven;
        Supplier        <String> supplierStr  = ()-> "supplier.get()=java2s.com";


        System.out.println(supplierStr.get());
        Consumer        <Integer> consumerSysout    = (y) -> {  System.out.println("x = " + x); // Statement A
                                                                System.out.println("y = " + y);
                                                            };
        consumerSysout.accept(x);


        System.out.println("1+1="+calc(x,add1));//2
        System.out.println("1+1="+calc(x,add_1)); // 2
        System.out.println("1+2="+calc2(x,2, add2Nmubers)); // 3

        System.out.println(calc(x,doubleIt));//2
        System.out.println(calc(x,add1.andThen(doubleIt)));//4
        System.out.println(calc2(10,12, add));//22
        System.out.println(calc2(10,12, mult));//120

        List<Integer> items =   Arrays.asList(1, 2, 3, 4, 4, 5, 6, 7, 8,9);
        System.out.println("count all     ="+items.stream().count());
        System.out.println("count distinct="+items.stream().distinct().count());
        System.out.println("count all even="+items.stream()
                                                  .filter(Func::isEven)//Func::isEven      or       e->Func.isEven(e)      or        e->e%2==0
                                                  .distinct()
                                                  .count());
        System.out.println("count all odds="+items.stream()
                                                  .filter(e->e%2==1)
                                                  .distinct()
                                                  .count());
        System.out.println("count all even and > 3="+items.stream()
                                                  .filter(p_isGreaterThan3.and(p_isEven))
                                                  .distinct()
                                                  .count());

        System.out.println("count all > 3 = "+items.stream()
                                                  .filter(e->Func.isGt2(e,3) )
                                                  .distinct()
                                                  .count());
        System.out.println("20 exists?  "+items.stream()
                                               .anyMatch(y -> y.equals(20)));
        System.out.println("group by and count elements > index 3 = "+items.stream()
                                                        .skip(3)
                .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()
                )));

        System.out.println("min of list= "+items.stream() .min((z,y)->z-y).get());
        System.out.println("sum of list= "+items.stream() .reduce(0,(z,y)->z+y));
        System.out.println("find any(unpredictable results due to multithreading)= "+items.stream() .findAny().get());

        items.stream() .map(z->z+1).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("stats= "+items.stream() .mapToInt(z->z).summaryStatistics());
        System.out.println("is 2 even ? "+isEven(2));
        System.out.println("findInts > 3  = "+Func.findInts(items, e->Func.isGt2(e, 3)));
        System.out.println("find even ints= "+Func.findInts(items, Func::isEven));
        System.out.println("findInts > 3  = "+Func.findInts(items, e->Func.isGt2(e, 3)));

        Func.execute( (y) -> {
            System.out.println("x = " + x); // Statement A
            System.out.println("y = " + y);
            });
        List names = new ArrayList();

        names.add("Mahesh");
        names.add("Suresh");
        names.add("Ramesh");
        names.add("Naresh");
        names.add("Kalpesh");

        names.forEach(System.out::println);
        names.stream().forEach(System.out::println);


        CompletableFuture.supplyAsync(supplierStr)
                         .thenApply(concat1)
                         .thenAccept(y -> System.out.println("doo something..."));

        new Thread(() -> {  System.out.println("do a long task asynchronously ....");}
        ).start();
    }

}
