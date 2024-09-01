import org.junit.jupiter.api.Test;
import org.mitenkov.configuration.properties.ValidationProperties;
import org.mitenkov.configuration.properties.ValidationProperties.BugProperties;
import org.mitenkov.configuration.properties.ValidationProperties.FeatureProperties;
import org.mitenkov.service.validator.TaskValidator;
import org.mockito.Mockito;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskValidatorTest {

   private final ValidationProperties validationProperties = Mockito.mock(ValidationProperties.class);
   private final TaskValidator taskValidator = new TaskValidator(validationProperties, 15);

    @Test
    void validateDeadlineTest() {
        Mockito.doReturn(new FeatureProperties(Period.ofDays(15))).when(validationProperties).getFeature();
        assertThrows(InvalidParameterException.class,
                () -> taskValidator.validateDeadline(LocalDate.now()));
        assertThrows(InvalidParameterException.class,
                () -> taskValidator.validateDeadline(LocalDate.now().plusDays(10)));
        taskValidator.validateDeadline(LocalDate.now().plusDays(20));
    }

    @Test
    void validateTitleLengthTest() {
        assertThrows(InvalidParameterException.class, () -> taskValidator.validateTitleLength("12345678901234567890"));
        taskValidator.validateTitleLength("Test");
    }

    @Test
    void validateVersionFormatTest() {
        assertThrows(PatternSyntaxException.class, () -> taskValidator.validateVersionFormat("123"));
        assertThrows(PatternSyntaxException.class, () -> taskValidator.validateVersionFormat("1.23"));
        assertThrows(PatternSyntaxException.class, () -> taskValidator.validateVersionFormat("1.23."));
        taskValidator.validateVersionFormat("1.0.0");
    }

    @Test
    void validateVersionNumberTest() {
        Mockito.doReturn(new BugProperties("1.10.15")).when(validationProperties).getBug();
        assertThrows(InvalidParameterException.class, () -> taskValidator.validateVersionNumber("1.1.2"));
        taskValidator.validateVersionFormat("2.3.4");
    }
}
