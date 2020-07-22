package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author linweiwei
 * @version 1.0
 * @date 2020-07-22 10:57
 * @describe:
 */

@Service
public class CmsConfigService {

    @Autowired
    CmsConfigRepository cmsConfigRepository;

    /**
     * 根据id获取数据模型
     * @param id
     * @return
     */
    public CmsConfig getModel(String id) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if (optional.isPresent()){
            CmsConfig cmsConfig = optional.get();
            return cmsConfig;
        }
        return null;
    }
}
