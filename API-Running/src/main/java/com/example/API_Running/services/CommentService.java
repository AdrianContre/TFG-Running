package com.example.API_Running.services;

import com.example.API_Running.dtos.CommentDTO;
import com.example.API_Running.dtos.CreateCommentDTO;
import com.example.API_Running.models.*;
import com.example.API_Running.repository.CommentRepository;
import com.example.API_Running.repository.TrainingProgressRepository;
import com.example.API_Running.repository.TrainingWeekRepository;
import com.example.API_Running.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TrainingWeekRepository trainingWeekRepository;
    private final UserRepository userRepository;
    private final TrainingProgressRepository trainingProgressRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, TrainingWeekRepository trainingWeekRepository, UserRepository userRepository, TrainingProgressRepository trainingProgressRepository) {
        this.commentRepository = commentRepository;
        this.trainingWeekRepository = trainingWeekRepository;
        this.userRepository = userRepository;
        this.trainingProgressRepository = trainingProgressRepository;
    }


    public ResponseEntity<Object> createComment(Long trainingWeekId, CreateCommentDTO createCommentDTO) {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), createCommentDTO.getUserId())) {
            data.put("error", "You can not write a comment on behalf of other user");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
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

        Optional<TrainingProgress> trainingProgress_query = this.trainingProgressRepository.findProgressByPlanAndRunner(createCommentDTO.getUserId(), tw.getTrainingPlan().getId());
        if ((!trainingProgress_query.isPresent()) && (!Objects.equals(userAuth.getId(),trainingWeek_query.get().getTrainingPlan().getCreator().getId()))) {
            data.put("error", "You can not send a comment on a plan that you are not enrolled or that you are not the creator");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        Optional<TrainingWeek> trainingWeek_query = this.trainingWeekRepository.findById(trainingWeekId);
        if (!trainingWeek_query.isPresent()) {
            data.put("error", "The training week does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Optional<TrainingProgress> progress_query = this.trainingProgressRepository.findProgressByPlanAndRunner(userAuth.getId(), trainingWeek_query.get().getTrainingPlan().getId());
        if ((!Objects.equals(userAuth.getId(),trainingWeek_query.get().getTrainingPlan().getCreator().getId())) && !(progress_query.isPresent())) {
            data.put("error", "You are not the creator of the plan or you are not enrolled to the plan");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
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
