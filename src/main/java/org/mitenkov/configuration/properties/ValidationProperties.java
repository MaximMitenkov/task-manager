package org.mitenkov.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Period;

@ConfigurationProperties(prefix = "app.validation")
@Data
@Builder
public class ValidationProperties {

    private BugProperties bug;
    private FeatureProperties feature;

    private int maxTitleLength;

    @Data
    @AllArgsConstructor
    public static class BugProperties {
        private String minAppVersion;
    }

    @Data
    @AllArgsConstructor
    public static class FeatureProperties {
        private Period minTimeToDo;
    }

}
