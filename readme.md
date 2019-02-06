# Introduction

SOAP stands for Simple Object Access Protocol. It is a XML based protocol for accessing web service. By using SOAP, we can interact with other programming language applications. In another word, SOAP web services can be written in any programming language and executed in any platform. 

##Structure 

You can find how SOAP works in Java in the [Oracle Docs](https://docs.oracle.com/javaee/5/tutorial/doc/figures/jaxws-simpleClientAndService.gif). This project is implemented both server and client sides using SOAP. Our server and client communicates like the following image. 

![image-20190205212819950](https://ws4.sinaimg.cn/large/006tNc79ly1fzwn2cfvm7j30c50560te.jpg)

This documentation will introduce how server and client programs work, how you designed with code and detailed explaination. Lastly, client changes its system time by adding half of *RTT* (Round-trip time) and server's system time. Screenshots are attached as a proof of success. 

##Software, libraries and IDEs

* Eclipse Java EE IDE for Web Developers version: Oxygen.2 Release (4.7.2) as IDE
* Apache Tomcat v8.0 as the target runtime with default configuration
* Apache Axis as the web service runtime
* Java 8
* Maven to organize libraries
* `org.json` used to serialize and deserialize the mapping between JSON string and object

# Server

Server is created as a dynamic web project so that eclipse can do some automatic configurations to save life. Server runs on Apache 8. Server's responsiblity is pretty simple. It generates server's system time and returns the result as a JSON string. 
```java
public String getServerTime() {
    JSONObject jsonObject = new JSONObject();
    Calendar cal = Calendar.getInstance();
    Date date = cal.getTime();

    long timeInMillis = date.getTime(); 
    jsonObject.put("timeInMillis", String.valueOf(timeInMillis));
    return jsonObject.toString();
}
```

Server converts date to milliseconds in a format of `long`. It is convenient for client to sum up its RTT. Then, the result will be put into a `JSONObject` as key `"timeInMillis"`. 



Server uses the Web Service Definition Language WSDL (created by Eclipse) to describe the functionality offered by itself. It contains the information that how the service can be called, and what parameters, datastructures it expectes and returns. 



Let's take a look at one important section.

```      xml
<wsdl:service name="SOAPClassService">

    <wsdl:port binding="impl:SOAPClassSoapBinding" name="SOAPClass">

        <wsdlsoap:address location="http://192.168.0.13:8080/soap-wsdl-server/services/SoapClass"/>

    </wsdl:port>

</wsdl:service>
```

It defines the wsdl soap address, port number, server IP and where the service holds. These information will be used by client side later.



Accessing to an specific URL in the browser to see if server works well.

```latex
http://localhost:8080/soap-wsdl-Client/sampleSoapClassProxy/TestClient.jsp?endpoint=http://localhost:9163/SOAPExample/services/SoapClass
```

TCP/IP Monitor is able to show the SOAP of request and response for invoking `getServerTime()`.

![Screen Shot 2019-02-05 at 10.33.43 PM](https://ws3.sinaimg.cn/large/006tNc79ly1fzwoyqec00j31is0dqk0k.jpg)



### Request

``` xml
<?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><soapenv:Body><getServerTime xmlns="http://com"/></soapenv:Body></soapenv:Envelope>
```

### Response

```xml
<?xml version="1.0" encoding="utf-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><soapenv:Body><getServerTimeResponse xmlns="http://com"><getServerTimeReturn>{&quot;timeInMillis&quot;:&quot;1549428854618&quot;}</getServerTimeReturn></getServerTimeResponse></soapenv:Body></soapenv:Envelope>
```



# Client

Similar to server project, client is created as a web service. Client side runs on Apache 8 and Apache Axis. 

In order to show the robustness and readability of the code, some classes and interface are used. 

