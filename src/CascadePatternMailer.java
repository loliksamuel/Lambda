import java.util.function.Consumer;

/**
 * Created by lolik on 2017-05-23.
 */

public class CascadePatternMailer {

    public static void main(String[] args) {
        Mailer.send( mailer -> {
                                mailer.to       ("to@example.com")
                                      .from     ("from@exmaple.com")
                                      .subject  ("Some subject")
                                      .body     ("Some content");
        });



        System.out.println("====================Consumer ex.");


        //Consumer consumer =  (Object o) ->  System.out.println(o);
        //Consumer consumer =   System.out::println;
        //Consumer consumer =  o ->  CascadePatternMailer.printNames(o);
        Consumer consumer =   CascadePatternMailer::printNames;
        consumer.accept("Jeremy");
        consumer.accept("Paul");
        consumer.accept("Richard");





    }

    private static void printNames(Object name) {
        System.out.println(name);
    }

}

class Mailer{

    private Mailer(){

    }
    public Mailer to(String address){
        System.out.println("To: "+address);
        return this;
    }
    public Mailer from(String address){
        System.out.println("From: "+address);
        return this;
    }
    public Mailer subject(String sub){
        System.out.println("Subject: "+sub);
        return this;
    }
    public Mailer body(String body){
        System.out.println("Body: "+body);
        return this;//returning this make the cascade pattern....to improve readability
    }
    public static void send(Consumer<Mailer> mailerOperator){
        Mailer mailer = new Mailer();
        mailerOperator.accept(mailer);
        System.out.println("Sending ...");
    }
}
