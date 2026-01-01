package com.openclassrooms.mddapi.post;

import com.openclassrooms.mddapi.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findBySubjectIn(Set<Subject> subscribedSubjects);
}