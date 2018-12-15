package net.ujacha.onmyojibot.log;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import net.ujacha.onmyojibot.aws.DynamoDBClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LogService {

    private static final String LOG_TABLE_NAME = "OMJ_LOG_NOT_FOUND";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");


    @Autowired
    private DynamoDBClientFactory dynamoDBClientFactory;

    public void logNotFound(String userKey, String query){

        AmazonDynamoDB client = dynamoDBClientFactory.createClient();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(LOG_TABLE_NAME);

        String now = DATE_FORMAT.format(new Date());
        Item item = new Item()
                .withPrimaryKey("userKey", userKey)
                .withString("createAt", now)
                .withString("query", query);
        table.putItem(item);


    }

}
