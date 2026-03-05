package com.hacheery.accountbe.service.impl;

import com.hacheery.accountbe.dto.ProjectRequestDTO;
import com.hacheery.accountbe.dto.ProjectResponseDTO;
import com.hacheery.accountbe.entity.Project;
import com.hacheery.accountbe.repository.ProjectRepository;
import com.hacheery.accountbe.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return mapToDTO(project);
    }

    @Override
    @Transactional
    public ProjectResponseDTO createProject(ProjectRequestDTO request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setCount(0);
        project.setLastUpdated(LocalDate.now());
        Project saved = projectRepository.save(project);
        return mapToDTO(saved);
    }

    @Override
    @Transactional
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        project.setName(request.getName());
        project.setLastUpdated(LocalDate.now());
        Project updated = projectRepository.save(project);
        return mapToDTO(updated);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        projectRepository.delete(project);
    }

    private ProjectResponseDTO mapToDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setCount(project.getCount());
        dto.setLastUpdated(project.getLastUpdated());
        return dto;
    }
}
