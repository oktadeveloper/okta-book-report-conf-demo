# Okta Book Report demo app
 
An example app that shows how to Okta to add authentication and authorization to a simple Spring Boot app.

**Prerequisites:** [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

> [Okta](https://developer.okta.com/) has Authentication and User Management APIs that reduce development time with instant-on, scalable user infrastructure. Okta's intuitive API and expert support make it easy for developers to authenticate, manage, and secure users and roles in any application.

* [Getting Started](#getting-started)
* [Links](#links)
* [Help](#help)
* [License](#license)

## Getting started

To install this example application, run the following commands:

```bash
git clone git@github.com:oktadeveloper/okta-book-report-conf-demo.git
cd okta-book-report-conf-demo
```

This will download a copy of the project.

### Create an Application in Okta

You will need to create an OpenID Connect Application in Okta to perform authentication. 

Log in to your Okta Developer account (or [sign up](https://developer.okta.com/signup/) if you don’t have an account) and navigate to **Applications** > **Add Application**. Click **Web**, click **Next**, and give the app a name you’ll remember. Click **Done** and note the client ID and secret at the bottom of the page.

Copy these values into `src/main/resources/application.yml`:

```yaml
okta:
  oauth2:
    baseUrl: https://{yourOktaDomain}.com # your Okta Organization URL
    issuer: https://{yourOktaDomain}.com/oauth2/default
    audience: api://default
    clientId: {clientId} # OIDC app Client ID
    clientSecret: {clientSecret} # OIDC app Client Secret
    redirectUri: http://localhost:8080 # must be registered in the OIDC app settings
```

**NOTE:** The value of `{yourOktaDomain}` should be something like `dev-123456.oktapreview`. Make sure you don't include `-admin` in the value!

### Start the app

To install all of the dependencies and start the app, run:
 
```bash
./mvnw spring-boot:run
```

## Links

This example uses the following libraries provided by Okta:

* [Okta Spring Boot Starter](https://github.com/okta/okta-spring-boot)
* [Okta Java SDK](https://github.com/okta/okta-sdk-java)

## Help

Please post any questions on the [Okta Developer Forums](https://devforum.okta.com/). You can also email developers@okta.com if you would like to create a support ticket.

## License

Apache 2.0, see [LICENSE](LICENSE).
