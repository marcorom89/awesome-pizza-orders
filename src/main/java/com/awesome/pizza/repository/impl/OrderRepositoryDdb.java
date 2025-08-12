package com.awesome.pizza.repository.impl;

import com.awesome.pizza.model.Order;
import com.awesome.pizza.model.OrderStatus;
import com.awesome.pizza.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryDdb implements OrderRepository {
    private final DynamoDbTable<Order> table;
    private final DynamoDbIndex<Order> byEntityTypeAndCreatedAt;

    public OrderRepositoryDdb(DynamoDbEnhancedClient enhancedClient,
            @Value("${DYNAMO_TABLE:awesome-pizza-orders}") String tableName) {
        this.table = enhancedClient.table(tableName, TableSchema.fromBean(Order.class));
        this.byEntityTypeAndCreatedAt = this.table.index("byEntityTypeAndCreatedAt");
    }

    @Override
    public void save(Order order) {
        table.putItem(order);
    }

    @Override
    public Optional<Order> findById(String id) {
        Order o = table.getItem(Key.builder().partitionValue(id).build());
        return Optional.ofNullable(o);
    }

    @Override
    public List<Order> list(String entityType, OrderStatus status, int limit) {
        List<Order> out = new ArrayList<>();
        Iterable<Page<Order>> pages = byEntityTypeAndCreatedAt.query(r -> r.queryConditional(
                QueryConditional.keyEqualTo(k -> k.partitionValue(entityType)))
                .scanIndexForward(true) // ascending by createdAt
                .limit(limit));
        for (Page<Order> p : pages) {
            for (Order o : p.items()) {
                if (status == null || status.equals(o.getStatus())) {
                    out.add(o);
                    if (out.size() >= limit)
                        return out;
                }
            }
        }
        return out;
    }
}
