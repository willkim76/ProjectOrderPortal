package integreation;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;

import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PhaseAlpha test ensures that the DynamoDB tables are set
 * correctly
 */
public class PhaseAlpha {
    private static final String TABLE_NAME = "OrderHistory";

    private static final String GSI_INDEX_NAME = "PlacedDateTimeIndex";
    private static final String GSI_PARTITION_KEY = "placed_date";
    private static final String GSI_PROJECTION_TYPE = ProjectionType.ALL.toString();
    private static final String GSI_RANGE_KEY = "placed_time";

    private final DynamoDB client =
            new DynamoDB(
                    AmazonDynamoDBClientBuilder
                            .standard()
                            .withRegion(Regions.US_WEST_2)
                            .build()
            );

    @Test
    void expectedTable_exists() {
        Table table = client.getTable(TABLE_NAME);

        assertEquals(TABLE_NAME, table.getTableName(),
                String.format("Did not find expected table: %s", TABLE_NAME)
        );
    }

    @Test
    void expectedTable_hasExpectedGSI() {
        Table table = client.getTable(TABLE_NAME);

        assertEquals(1, table.describe().getGlobalSecondaryIndexes().size(),
                String.format("Did not expect to find multiple GSIs in table: %s", TABLE_NAME)
        );

        GlobalSecondaryIndexDescription gsiDescription = table.getDescription().getGlobalSecondaryIndexes().get(0);

        assertEquals(GSI_INDEX_NAME, gsiDescription.getIndexName(),
                String.format(
                        "Expected index name of %s but was unexpectedly: %s",
                        GSI_INDEX_NAME,
                        gsiDescription.getIndexName()
                )
        );

        assertEquals(GSI_PARTITION_KEY, gsiDescription.getKeySchema().get(0).getAttributeName(),
                String.format(
                        "Expected partitionKey name of %s but was unexpectedly: %s",
                        GSI_PARTITION_KEY,
                        gsiDescription.getKeySchema().get(0).getAttributeName()
                )
        );

        assertEquals(GSI_RANGE_KEY, gsiDescription.getKeySchema().get(1).getAttributeName(),
                String.format(
                        "Expected partitionKey name of %s but was unexpectedly: %s",
                        GSI_RANGE_KEY,
                        gsiDescription.getKeySchema().get(1).getAttributeName()
                )
        );

        assertEquals(GSI_PROJECTION_TYPE, gsiDescription.getProjection().getProjectionType(),
                String.format(
                        "Expected projectionType of %s but was unexpectedly: %s",
                        GSI_PROJECTION_TYPE,
                        gsiDescription.getProjection().getProjectionType()
                )
        );
    }
}
