# Countersign Validator

A custom password validator (custom password policy extension) implementation to validate password against a set of commonly used dictionary values and username.

Simple put, the custom password validator (countersign validator) throws an error if the password is same as the useranem, and if the password contains any of the following strings ...

* admin
* password
* root
* 123

## Build

Build the project by running ...

```shell
mvn clean package
```

## Deploy

After a successful build, copy the `countersign-validator-1.0.0.jar` artifact from the `target` folder and paste it inside `<IS HOME>/repository/components/dropins` folder. And specify the deployed password validator as an extension inside the `<IS HOME>/repository/conf/identity/identity-mgt.properties` file.

Simply add the following lines at the end of the `identity-mgt.properties` file to use the custom password validator implementation

```properties
Password.policy.extensions.1=com.athiththan.sample.CountersignValidator
Password.policy.extensions.1.faultMsg="Validation failed :: according to Countersign Validator"
```

Navigate to `<IS HOME>/repository/conf/identity/identity.xml` and enable the following `EventListener` to `enable=true`

```xml
<EventListener type="org.wso2.carbon.user.core.listener.UserOperationEventListener"
                       name="org.wso2.carbon.identity.mgt.IdentityMgtEventListener"
                       orderId="50" enable="true"/>
```

## Run

Start your WSO2 Identity Server by executing the command from your `<IS HOME>/bin` folder

```shell
sh wso2server.sh
```

or

```shell
wso2is-5.5.0
```

## Test & Results

Navigate to the [`Carbon Management console`](https://localhost:9443/carbon) and login as admin using `admin` for both the username and password.

Navigate to [`Main -> Identity -> Users and Roles -> Add -> Add New User`](https://localhost:9443/carbon/user/add-step1.jsp)

![Add User Screen WSO2 Identity Server](https://docs.wso2.com/download/attachments/60494066/Add%20New%20User.png?version=1&modificationDate=1508308523000&api=v2)

and provide

1. your favourite string as both username and password
2. enter any username and enter a password containing any above mentioned strings

click finish and see the prompted error messages.
