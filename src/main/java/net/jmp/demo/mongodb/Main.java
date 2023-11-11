package net.jmp.demo.mongodb;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

public final class Main {
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    private Main() {
        super();
    }

    private void run() {
        this.logger.info("Beginning MongoDb demo...");

        new QuickStart().run();

        this.logger.info("Ending MongoDb demo.");
    }

    public static void main(final String[] args) {
        new Main().run();
    }
}
