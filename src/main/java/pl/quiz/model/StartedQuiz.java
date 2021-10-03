package pl.quiz.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class StartedQuiz implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name="quiz_id")
	private Quiz quiz;
	private long userId;
	private int question;
	private ArrayList<String> answers;
	
	public StartedQuiz(Quiz quiz, long userId, int question, ArrayList<String> answers) {
		this.quiz = quiz;
		this.userId = userId;
		this.question = question;
		this.answers = answers;
	}
	
	public void set(StartedQuiz startedQuiz) {
		this.quiz = startedQuiz.getQuiz();
		this.question = startedQuiz.getQuestion();
		this.answers = startedQuiz.getAnswers();
	}
}
