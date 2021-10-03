package pl.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.quiz.model.Quiz;
import pl.quiz.model.StartedQuiz;

@Repository
public interface StartedQuizRepository extends JpaRepository<StartedQuiz, Long>{
	public Optional<StartedQuiz> findByQuizAndUserId(Quiz quiz, long userId);
}
