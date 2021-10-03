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

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long question_id;
	private char question_type;
	@ManyToOne
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;
	
	public Question(long question_id, char question_type) {
		this.question_id = question_id;
		this.question_type = question_type;
	}
}
