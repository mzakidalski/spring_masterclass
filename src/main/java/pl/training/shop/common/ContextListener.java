package pl.training.shop.common;

import lombok.extern.java.Log;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log
@Component
public class ContextListener {

    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event) {
        log.info("Spring context has been refreshed");
    }

}
