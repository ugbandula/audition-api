package com.audition.service;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import java.util.List;

import com.audition.model.AuditionPostComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditionService {

    @Autowired
    private transient AuditionIntegrationClient auditionIntegrationClient;

    public List<AuditionPost> getPosts() {
        return auditionIntegrationClient.getPosts();
    }

    public AuditionPost getPostById(final String postId) {
        return auditionIntegrationClient.getPostById(postId);
    }

    public List<AuditionPostComment> getPostCommentsUsingPostId(final String postId) {
        return auditionIntegrationClient.getCommentsForPostUsingPathParam(postId);
    }

    public List<AuditionPostComment> getCommentsForPostId(final String postId) {
        return auditionIntegrationClient.getCommentsForPostUsingRequestParam(postId);
    }

}
