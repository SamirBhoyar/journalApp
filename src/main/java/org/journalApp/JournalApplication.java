package org.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
//@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {

        SpringApplication.run(JournalApplication.class, args);

//we can use this, to see which active env is running dev or prod.
//       ConfigurableApplicationContext contxt= SpringApplication.run(JournalApplication.class, args);
//        ConfigurableEnvironment env = contxt.getEnvironment();
//        System.out.println(env.getActiveProfiles()[0]);
    }





//    @Bean  //we can make bean object in "springApplication class/main class" as @springBootApplication allow for configuration of beans.
//    public PlatformTransactionManager transactionalFun(MongoDatabaseFactory dbFactory){
//        return  new MongoTransactionManager(dbFactory);
//    }

}

//Note:
//To implement @Transactional annotation we also need to specify the @EnableTransactionManagement in main class for container management of Transactional block.
//And, interface PlatformTransactionManager ---> which is implemented by MongoTransactionManager
//And, MongoDatabaseFactory which provides the factory function of Mongodb to connect and operate on db data.
