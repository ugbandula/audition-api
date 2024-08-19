package com.audition.web;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import com.audition.service.AuditionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuditionService.class)
@ExtendWith(MockitoExtension.class)
public class AuditionServiceTest {

    @Mock
    private AuditionIntegrationClient integrationClient;

    @InjectMocks
    private AuditionService auditionService;

    @Test
    public void testGetAllPosts() {
        AuditionPost auditionPost1 = new AuditionPost();
        auditionPost1.setId(1);
        auditionPost1.setTitle("Test title 1");
        auditionPost1.setBody("Sample post message body 1");
        auditionPost1.setUserId(1);

        AuditionPost auditionPost2 = new AuditionPost();
        auditionPost2.setId(2);
        auditionPost2.setTitle("Test title 2");
        auditionPost2.setBody("Sample post message body 2");
        auditionPost2.setUserId(1);

        List<AuditionPost> postsList = new ArrayList<>();
        postsList.add(auditionPost1);
        postsList.add(auditionPost2);

        when(integrationClient.getPosts()).thenReturn(postsList);

        List<AuditionPost> posts = auditionService.getPosts();

        // Assert
        Assertions.assertNotEquals(Collections.EMPTY_LIST, posts);
        Assertions.assertEquals(2, posts.size());
    }

    @Test
    public void testGetCommentsForPostUsingPathParam() {
        final String postId = "1";
        AuditionPostComment auditionPostComment1 = new AuditionPostComment();
        auditionPostComment1.setId(1);
        auditionPostComment1.setEmail("test1@test.com");
        auditionPostComment1.setBody("Sample post message body 1");
        auditionPostComment1.setPostId(1);

        AuditionPostComment auditionPostComment2 = new AuditionPostComment();
        auditionPostComment2.setId(2);
        auditionPostComment2.setEmail("test2@test.com");
        auditionPostComment2.setBody("Sample post message body 2");
        auditionPostComment2.setPostId(1);

        List<AuditionPostComment> postCommentsList = new ArrayList<>();
        postCommentsList.add(auditionPostComment1);
        postCommentsList.add(auditionPostComment2);

        when(integrationClient.getCommentsForPostUsingPathParam(postId)).thenReturn(postCommentsList);

        List<AuditionPostComment> postComments = auditionService.getPostCommentsUsingPostId(postId);

        // Assert
        Assertions.assertNotEquals(Collections.EMPTY_LIST, postComments);
        Assertions.assertEquals(2, postComments.size());
    }

    @Test
    public void testGetCommentsForPostUsingRequestParam() {
        final String postId = "1";
        AuditionPostComment auditionPostComment1 = new AuditionPostComment();
        auditionPostComment1.setId(1);
        auditionPostComment1.setEmail("test1@test.com");
        auditionPostComment1.setBody("Sample post message body 1");
        auditionPostComment1.setPostId(1);

        AuditionPostComment auditionPostComment2 = new AuditionPostComment();
        auditionPostComment2.setId(2);
        auditionPostComment2.setEmail("test2@test.com");
        auditionPostComment2.setBody("Sample post message body 2");
        auditionPostComment2.setPostId(1);

        List<AuditionPostComment> postCommentsList = new ArrayList<>();
        postCommentsList.add(auditionPostComment1);
        postCommentsList.add(auditionPostComment2);

        when(integrationClient.getCommentsForPostUsingRequestParam(postId)).thenReturn(postCommentsList);

        List<AuditionPostComment> postComments = auditionService.getCommentsForPostId(postId);

        // Assert
        Assertions.assertNotEquals(Collections.EMPTY_LIST, postComments);
        Assertions.assertEquals(2, postComments.size());
    }


}
