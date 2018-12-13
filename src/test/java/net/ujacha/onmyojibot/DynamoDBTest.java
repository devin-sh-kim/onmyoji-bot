package net.ujacha.onmyojibot;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.junit.Test;

public class DynamoDBTest {

    @Test
    public void test(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2"))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        TableCollection<ListTablesResult> tables = dynamoDB.listTables();

        tables.iterator().forEachRemaining(table -> System.out.println(table.getTableName()));


        // CRUD



    }

}
