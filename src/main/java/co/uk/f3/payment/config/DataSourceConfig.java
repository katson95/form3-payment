package co.uk.f3.payment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "co.uk.f3.payment.repository", mongoTemplateRef = "mongoTemplate")
public class DataSourceConfig extends AbstractMongoConfiguration{

		@Override
		protected String getDatabaseName() {
			return "f3-payment-db";
		}
		
		@Override
	    protected String getMappingBasePackage() {
	        return "co.uk.f3.payment.model.domain";
	    }

		@Override
		public Mongo mongo() throws Exception {
			return new MongoClient("localhost", 27017);
		}

}
