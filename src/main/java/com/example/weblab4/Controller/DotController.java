package com.example.weblab4.Controller;

import com.example.weblab4.AService;
import com.example.weblab4.Repository.DotRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.example.weblab4.Database.Dot;

import java.util.List;

@RestController
public class DotController {
    private final DotRepository dotRepository;
    private final AService authService;
    @Autowired
    public DotController(DotRepository dotRepository, AService authService) {
        this.dotRepository = dotRepository;
        this.authService = authService;
    }
    @PostMapping("/api/dot")
    public void addDot(@RequestBody Dot dot, @RequestHeader("Authorization") String authorization) {
        String owner = authService.check(authorization);
        dot.setDotStatus();
        dot.setOwner(owner);
        dotRepository.save(dot);
    }


    @PostMapping("/api/dots")
    public List<Dot> getDotsByOwner(@RequestHeader("Authorization") String authorization) {
        String owner = authService.check(authorization);
        return dotRepository.getDotsByOwner(owner);
    }
}
