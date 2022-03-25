package com.ensar.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.quicksight.AmazonQuickSight;
import com.amazonaws.services.quicksight.AmazonQuickSightClientBuilder;
import com.amazonaws.services.quicksight.model.GenerateEmbedUrlForRegisteredUserRequest;
import com.amazonaws.services.quicksight.model.GenerateEmbedUrlForRegisteredUserResult;
import com.amazonaws.services.quicksight.model.RegisteredUserDashboardEmbeddingConfiguration;
import com.amazonaws.services.quicksight.model.RegisteredUserEmbeddingExperienceConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//https://docs.aws.amazon.com/quicksight/latest/user/embedded-dashboards-for-authenticated-users-step-1.html

@Component
public class QuickSightURLGenerator {

    @Value("${AWS_ACCESS_KEY:}")
    private String access_key;

    @Value("${AWS_SECRET_KEY:}")
    private String secret_key;

    @Value("${AWS_ACCOUNT_ID:}")
    private String account_id;

    @Value("${AWS_USER_ARN:}")
    private String user_arn;

    private final AmazonQuickSight quickSightClient;

    public QuickSightURLGenerator() {
        quickSightClient = AmazonQuickSightClientBuilder
                .standard()
                .withRegion(Regions.US_WEST_2.getName())
                .withCredentials(new AWSCredentialsProvider() {
                                     @Override
                                     public AWSCredentials getCredentials() {
                                         // provide actual IAM access key and secret key here
                                         return new BasicAWSCredentials(access_key, secret_key);
                                     }

                                     @Override
                                     public void refresh() {
                                     }
                                 }
                )
                .build();
    }

    public String getQuicksightEmbedUrl(final Long dashboardId) {
        final RegisteredUserEmbeddingExperienceConfiguration experienceConfiguration = new RegisteredUserEmbeddingExperienceConfiguration()
                .withDashboard(new RegisteredUserDashboardEmbeddingConfiguration().withInitialDashboardId(dashboardId.toString()));
        final GenerateEmbedUrlForRegisteredUserRequest generateEmbedUrlForRegisteredUserRequest = new GenerateEmbedUrlForRegisteredUserRequest();
        generateEmbedUrlForRegisteredUserRequest.setAwsAccountId(account_id);
        generateEmbedUrlForRegisteredUserRequest.setUserArn(user_arn);
        generateEmbedUrlForRegisteredUserRequest.setSessionLifetimeInMinutes(60L);
        generateEmbedUrlForRegisteredUserRequest.setExperienceConfiguration(experienceConfiguration);

        final GenerateEmbedUrlForRegisteredUserResult generateEmbedUrlForRegisteredUserResult = quickSightClient.generateEmbedUrlForRegisteredUser(generateEmbedUrlForRegisteredUserRequest);

        return generateEmbedUrlForRegisteredUserResult.getEmbedUrl();
    }

}

