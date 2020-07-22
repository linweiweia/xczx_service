package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author linweiwei
 * @version 1.0
 * @date 2020-07-22 10:46
 * @describe:
 */
@Repository
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {

}
