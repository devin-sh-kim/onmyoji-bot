package net.ujacha.onmyojibot.log;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import net.ujacha.onmyojibot.aws.DynamoDBClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Profile("log-dynamoDB")
public class DynamoDbLogServiceImpl implements LogService {

    private static final Logger log = LoggerFactory.getLogger(DynamoDbLogServiceImpl.class);

    private static final String LOG_TABLE_NAME = "OMJ_LOG";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");


    @Autowired
    private DynamoDBClientFactory dynamoDBClientFactory;

    @Override
    public void logNotFound(String userKey, String query) {
        try {
            AmazonDynamoDB client = dynamoDBClientFactory.createClient();
            DynamoDB dynamoDB = new DynamoDB(client);

            Table table = dynamoDB.getTable(LOG_TABLE_NAME);

            String now = DATE_FORMAT.format(new Date());
            Item item = new Item()
                    .withPrimaryKey("logType", "NOT_FOUND")
                    .withString("createAt", now)
                    .withString("userKey", userKey)
                    .withString("query", query);
            table.putItem(item);

        }catch (Exception e){
            log.error("{}", e.getMessage());
        }


    }

}
