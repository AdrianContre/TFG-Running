package com.example.API_Running.services;

import com.example.API_Running.dtos.CommentDTO;
import com.example.API_Running.dtos.CreateCommentDTO;
import com.example.API_Running.models.Comment;
import com.example.API_Running.models.TrainingWeek;
import com.example.API_Running.models.User;
import com.example.API_Running.repository.CommentRepository;
import com.example.API_Running.repository.TrainingWeekRepository;
import com.example.API_Running.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TrainingWeekRepository trainingWeekRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, TrainingWeekRepository trainingWeekRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.trainingWeekRepository = trainingWeekRepository;
        this.userRepository = userRepository;
    }


    public ResponseEntity<Object> createComment(Long trainingWeekId, CreateCommentDTO createCommentDTO) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingWeek> trainingWeek_query = this.trainingWeekRepository.findById(trainingWeekId);
        if (!trainingWeek_query.isPresent()) {
            data.put("error", "The training week does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Long senderId = createCommentDTO.getUserId();
        Optional<User> user_query = this.userRepository.findById(senderId);
        if (!user_query.isPresent()) {
            data.put("error", "User does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        TrainingWeek tw = trainingWeek_query.get();
        User sender = user_query.get();
        String content = createCommentDTO.getContent();
        Comment comment = new Comment(content, sender, tw);
        Comment savedComment = this.commentRepository.save(comment);
        sender.addComment(comment);
        this.userRepository.save(sender);
        tw.addComment(savedComment);
        this.trainingWeekRepository.save(tw);
        data.put("data", "Comment created properly");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> getTrainingWeekComments(Long trainingWeekId) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingWeek> trainingWeek_query = this.trainingWeekRepository.findById(trainingWeekId);
        if (!trainingWeek_query.isPresent()) {
            data.put("error", "The training week does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        List<Comment> comments = this.commentRepository.findCommentsByTrainingWeekId(trainingWeekId);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        comments.forEach(comment -> {
            CommentDTO dto = new CommentDTO(comment);
            commentDTOS.add(dto);
        });
        data.put("data", commentDTOS);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
