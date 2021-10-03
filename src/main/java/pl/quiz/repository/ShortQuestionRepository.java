package pl.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.quiz.model.ShortQuestion;

@Repository
public interface ShortQuestionRepository extends JpaRepository<ShortQuestion, Long>{
}
