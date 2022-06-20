package converters.dynamodbtypeconverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import exceptions.OrderException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {

    @Override
    public String convert(LocalDate object) {
        return object.toString();
    }

    @Override
    public LocalDate unconvert(String object) {
        try {
            return LocalDate.parse(object);
        } catch (Exception e) {
            throw new OrderException("Unable to parse string into LocalDateTime", e);
        }
    }
}
