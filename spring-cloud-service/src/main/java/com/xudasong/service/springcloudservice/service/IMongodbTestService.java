package com.xudasong.service.springcloudservice.service;

import com.xudasong.service.springcloudservice.model.MongodbTest;

/**
 * mongodb测试服务接口
 */
public interface IMongodbTestService {

    /**
     * 保存
     * @param mongodbTest
     */
    public void saveTest(MongodbTest mongodbTest);

    /**
     * 查找
     * @param
     * @return
     */
    public MongodbTest findTest(String key, Object value);

    /**
     * 更新
     * @param mongodbTest
     */
    public void updateTest(MongodbTest mongodbTest);

    /**
     * 删除
     * @param object
     */
    public void deleteTes(String key, Object object);

}
