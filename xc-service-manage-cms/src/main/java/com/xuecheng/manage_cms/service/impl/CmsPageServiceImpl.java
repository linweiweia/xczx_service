package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


public interface CmsPageServiceImpl {
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
}
