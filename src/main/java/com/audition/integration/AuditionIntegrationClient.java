package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionPost;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.audition.model.AuditionPostComment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuditionIntegrationClient {

    @Autowired
    private transient Environment env;

    // We can replace RestTemplate with WebClient as it has been deprecated. But no harm using in this application
    @Autowired
    private transient RestTemplate restTemplate;

    private final transient ObjectMapper objectMapper = new ObjectMapper();

    private final transient String extSvcUrl = env.getProperty("external.data.endpoint");

    public List<AuditionPost> getPosts() {
        // TODO make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts
        final ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(extSvcUrl+ "/posts", Object[].class);
        final Object[] objects = responseEntity.getBody();
        return Arrays.stream(objects)
                .map(object -> objectMapper.convertValue(object, AuditionPost.class))
                .collect(Collectors.toList());
    }

    public AuditionPost getPostById(final String id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            final String postUrl = extSvcUrl + "/posts/" + id;
            return restTemplate.getForObject(postUrl, AuditionPost.class);
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found", 404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                throw new SystemException("Unknown Error message", "Unknown Error", e.getStatusCode().value(), e);
            }
        }
    }

    // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.
    public List<AuditionPostComment> getCommentsForPostUsingPathParam(final String postId) {
        final String postUrl = extSvcUrl + "/posts/" + postId + "/comments";
        return readPostComments(postId, postUrl);
    }

    // TODO write a method. GET comments for a particular Post from https://jsonplaceholder.typicode.com/comments?postId={postId}.
    // The comments are a separate list that needs to be returned to the API consumers. Hint: this is not part of the AuditionPost pojo.
    public List<AuditionPostComment> getCommentsForPostUsingRequestParam(final String postId) {
        final String postUrl = extSvcUrl + "/comments?postId=" + postId;
        return readPostComments(postId, postUrl);
    }

    private List<AuditionPostComment> readPostComments(final String postId, final String postUrl) {
        try {
            final ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(postUrl, Object[].class);
            final Object[] objects = responseEntity.getBody();
            return Arrays.stream(objects)
                .map(object -> objectMapper.convertValue(object, AuditionPostComment.class))
                .collect(Collectors.toList());
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + postId, "Resource Not Found", 404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                throw new SystemException("Unknown Error message", "Unknown Error", e.getStatusCode().value(), e);
            }
        }
    }
}
