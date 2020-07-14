package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    //就是只单单定义接口继承MongoRepository
    //写好模型类参数和id类型的参数就好，直接使用MongoRepository封住好的方法

    //自定义查询（查询名称有固定开头根据字段名）
    CmsPage findByPageName(String name);

    //根据页面名称、站点Id、页面webpath查询是页面
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
