package com.xuecheng.manage_cms.dao;


import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)//同样的包名就会去找ManageCmsApplication这个启动类
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void findAllTest(){
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    //分页测试
    @Test
    public void findPageTest(){
        int page = 0;   //从第0页开始
        int size = 10;
        PageRequest pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //测试SpringMongoDB的分页查询
    @Test
    public void updateTest(){
        //获取对象(optional是jdk1.8出现容器，用为标准化判空的)
        Optional<CmsPage> optional = cmsPageRepository.findById("5abefd525b05aa293098fca6");
        if (optional.isPresent()){
            //设置新值
            CmsPage cmsPage = optional.get();
            cmsPage.setPageAliase("test");
            //修改提交
            cmsPageRepository.save(cmsPage);
        }
    }


    //测试自定义查询（不用写sql就像Springdata一样）
    //名称要在接口在按规定写好
    @Test
    public void findByName(){
        CmsPage cmsPage = cmsPageRepository.findByPageName("index2.html");
        System.out.println(cmsPage);
    }

}
