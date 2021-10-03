package pl.quiz.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Stats implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name="quiz_id")
	private Quiz quiz;
	private int correctAnswers;
	private int wrongAnswers;
	private long userId;
	
	public Stats(Quiz quiz, int correctAnswers, int wrongAnswers, long userId) {
		this.quiz = quiz;
		this.correctAnswers = correctAnswers;
		this.wrongAnswers = wrongAnswers;
		this.userId = userId;
	}

}
