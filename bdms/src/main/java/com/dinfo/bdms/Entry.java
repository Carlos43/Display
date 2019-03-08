package com.dinfo.bdms;

import com.dinfo.bdms.extract.ThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.dinfo.*"})
@SpringBootApplication
@EnableAutoConfiguration
public class Entry {

	public static void main(String[] args) {
		ThreadPool threadPool = new ThreadPool();
		SpringApplication.run(Entry.class, args);
	}
}
