package com.example.assignment.service;

import com.example.assignment.entity.Comment;
import com.example.assignment.entity.Post;
import com.example.assignment.repository.CommentRepository;
import com.example.assignment.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RedisService redisService;

    public Post createPost(Post post) {

        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public Comment addComment(Long postId, Comment comment, boolean isBot) {

        if (comment.getDepthLevel() > 20) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Depth level exceeded"
            );
        }

        if (isBot) {

            Long count = redisService.incrementBotCount(postId);

            if (count > 100) {
                throw new ResponseStatusException(
                        HttpStatus.TOO_MANY_REQUESTS,
                        "Bot limit exceeded"
                );
            }

            redisService.incrementViralityScore(postId, 1);

        } else {

            redisService.incrementViralityScore(postId, 50);
        }

        comment.setPostId(postId);

        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public String likePost(Long postId) {

        redisService.incrementViralityScore(postId, 20);

        return "Post liked successfully";
    }
}