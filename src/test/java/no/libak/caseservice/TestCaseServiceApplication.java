package no.libak.caseservice;

import org.springframework.boot.SpringApplication;

public class TestCaseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(CaseServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
