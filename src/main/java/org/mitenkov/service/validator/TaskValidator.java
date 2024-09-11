package org.mitenkov.service.validator;

import lombok.RequiredArgsConstructor;
import org.mitenkov.configuration.properties.ValidationProperties;
import org.mitenkov.enums.ErrorCode;
import org.mitenkov.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TaskValidator {

    private final ValidationProperties validationProperties;
    private final String versionRegex = "[0-9]+\\.[0-9]+\\.[0-9]+";
    private final Pattern versionPattern = Pattern.compile(versionRegex);

    @Value("${app.validation.max-title-length}")
    private final int maxTitleLength;

    public void validateDeadline(LocalDate deadline) {
        if (deadline.isBefore(LocalDate.now().plus(validationProperties.getFeature().getMinTimeToDo()))) {
            throw new BusinessException(ErrorCode.ILLEGAL_VERSION);
        }
    }

    public void validateTitleLength(String title) {
        if (title.length() > maxTitleLength) {
            throw new BusinessException(ErrorCode.ILLEGAL_VERSION);
        }
    }

    public void validateVersionFormat(String version) {
        Matcher matcher = versionPattern.matcher(version);
        if (!matcher.matches()) {
            throw new BusinessException(ErrorCode.ILLEGAL_VERSION);
        }
    }

    public void validateVersionNumber(String version) {

        String[] minNumbers = validationProperties.getBug().getMinAppVersion().split("\\.");
        String[] numbers = version.split("\\.");

        for (int i = 0; i < minNumbers.length; i++) {

            int min = Integer.parseInt(minNumbers[i]);
            int number = Integer.parseInt(numbers[i]);

            if (min > number) {
                throw new BusinessException(ErrorCode.ILLEGAL_VERSION);
            } else if (min < number) {
                break;
            }
        }
    }

}
