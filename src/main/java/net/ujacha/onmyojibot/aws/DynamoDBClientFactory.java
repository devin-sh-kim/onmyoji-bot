package net.ujacha.onmyojibot.aws;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.stereotype.Component;

@Component
public class DynamoDBClientFactory {

    public AmazonDynamoDB createClient() {

        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
//        builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2"));
        return builder.build();

    }

}
