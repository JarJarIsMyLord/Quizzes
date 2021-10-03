package pl.quiz.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.quiz.model.Question;
import pl.quiz.model.Quiz;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
	ArrayList<Question> findAllByQuiz(Quiz quiz);
}
