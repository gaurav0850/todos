package com.gaurav.springboot.todos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

@SpringBootApplication
public class TodosApplication {

	public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch("Application startup");
        stopWatch.start();
		SpringApplication.run(TodosApplication.class, args);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
	}

}
