package converters.dynamodbtypeconverters;

import exceptions.OrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class LocalDateTimeConverterTest {
    private LocalDateTimeConverter localDateTimeConverter;

    @BeforeEach
    void setup() {
        localDateTimeConverter = new LocalDateTimeConverter();
    }

    @Test
    void convert_withLocalDateTime_returnsStringOfLocalDateTime() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        String expected = now.toString();

        // WHEN
        String actual = localDateTimeConverter.convert(now);

        // THEN
        assertEquals(expected, actual,
                String.format("Expected %s but was unexpectedly: %s", expected, actual)
        );
    }

    @Test
    void unconvert_withValidStringOfLocalDateTime_returnsLocalDateTime() {
        // GIVEN
        LocalDateTime expected = LocalDateTime.now();
        String expectedString = expected.toString();

        // WHEN
        LocalDateTime actual = localDateTimeConverter.unconvert(expectedString);

        // THEN
        assertEquals(expected, actual,
                String.format("Expected %s but was unexpectedly: %s", expected, actual)
        );
    }

    @Test
    void unconvert_withInvalidStringOfLocalDateTime_throws() {
        // GIVEN
        String invalidString = "asdf2";

        // WHEN - THEN
        assertThrows(OrderException.class, () ->
                localDateTimeConverter.unconvert(invalidString)
        );
    }
}
