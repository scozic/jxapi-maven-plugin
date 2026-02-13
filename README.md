# JXAPI Maven Plugin

Maven plugin for [JXAPI](https://github.com/scozic/jxapi-core/) code generation. Generate Java POJOs and REST/WebSocket API client libraries from JSON/YAML descriptors.

## Key Features

**🚀 Advanced Serialization**
- Custom Jackson serializers/deserializers for optimal performance
- Support for mixed data types (numeric/boolean as strings or native types)
- Flexible field mapping between POJOs and JSON
- Reusable POJO definitions across endpoints

**🔒 Production-Ready Features**
- **Rate Limiting**: Built-in enforcement prevents API bans
- **Authentication**: Custom HTTP request interceptors for complex auth flows
- **Pagination**: Automatic handling of paginated responses
- **Observability**: Built-in monitoring for metrics

**🌐 WebSocket Excellence**
- Multiplexed stream support on shared connections
- Automatic heartbeat and connection management
- Custom protocol handshake support

**📁 Developer Experience**
- **Modular Definitions**: Split large APIs into maintainable files
- **Logical Organization**: Group endpoints into functional categories  
- **Constants & Configuration**: Generated classes for API constants and config properties
- **Demo Snippets**: Ready-to-run examples for every endpoint
- **Auto Documentation**: Generated README with complete API reference

## Quick Start

```xml
<plugin>
    <groupId>org.jxapi</groupId>
    <artifactId>jxapi-maven-plugin</artifactId>
    <version>1.0.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate-pojos</goal>
                <goal>generate-exchanges</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Goals

### `jxapi:generate-pojos`

Generates Java POJOs from descriptors in `src/main/resources/jxapi/pojos/`.

```bash
mvn jxapi:generate-pojos
```

**Input:** `src/main/resources/jxapi/pojos/*.yaml`  
**Output:** `target/generated-sources/`

### `jxapi:generate-exchanges`

Generates REST/WebSocket API clients from descriptors in `src/main/resources/jxapi/exchange/`.

```bash
mvn jxapi:generate-exchanges
```

**Input:** `src/main/resources/jxapi/exchange/*.yaml`  
**Output:** `target/generated-sources/jxapi/` (main), `target/generated-test-sources/jxapi/` (demos)

## Configuration

```xml
<plugin>
    <groupId>org.jxapi</groupId>
    <artifactId>jxapi-maven-plugin</artifactId>
    <version>1.0.0</version>
    <configuration>
        <baseProjectDir>${project.basedir}</baseProjectDir>
        <mainSourcesDirectory>target/generated-sources/jxapi</mainSourcesDirectory>
        <testSourcesDirectory>target/generated-test-sources/jxapi</testSourcesDirectory>
        <baseJavaDocUrl>https://docs.example.com</baseJavaDocUrl>
    </configuration>
</plugin>
```

## Usage

### POJO Example

**Descriptor** (`src/main/resources/jxapi/pojos/model.yaml`):
```yaml
basePackage: com.example.model
pojos:
  - name: User
    properties:
      - name: id
        type: LONG
      - name: name
        type: STRING
```

**Generated Usage:**
```java
User user = new UserBuilder()
    .setId(123L)
    .setName("John")
    .build();
```

### Exchange Example

**Descriptor** (`src/main/resources/jxapi/exchange/api.yaml`):
```yaml
id: "UserAPI"
basePackage: "com.example.client"
httpUrl: "${config.baseUrl}"
# ... API definitions
```

**Generated Usage:**
```java
Properties config = new Properties();
config.setProperty("config.baseUrl", "https://api.example.com");

UserAPIExchange exchange = new UserAPIExchangeImpl(config);
FutureRestResponse<GetUserResponse> response = exchange.getUserApi().getUser(request);
```

### Demo Testing

```bash
# Copy demo config
cp target/generated-test-sources/jxapi/demo-userapi.properties.dist \
   src/test/resources/demo-userapi.properties

# Edit config and run demo
mvn test-compile exec:java -Dexec.mainClass="com.example.client.demo.GetUserDemoSnippet"
```

## Project Structure

```
project/
├── pom.xml
├── src/main/resources/jxapi/
│   ├── pojos/model.yaml
│   └── exchange/api.yaml
├── target/generated-sources/        # Generated code
└── target/generated-test-sources/   # Generated demos
```

## Requirements

- Java 17+
- Maven 3.8+

## Documentation

- [POJO Syntax](https://github.com/scozic/jxapi-core/doc/manual/PojoOnlyGeneration.md)
- [Exchange Syntax](https://github.com/scozic/jxapi-core/doc/manual/ExchangeDescriptorFileDoc.md)
- [Using Wrappers](https://github.com/scozic/jxapi-core/doc/manual/UsingTheWrapper.md)

## License

Apache License 2.0