package com.audition.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditionPostComment {

    private int postId;
    private int id;
    private String email;
    private String body;

}
