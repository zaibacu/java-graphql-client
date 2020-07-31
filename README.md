# Java GraphQL Client

Interact with GraphQL API via Java. Nothing more, nothing less.

# Quickstart

## Install
...

## Usage

```java
public class Example{
    public List<Product> fetchProducts(){
        GraphqlClient client = GraphqlClient
            .builder()
            .withUrl("http://localhost:8000/graphql")
            .build();
        
        Products[] products = client
            .query("products")
            .withParameter("category", "shoes")
            .withParameter("priceRange", "cheap")
            .execute(Product[].class);

        return List.of(products);
    }
}
```

Where `Product` is:

```java
public class Product implements Serializable{
    private String name;
    private double price;
    private String category;
    private String priceRange;
}
```