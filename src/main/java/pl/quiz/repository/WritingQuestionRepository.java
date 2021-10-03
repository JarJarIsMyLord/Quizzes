package pl.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.quiz.model.WritingQuestion;

@Repository
public interface WritingQuestionRepository extends JpaRepository<WritingQuestion, Long>{

}
