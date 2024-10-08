package com.audition.web;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import com.audition.service.AuditionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = AuditionController.class)
@AutoConfigureMockMvc
public class AuditionControllerTest {

    private static final String PATH_POSTS_END_POINT = "/posts";
    private static final String PATH_COMMENTS_END_POINT = "/comments";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditionService auditionService;

    @Test
    public void testGetPostById404NotFound() throws Exception {
        String postIdStr = "100000";
        String title = "Test post title";
        final String requestURI = PATH_POSTS_END_POINT + "/" + postIdStr;

        Mockito.when(auditionService.getPostById(postIdStr)).thenThrow(SystemException.class);

        mockMvc.perform(get(requestURI))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    public void testGetPostById200OK() throws Exception {
        final Integer postId = 1;
        final String postIdStr = "1";
        final String title = "Test post title";
        final String requestURI = PATH_POSTS_END_POINT + "/" + postIdStr;

        AuditionPost auditionPost = new AuditionPost();
        auditionPost.setId(postId);
        auditionPost.setTitle(title);
        auditionPost.setBody("Sample post message body");
        auditionPost.setUserId(1);

        Mockito.when(auditionService.getPostById(postIdStr)).thenReturn(auditionPost);

        mockMvc.perform(get(requestURI))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.title", is(title)))
            .andDo(print());
    }

    @Test
    public void testGetCommentsByPostIdAsPathParam200OK() throws Exception {
        final Integer postId = 1;
        final String postIdStr = "1";
        final String title = "Test comment";
        final String requestURI = PATH_POSTS_END_POINT + "/" + postId + "/comments";

        AuditionPostComment auditionPostComment1 = new AuditionPostComment();
        auditionPostComment1.setId(postId);
        auditionPostComment1.setEmail("test1@test.com");
        auditionPostComment1.setBody("Sample post message body 1");
        auditionPostComment1.setPostId(1);

        AuditionPostComment auditionPostComment2 = new AuditionPostComment();
        auditionPostComment2.setId(postId);
        auditionPostComment2.setEmail("test2@test.com");
        auditionPostComment2.setBody("Sample post message body 2");
        auditionPostComment2.setPostId(1);

        List<AuditionPostComment> commentsList = new ArrayList<>();
        commentsList.add(auditionPostComment1);
        commentsList.add(auditionPostComment2);

        Mockito.when(auditionService.getPostCommentsUsingPostId(postIdStr)).thenReturn(commentsList);

        mockMvc.perform(get(requestURI))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].email", is("test1@test.com")))
            .andExpect(jsonPath("$[1].email", is("test2@test.com")))
            .andDo(print());
    }

    @Test
    public void testGetCommentsByPostIdAsRequestParam200OK() throws Exception {
        final Integer postId = 1;
        final String postIdStr = "1";
        final String title = "Test comment";
        final String requestURI = PATH_COMMENTS_END_POINT + "?postId=" + postId;

        AuditionPostComment auditionPostComment1 = new AuditionPostComment();
        auditionPostComment1.setId(postId);
        auditionPostComment1.setEmail("test1@test.com");
        auditionPostComment1.setBody("Sample post message body 1");
        auditionPostComment1.setPostId(1);

        AuditionPostComment auditionPostComment2 = new AuditionPostComment();
        auditionPostComment2.setId(postId);
        auditionPostComment2.setEmail("test2@test.com");
        auditionPostComment2.setBody("Sample post message body 2");
        auditionPostComment2.setPostId(1);

        List<AuditionPostComment> commentsList = new ArrayList<>();
        commentsList.add(auditionPostComment1);
        commentsList.add(auditionPostComment2);

        Mockito.when(auditionService.getPostCommentsUsingPostId(postIdStr)).thenReturn(commentsList);

        mockMvc.perform(get(requestURI))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].email", is("test1@test.com")))
            .andExpect(jsonPath("$[1].email", is("test2@test.com")))
            .andDo(print());
    }

}
