# affiliate-amazon
Example POC of Amazon Product Advertising API

Requires Java 7 or higher.

This code shows how to use the Amazon Product Advertising API to create a remote cart.

You need to create your own AWS account and Affiliate Account to get an API key, a AWS secret key and an affiliate tag.

Copy these values to src/main/resources/app.properties.template and rename the file to src/main/resources/app.properties
Then you can run:

mvn clean package jetty:run to test

a curl command like curl -v http://localhost:8080/cart/a168114704da3eef94a729573b56bc0a will create a remote cart and
return a string represenation of the Purchase URL
