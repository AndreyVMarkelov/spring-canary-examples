package ru.andreymarkelov.epam.canary;

import java.util.concurrent.atomic.AtomicLong;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandler extends TurboFilter implements InitializingBean {
    private final AtomicLong errorCount;
    private final Counter counter;

    public ErrorHandler(@Value("${build.version}") String buildVersion) {
        this.errorCount = new AtomicLong(0);
        this.counter = Metrics.counter("all_errors", "version", buildVersion);
    }

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        if (level.toInt() == Level.ERROR.toInt() && format != null) {
            errorCount.incrementAndGet();
            counter.increment();
        }
        return FilterReply.NEUTRAL;
    }

    public long countErrors() {
        return errorCount.get();
    }

    @Override
    public void afterPropertiesSet() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.addTurboFilter(this);
    }
}
