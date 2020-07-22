package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.service.impl.CmsPageServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmsPageService implements CmsPageServiceImpl {

    @Autowired
    CmsPageRepository cmsPageRepository;

    /**
     * 自定义查询页面
     * @param page  当前页
     * @param size  每页大小
     * @param queryPageRequest  自定义查询类
     * @return
     */
    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null){
            queryPageRequest = new QueryPageRequest();
        }
        CmsPage cmsPage = new CmsPage();

        //精确查找(站点id)
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //精确查找（模板ID）
        if(StringUtils.isNotEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //模糊查找（要配合ExampleMatcher使用）
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //自定义条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //定义条件对象Example（就是把条件匹配器和实体关联起来）
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        //对外提供第一页为1
        if (page <= 0){
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 10;
        }
        PageRequest pageable = PageRequest.of(page, size);
        // 分页查询
        //Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageable);   //返回的是一个Page
        // 分页、条件查询这些都不用自己写SpringData封装好了
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example,pageable);   //返回的是一个Page
        QueryResult queryResult = new QueryResult();
        queryResult.setList(cmsPages.getContent());
        queryResult.setTotal(cmsPages.getTotalElements());
        //返回结果
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    /**
     * 添加新的页面
     * @param cmsPage
     * @return
     */
    public CmsPageResult add(CmsPage cmsPage) {
        //像参数异常cmsPage == null，在公共模块的ExceptionCatch中做同一处理这里不再处理.

        //判断要添加的页面是否已经存在
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 != null){
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        //正确执行添加操作
        cmsPage.setPageId(null);    //因为这个id是MongoDB自动生成的，所以页面不管有没有设置为空
        CmsPage save = cmsPageRepository.save(cmsPage);     //返回的save就是cmsPage
        CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
        return cmsPageResult;
    }

    /**
     * 根据ID查询页面
     * @param id
     * @return
     */
    public CmsPage findById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 更新页面
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult edit(String id, CmsPage cmsPage) {
        //先查询是否有这个页面
        CmsPage one = this.findById(id);
        if (one != null){
            //设置要更新的值
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //跟新到mongodb中
            cmsPageRepository.save(one);
            return new CmsPageResult(CommonCode.SUCCESS, one);
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    /**
     * 删除页面
     * @param id
     * @return
     */
    public ResponseResult delete(String id) {
        CmsPage cmsPage = this.findById(id);
        if (cmsPage != null){
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
