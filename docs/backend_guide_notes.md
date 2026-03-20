# Hướng dẫn tạo API Backend cho Notes (Spring Boot)

Dưới đây là các file mã nguồn và cấu trúc Database gợi ý cho Java Spring Boot để có thể tương thích hoàn hảo với tính năng tạo Note mới trên Frontend. 

Mấu chốt vấn đề báo lỗi 401 trên Frontend là do Backend cần **mở khóa Security cho URL `/api/notes`** để nhận data từ UI.

## 1. Schema Cơ sơ dữ liệu (MySQL / PostgreSQL)
Frontend gửi xuống và cũng chờ đón một Object có các trường này:

```sql
CREATE TABLE notes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content LONGTEXT,               -- Kiểu văn bản dài vì lưu content dạn JSON text của EditorJS
    project_name VARCHAR(100),
    type VARCHAR(50),
    priority VARCHAR(50),
    status VARCHAR(50),
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## 2. NoteEntity.java
Ánh xạ các trường xuống Database bằng Hibernate JPA.

```java
package com.yourdomain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notes")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "project_name")
    private String projectName;

    private String type;
    private String priority;
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
```

## 3. NoteRepository.java

```java
package com.yourdomain.repository;

import com.yourdomain.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
}
```

## 4. NoteService.java

```java
package com.yourdomain.service;

import com.yourdomain.entity.NoteEntity;
import com.yourdomain.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll();
    }

    public NoteEntity createNote(NoteEntity note, String currentUsername) {
        note.setCreatedBy(currentUsername != null ? currentUsername : "System");
        return noteRepository.save(note);
    }
    
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
```

## 5. NoteController.java
Endpoint này chịu trách nhiệm nhận request `POST /api/notes` và `GET /api/notes`.

```java
package com.yourdomain.controller;

import com.yourdomain.entity.NoteEntity;
import com.yourdomain.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*") // Thêm origin cho localhost Angular của bạn
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteEntity>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @PostMapping
    public ResponseEntity<NoteEntity> createNote(@RequestBody NoteEntity note, Principal principal) {
        // Principal sẽ lấy tên của user đang truyền JWT Token lên
        String username = (principal != null) ? principal.getName() : "Hach NV";
        NoteEntity savedNote = noteService.createNote(note, username);
        return ResponseEntity.ok(savedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
```

## 6. SỬA LỖI VĂNG RA TRANG LOGIN (IMPORTANT)
Đây là cấu hình Security để không bị `401 Unauthorized` nữa. Tùy theo project của bạn dùng Spring Security config kiểu gì, hãy đảm bảo `/api/notes/**` được phân quyền cho phép truy cập.

```java
// Trong Security Config (SecurityFilterChain):
http.authorizeHttpRequests(auth -> auth
    // Nếu bạn có dùng jwt (tức là request gửi qua có token)
    .requestMatchers("/api/notes/**").authenticated() 
    
    // NẾU BẠN CHƯA CODE XONG PHẦN LOGIN BACKEND VÀ MUỐN TEST: Chỉnh thành permitAll để thông qua
    // .requestMatchers("/api/notes/**").permitAll() 
)
```
