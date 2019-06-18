package com.xudasong.service.springcloudservice.service.Impl;

import com.xudasong.service.springcloudservice.model.MongodbTest;
import com.xudasong.service.springcloudservice.service.IMongodbTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MongodbTestServiceImp implements IMongodbTestService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveTest(MongodbTest mongodbTest) {

        mongoTemplate.save(mongodbTest);

    }

    @Override
    public MongodbTest findTest(String key, Object object) {
        Query query = new Query(Criteria.where(key).is(object));
        MongodbTest mongodbTest = mongoTemplate.findOne(query, MongodbTest.class);
        return mongodbTest;
    }

    @Override
    public void updateTest(MongodbTest mongodbTest) {

        Query query = new Query(Criteria.where("id").is(mongodbTest.getId()));
        Update update = new Update().set("name", mongodbTest.getName()).set("price", mongodbTest.getPrice());
        mongoTemplate.updateFirst(query, update, MongodbTest.class);

    }

    @Override
    public void deleteTes(String key, Object object) {

        Query query = new Query(Criteria.where(key).is(object));
        mongoTemplate.remove(query, MongodbTest.class);

    }
}
