package com.hacheery.accountbe.controller;

import com.hacheery.accountbe.dto.ProjectRequestDTO;
import com.hacheery.accountbe.dto.ProjectResponseDTO;
import com.hacheery.accountbe.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO request) {
        return ResponseEntity.ok(projectService.createProject(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody ProjectRequestDTO request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(Map.of("message", "Project deleted successfully"));
    }
}
