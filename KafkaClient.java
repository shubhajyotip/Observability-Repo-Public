package com.example.kafkasample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// Main Spring Boot application class
@SpringBootApplication
@RestController // This makes this class capable of handling HTTP requests
public class KafkaEndpointsApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaEndpointsApplication.class, args);
    }

    private static final String TOPIC_NAME = "my-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; // Used to send messages to Kafka

    /**
     * Kafka Producer Endpoint (HTTP GET request)
     * This endpoint sends a message to the Kafka topic when accessed via HTTP.
     *
     * To test: Open your browser or use curl:
     * GET http://localhost:8080/kafka/publish/HelloKafka
     */
    @GetMapping("/kafka/publish/{message}")
    public String publishMessage(@PathVariable String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
        System.out.println("Published message: '" + message + "' to topic: " + TOPIC_NAME);
        return "Message '" + message + "' published to Kafka topic '" + TOPIC_NAME + "'";
    }

    /**
     * Kafka Consumer Endpoint (Kafka Listener)
     * This method automatically listens for messages on the specified Kafka topic.
     * Spring Kafka handles all the consumer group management, offset committing, etc.
     *
     * When a message arrives on "my-topic", this method will be invoked.
     */
    @KafkaListener(topics = TOPIC_NAME, groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received message in group 'my-group': '" + message + "' from topic: " + TOPIC_NAME);
        // Here you would typically process the consumed message, e.g.,
        // store it in a database, perform some business logic, etc.
    }

    // You can add more complex consumers with headers, Acks, etc.
    /*
    @KafkaListener(topics = TOPIC_NAME, groupId = "another-group")
    public void listenWithHeaders(String message,
                                  @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                  @Header(KafkaHeaders.OFFSET) long offset) {
        System.out.println("Received message with details: '" + message + "' from topic " + topic +
                           " on partition " + partition + " with offset " + offset);
    }
    */
}
