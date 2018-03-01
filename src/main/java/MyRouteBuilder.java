import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    /**
     * Allow this route to be run as an application
     */
    public static void main(String[] args) throws Exception {
//        new Main().run(args);
    }

    public void configure() {
        // populate the message queue with some messages
        from("file:src/data?noop=true").
                to("jms:test.MyQueue");

        from("jms:test.MyQueue").
                to("file://target/test?noop=true");

        // set up a listener on the file component
        from("file://target/test?noop=true").
                bean(new SomeBean());
    }

    public static class SomeBean {

        public void someMethod(String body) {
            System.out.println("Received: " + body);
        }
    }

}
