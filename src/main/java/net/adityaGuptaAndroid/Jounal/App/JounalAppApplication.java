package net.adityaGuptaAndroid.Jounal.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication

public class JounalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JounalAppApplication.class, args);
	}


//	@Bean
//	public PlatformTransactionManager TransactionalImplementation(MongoDatabaseFactory db_factory){
//		return new MongoTransactionManager(db_factory);
//	}

}



