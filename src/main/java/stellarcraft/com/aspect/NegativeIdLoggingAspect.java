package stellarcraft.com.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class NegativeIdLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(NegativeIdLoggingAspect.class);

    @Before("execution(* stellarcraft.com.controller.SpacecraftController.getSpacecraftById(Long)) && args(id)")
    public void logNegativeId(Long id) {
        if (id < 0) {
            logger.warn("Se solicitó una nave con ID negativo: {}", id);
        }
    }
}
