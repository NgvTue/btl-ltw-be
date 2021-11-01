/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import configuration.FileStorageProperties;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import model.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.PostRepository;
import repository.TagRepositotry;
import tokenAuthen.JwtTokenUtil;

/**
 *
 * @author tvthag
 */
@Service
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags/")
public class TagService {
    @Autowired
    private  TagRepositotry tagRepo;
    @GetMapping("/getAllTags")
    public ResponseEntity getAllPosts() throws SQLException {
        ArrayList<String> tags = tagRepo.getAllTags();
        return ResponseEntity.ok().body(tags);
    }
}
