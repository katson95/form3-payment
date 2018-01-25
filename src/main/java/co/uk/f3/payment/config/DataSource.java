package co.uk.f3.payment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "co.uk.f3.payment.repository", mongoTemplateRef = "mongoTemplate")
public class DataSource extends AbstractMongoConfiguration{

		@Override
		protected String getDatabaseName() {
			return "f3-payment-db";
		}

		@Override
		public MongoClient mongoClient() {
			
			return new MongoClient("localhost", 27017);
		}
		
		@Override
	    protected String getMappingBasePackage() {
	        return "co.uk.f3.payment.model";
	    }

}
