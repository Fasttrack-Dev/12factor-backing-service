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
