import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.activemq.camel.component.ActiveMQComponent;

public class HelloCamel {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        try {
            context.addComponent("activemq",
                    ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"));
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("activemq:queue:test.queue1")
                        .to("stream:out");
                }
            });
            ProducerTemplate template = context.createProducerTemplate();
            context.start();
            template.sendBody("activemq:test.queue1", "Hello World");
            Thread.sleep(2000);
        } finally {
            context.stop();
        }
    }
}