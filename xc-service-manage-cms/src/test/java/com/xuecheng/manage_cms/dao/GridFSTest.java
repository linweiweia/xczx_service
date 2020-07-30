package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * @author linweiwei
 * @version 1.0
 * @date 2020-07-27 14:35
 * @describe:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFSTest {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * 存文件到fs.chunks/fs.files集合中
     * @throws FileNotFoundException
     */
    @Test
    public void testStore() throws FileNotFoundException {
        File file = new File("D:\\index_banner.ftl");
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectId objectId = gridFsTemplate.store(fileInputStream, "index_banner.ftl");
        System.out.println(objectId);
    }

    /**
     * 从fs.chunks/fs中取
     */
    @Test
    public void testQuery() throws IOException {
        //获取文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5f1e7e948b4ac8410c2f2db6")));
        //打开一个下载流对象
        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建GridFsResource获取流
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, downloadStream);
        //从流中获取数据
        String file = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
        System.out.println(file);
    }


}
