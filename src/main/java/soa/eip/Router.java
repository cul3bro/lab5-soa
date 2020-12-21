package soa.eip;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Router extends RouteBuilder {

  public static final String DIRECT_URI = "direct:twitter";

  @Override
  public void configure() {
    from(DIRECT_URI)
      .log("Body contains \"${body}\"")
      .log("Searching twitter for \"${body}\"!")
      .process(new MaxProcessor())
      .toD("twitter-search:${body}")
      .log("Body now contains the response from twitter:\n${body}");
  }
}

class MaxProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    String mensaje = (String) exchange.getIn().getBody();
    String max = "5";

    if(mensaje.contains(" max:")){
      String aux[] = mensaje.split(" max:");
      mensaje = aux[0];
      max = aux[1];
    }

    exchange.getOut().setBody(mensaje + "?count=" + max);
  }

}
