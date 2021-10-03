package pl.quiz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.quiz.model.Quiz;
import pl.quiz.model.Stats;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long>{
	public Optional<Stats> findByQuizAndUserId(Quiz quiz, long userId);
	public Optional<List<Stats>> findAllByUserId(long userId);
}
