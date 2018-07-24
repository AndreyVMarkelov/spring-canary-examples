package ru.andreymarkelov.epam.canary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
public class ErrorController {
    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @Autowired
    private ErrorHandler errorHandler;

    @Value("${handleError:false}")
    private Boolean handleError;

    @GetMapping("/sample/{error}")
    @ResponseBody
    public Mono<String> sample(@PathVariable("error") Boolean error) {
        if (handleError || error) {
            log.error("Error request");
        }
        return Mono.justOrEmpty("OK");
    }

    @GetMapping("/errorCount")
    @ResponseBody
    public Mono<String> errorCount() {
        return Mono.justOrEmpty(String.valueOf(errorHandler.countErrors()));
    }
}
