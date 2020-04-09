package com.boco.eoms.ruleproject.base.logback;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.io.IOException;

public class LogBackExEncoder extends PatternLayoutEncoder {
    static {
        PatternLayout.defaultConverterMap.put("T", ThreadNumConverter.class.getName());
        PatternLayout.defaultConverterMap.put("threadNum", ThreadNumConverter.class.getName());
    }
    @Override
    public void doEncode(ILoggingEvent event) throws IOException {
        super.doEncode(event);
    }
}