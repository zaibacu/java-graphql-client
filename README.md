# Java GraphQL Client

Interact with GraphQL API via Java. Nothing more, nothing less.

# Quickstart

## Install

Maven:
```xml
<dependency>
    <groupId>com.github.zaibacu</groupId>
    <artifactId>graphql</artifactId>
    <version>1.1</version>
</dependency>
```

## Usage

### Query

```java
public class Example{
    public List<Product> fetchProducts(){
        GraphqlClient client = GraphqlClient
            .builder()
            .withUrl("http://localhost:8000/graphql")
            .build();
        
        List<Products> products = client
            .query("products")
            .withParameter("category", "shoes")
            .withParameter("priceRange", "cheap")
            .execute("products", Product.class);

        return products;
    }
}
```

Where `Product` is:

```java
public class Product implements Serializable{
    public String name;
    public double price;
    public String category;
    public String priceRange;
}
```


### Mutation

```java
public class Example {
    public void addProduct(Product product){
        GraphqlClient client = GraphqlClient
                    .builder()
                    .withUrl("http://localhost:8000/graphql")
                    .build();
        client
            .mutate("addProduct")
            .withParameter("name", "test")
            .withParameter("price", 42.0)
            .withParameter("category", "shoes")
            .withParameter("priceRange", "cheap")
            .execute("products");

    }
}
```
