package com.xuecheng.manage_cms.dao;


import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.service.CmsPageService;
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
public class CmsPageServiceTest {
    @Autowired
    CmsPageService cmsPageService;

    @Test
    public void getHtmlTest(){
        String html = cmsPageService.getPageHtml("5f1fd1198b4ac81ba0bbdb42");
        System.out.println(html);
    }
}
