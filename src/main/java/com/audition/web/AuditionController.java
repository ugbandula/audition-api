package com.audition.web;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import com.audition.service.AuditionService;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuditionController {

    @Autowired
    private transient AuditionService auditionService;

    // TODO Add a query param that allows data filtering. The intent of the filter is at developers discretion.
    /**
     * <p>Returns all or filtered posts using the provided User Id.</p>
     *
     * @param userId User Id
     * @return List of {@link AuditionPost} objects
     */
    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionPost> getPosts(@RequestParam final Optional<String> userId) {
        // TODO Add logic that filters response data based on the query param
        return userId.map(value -> auditionService.getPosts()
                .stream()
                .filter(s -> s.getUserId() == Integer.parseInt(value))
                .toList()).orElseGet(() -> auditionService.getPosts());
    }

    /**
     * <p>Returns the selected post with the given post Id.</p>
     *
     * @param postId Post Id
     * @return The retrieved post bearing the given Id
     */
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPosts(@PathVariable("id") final String postId) {
        // TODO Add input validation
        if (StringUtils.hasText(postId) && Integer.parseInt(postId) >= 0) {
            return auditionService.getPostById(postId);
        } else {
            throw new SystemException("Invalid Post Id found");
        }
    }

    // TODO Add additional methods to return comments for each post. Hint: Check https://jsonplaceholder.typicode.com/
    /**
     * <p>Returns the comments attached to a given post.</p>
     *
     * @param postId Post Id
     * @return List of {@link AuditionPostComment} objects
     */
    @RequestMapping(value = "/posts/{id}/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionPostComment> getPostComments(@PathVariable("id") final String postId) {
        // TODO Add input validation
        if (StringUtils.hasText(postId) && Integer.parseInt(postId) >= 0) {
            return auditionService.getPostCommentsUsingPostId(postId);
        } else {
            throw new SystemException("Invalid Post Id found");
        }
    }

    /**
     * <p>Returns the comments attached to a given post.</p>
     *
     * @param postId Post Id
     * @return List of {@link AuditionPostComment} objects
     */
    @RequestMapping(value = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionPostComment> getCommentsForPost(@RequestParam("postId") final String postId) {
        // TODO Add input validation
        if (StringUtils.hasText(postId) && Integer.parseInt(postId) >= 0) {
            return auditionService.getCommentsForPostId(postId);
        } else {
            throw new SystemException("Invalid Post Id found");
        }
    }
}
