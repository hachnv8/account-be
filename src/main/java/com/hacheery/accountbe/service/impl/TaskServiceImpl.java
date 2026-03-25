package com.hacheery.accountbe.service.impl;

import com.hacheery.accountbe.entity.Task;
import com.hacheery.accountbe.repository.TaskRepository;
import com.hacheery.accountbe.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setTitle(taskDetails.getTitle());
            existingTask.setTaskdesc(taskDetails.getTaskdesc());
            existingTask.setTask(taskDetails.getTask());
            existingTask.setStatus(taskDetails.getStatus());
            existingTask.setAttachments(taskDetails.getAttachments());
            return taskRepository.save(existingTask);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
