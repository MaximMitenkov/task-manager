package org.mitenkov.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.PeriodUnit;

import java.time.Period;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "app.validation")
@Data
public class ValidationProperties {

    private BugProperties bug;
    private FeatureProperties feature;
    //private int maxTitleLength;

    @Data
    public static class BugProperties {
        private String minAppVersion;
    }

    @Data
    public static class FeatureProperties {
        @PeriodUnit(ChronoUnit.DAYS)
        private Period minTimeFeature;
    }

}