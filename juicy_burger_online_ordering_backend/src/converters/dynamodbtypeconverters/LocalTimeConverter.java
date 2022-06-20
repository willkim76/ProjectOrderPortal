package converters.dynamodbtypeconverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import exceptions.OrderException;

import java.time.LocalTime;

public class LocalTimeConverter implements DynamoDBTypeConverter<String, LocalTime> {

    @Override
    public String convert(LocalTime object) {
        return object.toString();
    }

    @Override
    public LocalTime unconvert(String object) {
        try {
            return LocalTime.parse(object);
        } catch (Exception e) {
            throw new OrderException("Unable to parse string into LocalDateTime", e);
        }
    }
}
