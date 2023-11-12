package com.xmz.bi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xmz.bi.model.dto.post.PostQueryRequest;
import com.xmz.bi.model.entity.Post;

import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 帖子服务测试
 *
 * @author <a href="https://github.com/xmzdog/xmzbi-backend">程序员xmz</a>
 * 
 */
@SpringBootTest
class PostServiceTest {

    @Resource
    private PostService postService;

    @Test
    void searchFromEs() {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setUserId(1L);
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        Assertions.assertNotNull(postPage);
    }

}