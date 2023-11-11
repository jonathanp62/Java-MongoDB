package net.jmp.demo.mongodb;

import com.mongodb.client.MongoClients;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import org.bson.codecs.pojo.PojoCodecProvider;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;

import static com.mongodb.client.model.Filters.eq;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

final class QuickStart {
    private static final String MONGODB_URI = "mongodb://localhost:27017";

    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    QuickStart() {
        super();
    }

    void run() {
        this.document();
        this.pojo();
    }

    private void document() {
        try (final var mongoClient = MongoClients.create(MONGODB_URI)) {
            final var database = mongoClient.getDatabase("local");
            final var collection = database.getCollection("demo");
            final var doc = collection.find(eq("prodId", 100)).first();

            if (doc != null) {
                this.logger.info(doc.toJson());
            } else {
                this.logger.info("No matching document(s) found");
            }
        }
    }

    private void pojo() {
        final CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        final CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        try (final var mongoClient = MongoClients.create(MONGODB_URI)) {
            final var database = mongoClient.getDatabase("local").withCodecRegistry(pojoCodecRegistry);
            final var collection = database.getCollection("demo", Demo.class);
            final var demo = collection.find(eq("prodId", 100)).first();

            if (demo != null) {
                this.logger.info(demo.toString());
            } else {
                this.logger.info("No matching document(s) found");
            }
        }
    }
}
