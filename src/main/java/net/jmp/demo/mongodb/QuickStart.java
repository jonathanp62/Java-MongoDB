package net.jmp.demo.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.Properties;

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
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));
    private final Properties properties;
    private final String dbName;
    private final String collectionName;

    QuickStart(final Properties properties) {
        super();

        this.properties = properties;

        this.dbName = properties.getProperty("mongodb.db", "local");
        this.collectionName = properties.getProperty("mongodb.collection", "demo");
    }

    void run() {
        try (final var mongoClient = MongoClients.create(properties.getProperty("mongodb.uri"))) {
            this.document(mongoClient);
            this.pojo(mongoClient);
            this.documents(mongoClient);
            this.pojos(mongoClient);
        }
    }

    private void document(final MongoClient mongoClient) {
        final var database = mongoClient.getDatabase(this.dbName);
        final var collection = database.getCollection(this.collectionName);
        final var doc = collection.find(eq("prodId", 100)).first();

        if (doc != null) {
            if (this.logger.isInfoEnabled())
                this.logger.info(doc.toJson());
        } else {
            this.logger.info("No matching document(s) found");
        }
    }

    private void pojo(final MongoClient mongoClient) {
        final CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        final CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        final var database = mongoClient.getDatabase(this.dbName).withCodecRegistry(pojoCodecRegistry);
        final var collection = database.getCollection(this.collectionName, Demo.class);
        final var demo = collection.find(eq("prodId", 100)).first();

        if (demo != null) {
            if (this.logger.isInfoEnabled())
                this.logger.info(demo.toString());
        } else {
            this.logger.info("No matching document(s) found");
        }
    }

    private void documents(final MongoClient mongoClient) {
        final var database = mongoClient.getDatabase(this.dbName);
        final var collection = database.getCollection(this.collectionName);
        final var docs = collection.find();

        for (final var doc : docs) {
            if (this.logger.isInfoEnabled())
                this.logger.info(doc.toJson());
        }
    }

    private void pojos(final MongoClient mongoClient) {
        final CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        final CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        final var database = mongoClient.getDatabase(this.dbName).withCodecRegistry(pojoCodecRegistry);
        final var collection = database.getCollection(this.collectionName, Demo.class);
        final var demos = collection.find();

        for (final var demo : demos) {
            if (this.logger.isInfoEnabled())
                this.logger.info(demo.toString());
        }
    }
}
