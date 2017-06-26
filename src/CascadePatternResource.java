import java.util.function.Consumer;

/**
 * Created by lolik on 2017-05-24.
 */
public class CascadePatternResource{

    public static void main(String[] args) {
        Resource.use( (r) -> {
            r.op1();
            r.op2();
        });
        Resource.use( r ->    r.op1());
        Resource.use( Resource::op2  );

    }

}

class Resource{

    private Resource(){

    }
    private Resource open(){
        System.out.println("openning connection");
        return this;
    }
    public Resource op1(){
        System.out.println("doing op1");
        return this;
    }
    public Resource op2(){
        System.out.println("doing op2");
        return this;
    }
    private Resource close(){
        System.out.println("closing connection");
        return this;//returning this make the cascade pattern....to improve readability
    }
    public static void use(Consumer<Resource> block){
        Resource resource = new Resource();
        try{
            resource.open();
            block.accept(resource);
        }
        finally{
            resource.close();
        }
        System.out.println("done using this resource with automatic resource open and close  ...");
    }
}
