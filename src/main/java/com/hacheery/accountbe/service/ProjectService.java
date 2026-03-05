package com.hacheery.accountbe.service;

import com.hacheery.accountbe.dto.ProjectRequestDTO;
import com.hacheery.accountbe.dto.ProjectResponseDTO;

import java.util.List;

public interface ProjectService {
    List<ProjectResponseDTO> getAllProjects();
    ProjectResponseDTO getProjectById(Long id);
    ProjectResponseDTO createProject(ProjectRequestDTO request);
    ProjectResponseDTO updateProject(Long id, ProjectRequestDTO request);
    void deleteProject(Long id);
}
