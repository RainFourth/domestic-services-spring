package com.rrain.springdomesticservicesapp.config;


import com.rrain.springdomesticservicesapp.model.Role;
import com.rrain.springdomesticservicesapp.model.Service;
import com.rrain.springdomesticservicesapp.model.User;
import com.rrain.springdomesticservicesapp.repo.RoleRepository;
import com.rrain.springdomesticservicesapp.repo.ServiceRepository;
import com.rrain.springdomesticservicesapp.repo.UserRepository;
import com.rrain.springdomesticservicesapp.utils.PathProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.FileInputStream;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner ensureIndexes(MongoTemplate mongoTemplate){
        return args -> {
            //создать индекс если его нет
            //mongoTemplate.indexOps("YOUR_COLLECTION_NAME").ensureIndex(new Index("YOUR_FEILD_OF_COLLECTION", Direction.ASC).unique());
            //Индекс делает поле в монго уникальным
            mongoTemplate.indexOps("roles").ensureIndex(new Index("role", Sort.Direction.ASC).unique());
            mongoTemplate.indexOps("users").ensureIndex(new Index("login", Sort.Direction.ASC).unique());
        };
    }


    private <T,R extends MongoRepository<T,?>,K> void create(Iterable<T> documents, R repo, Function<K,T> findFun, Function<T,K> keyExtractor){
        for(var document : documents){
            var docInDB = findFun.apply(keyExtractor.apply(document));
            if (docInDB == null) log.info("PRELOADING: new " + repo.save(document));
            else log.info("PRELOADING: already exists " + docInDB);
        }
    }

    @Bean
    public CommandLineRunner initRolesDatabase(RoleRepository repo){
        return args -> {
            Set<Role> roles = Stream.of(Role.ADMIN, Role.USER).map(Role::new).collect(Collectors.toSet());
            create(roles, repo, repo::findByRole, Role::getRole);

            /*try {
                repo.save(new Role(Role.USER));
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
            }*/
        };
    }

    @Bean
    public CommandLineRunner initUsersDatabase(UserRepository userRepo, RoleRepository roleRepo) {
        return args -> {
            Set<User> users = Set.of(
                    new User(
                            "admin",
                            "{bcrypt}$2y$12$wbZM9rIYHXQ2JxCYsFuRbOJZxTNFYf2yj9t9uZ/xymlIvVZvIxyEK", // пароль 100
                            Stream.of(Role.ADMIN, Role.USER).map(roleRepo::findByRole).collect(Collectors.toSet()),
                            "Admin",
                            true
                    ),
                    new User(
                            "user",
                            "{bcrypt}$2y$12$wbZM9rIYHXQ2JxCYsFuRbOJZxTNFYf2yj9t9uZ/xymlIvVZvIxyEK", // пароль 100
                            Stream.of(Role.USER).map(roleRepo::findByRole).collect(Collectors.toSet()),
                            "User",
                            true
                    ),
                    new User(
                            "mechanic",
                            "{bcrypt}$2y$12$wbZM9rIYHXQ2JxCYsFuRbOJZxTNFYf2yj9t9uZ/xymlIvVZvIxyEK", // пароль 100
                            Stream.of(Role.USER).map(roleRepo::findByRole).collect(Collectors.toSet()),
                            "Mech Man",
                            true
                    )
            );
            create(users, userRepo, userRepo::findByLogin, User::getLogin);
        };
    }

    @Bean
    public CommandLineRunner initServicesDatabase(ServiceRepository serviceRepo) {
        return args -> {
            byte[] img1 = new byte[0];
            byte[] img2 = new byte[0];
            byte[] img6 = new byte[0];
            try(var fis1 = new FileInputStream(PathProvider.getImage("univirsal.jpg").toFile());
                var fis2 = new FileInputStream(PathProvider.getImage("homemgtsru_1 (2).jpg").toFile());
                var fis6 = new FileInputStream(PathProvider.getImage("chto-luchshe-remont-materinskoj-platy-noutbuka-ili-ee-zamena-1536x1025.jpg").toFile())){
                img1 = fis1.readAllBytes();
                img2 = fis2.readAllBytes();
                img6 = fis6.readAllBytes();
            }
            //byte[] img1 = new FileInputStream(PathProvider.getImage("univirsal.jpg").getPath()).readAllBytes();
            Set<Service> services = Set.of(
                    new Service(
                            "First service",
                            img1,
                            "First service description",
                            "First service price"
                    ),
                    new Service(
                            "Second service",
                            img2,
                            "Second service description",
                            "Second service price"
                    ),
                    new Service(
                            "3rd service",
                            new byte[0],
                            "3rd service description",
                            "3rd service price"
                    ),
                    new Service(
                            "4th service",
                            new byte[0],
                            "4th service description",
                            "4th service price"
                    ),
                    new Service(
                            "5th service",
                            new byte[0],
                            "5th service description",
                            "5th service price"
                    ),
                    new Service(
                            "6th service",
                            img6,
                            "6th service description",
                            "6th service price"
                    ),
                    new Service(
                            "7th service",
                            new byte[0],
                            "7th service description",
                            "7th service price"
                    )
            );
            create(services, serviceRepo, serviceRepo::findByTitle, Service::getTitle);
        };
    }


}
