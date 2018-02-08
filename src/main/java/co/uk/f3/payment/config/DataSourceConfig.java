package co.uk.f3.payment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "co.uk.f3.payment.repository", mongoTemplateRef = "mongoTemplate")
public class DataSourceConfig extends AbstractMongoConfiguration {

	public static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

	
	@Autowired
	private Environment env;

	@Override
	protected String getDatabaseName() {
		return env.getProperty("spring.data.mongodb.database");
	}

	@Override
	protected String getMappingBasePackage() {
		return "co.uk.f3.payment.model.domain";
	}

	@Override
	public Mongo mongo() throws Exception {
		LOGGER.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" +env.getProperty("spring.data.mongodb.host"));
		return new MongoClient(env.getProperty("spring.data.mongodb.host"),
				Integer.parseInt(env.getProperty("spring.data.mongodb.port")));
	}

}
