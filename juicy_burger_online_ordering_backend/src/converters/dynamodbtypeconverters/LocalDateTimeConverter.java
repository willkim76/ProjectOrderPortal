package converters.dynamodbtypeconverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import exceptions.OrderException;

import java.time.LocalDateTime;

/**
 * A converter class for the LocalDateTime data type for DynamoDB parsing
 * @author willi
 */
public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

    /**
     * Converts a LocalDateTime Object into a String representation for
     * DyanmoDB
     * @param object LocalDateTime to convert
     * @return String representation of the LocalDateTime
     */
    @Override
    public String convert(LocalDateTime object) {
        return object.toString();
    }

    /**
     * Converts a String representation of a LocalDateTime in to a Java
     * LocalDateTime Object
     * @param object String to convert
     * @return LocalDateTime based on a valid String
     * @throws OrderException for invalid String
     */
    @Override
    public LocalDateTime unconvert(String object) {
        try {
            return LocalDateTime.parse(object);
        } catch (Exception e) {
            throw new OrderException("Unable to parse string into LocalDateTime", e);
        }
    }
}
