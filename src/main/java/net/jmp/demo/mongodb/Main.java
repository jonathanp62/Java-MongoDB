package net.jmp.demo.mongodb;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Optional;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

public final class Main {
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    private Main() {
        super();
    }

    private void run() {
        this.logger.info("Beginning MongoDb demo...");

        final var properties = this.getAppProperties();

        properties.ifPresent(value -> new QuickStart(value).run());

        this.logger.info("Ending MongoDb demo.");
    }

    private Optional<Properties> getAppProperties() {
        this.logger.entry();

        Properties properties = null;

        final var configFileName = System.getProperty("app.configurationFile");

        if (configFileName != null) {
            this.logger.info("Loading the configuration from {}", configFileName);

            properties = new Properties();

            try (final var fis = new FileInputStream(configFileName)) {
                properties.load(fis);
            } catch (final IOException ioe) {
                this.logger.catching(ioe);
            }
        } else {
            this.logger.error("System property app.configurationFile was not found");
        }

        final var results = Optional.ofNullable(properties);

        this.logger.exit(results);

        return results;
    }

    public static void main(final String[] args) {
        new Main().run();
    }
}
