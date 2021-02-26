## Backing services example
This is a small sample application which utilizes a backing service to 
retrieve some JSON data. To showcase the independence, the application alternates 
between an external data source on the internet and a mock service that is spun
up by means of a Lambda function and an API Gateway.

The `pipeline` script gets everything set up, the `teardown` script removes 
all resources related to the application.

You can call the sample service with
```
http://12factor-backing-service.<environment>.<region>.elasticbeanstalk.com/currency
```
With every call, the application will call the other backing service to produce the result.

## Backing Service
The intresting part of the Backing service are in the following areas 
`cloudformation/backing-service-app.yml`
```yml
...
        - Namespace: "aws:elasticbeanstalk:application:environment"
          OptionName: "BACKING_SERVICE_ENDPOINT"
          Value: !Join
            - ""
            - - "https://"
              - !Ref DummyHttpApi
              - ".execute-api."
              - !Ref AWS::Region
              - ".amazonaws.com/"
              - !Ref DummyStage
              - "/"
        - Namespace: "aws:elasticbeanstalk:application:environment"
          OptionName: "BACKING_SERVICE_ENDPOINT_FALLBACK"
          Value: "https://api.frankfurter.app/latest"
...
```
and in the Java class file `src/main/java/backing/BackingController.java`
```java
    @Value("#{environment.BACKING_SERVICE_ENDPOINT}")
    private String endpoint;

    @Value("#{environment.BACKING_SERVICE_ENDPOINT_FALLBACK}")
    private String endpoint_fallback;
```
