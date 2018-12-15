package net.ujacha.onmyojibot;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import net.ujacha.onmyojibot.aws.DynamoDBClientFactory;
import net.ujacha.onmyojibot.log.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamoDBTest {

    @Autowired
    private DynamoDBClientFactory dynamoDBClientFactory;

    @Autowired
    private LogService logService;

    @Test
    public void test(){
        AmazonDynamoDB client = dynamoDBClientFactory.createClient();

        // DynamoDB Client Factory
        DynamoDB dynamoDB = new DynamoDB(client);

        TableCollection<ListTablesResult> tables = dynamoDB.listTables();

        tables.iterator().forEachRemaining(table -> System.out.println(table.getTableName()));

        String tableName = "OMJ_LOG_NOT_FOUND";
        Table table = dynamoDB.getTable(tableName);


        // Insert item
//        Item item = new Item()
//                .withPrimaryKey("userKey", "qwer1234")
//                .withString("createAt", "20181215102030")
//                .withString("query", "입력 쿼리...");
//        table.putItem(item);

//        logService.logNotFound("qwer1234", "hwoijldskjdsi");
//        logService.logNotFound("qwer1234", "sakjqwoisa");

        // Read item
//        Item readItem = table.getItem(
//                "userKey", "qwer1234",
//                "createAt", "20181215102030");
//        System.out.println(readItem.toJSONPretty());

        // Scan
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(tableName);

        ScanResult result = client.scan(scanRequest);
        for (Map<String, AttributeValue> scanItem : result.getItems()){
            System.out.println(scanItem);
        }

        // TODO Update item

        // TODO Remove item






    }

}
