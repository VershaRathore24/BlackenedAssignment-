package com.example.assignment.controller;

import com.example.assignment.entity.Comment;
import com.example.assignment.entity.Post;
import com.example.assignment.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody Post post) {

        return postService.createPost(post);
    }

    @PostMapping("/{postId}/comments")
    public Comment addComment(
            @PathVariable Long postId,
            @RequestBody Comment comment,
            @RequestParam(defaultValue = "false") boolean isBot
    ) {

        return postService.addComment(postId, comment, isBot);
    }

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {

        return postService.likePost(postId);
    }
}