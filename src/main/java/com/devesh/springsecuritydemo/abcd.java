package com.devesh.springsecuritydemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

public class abcd implements CommandLineRunner {


    Logger logger= LoggerFactory.getLogger(abcd.class);

    @Override
    public void run(String... args) throws Exception {
        System.out.println("  Hi This is working ");
        logger.info("  command line runner is working ");
    }

    public abcd() {
    }
}
