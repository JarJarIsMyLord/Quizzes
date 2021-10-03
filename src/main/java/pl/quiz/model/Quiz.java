package pl.quiz.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Quiz implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String description;
	private int questions_number;
	@OneToMany(mappedBy = "quiz")
	private List<Question> questions;
	private String author;
	@OneToMany(mappedBy="quiz")
	private List<Stats> stats;
	
	public Quiz(String title, String description, int questions_number, List<Question> questions, String author) {
		this.title = title;
		this.description = description;
		this.questions_number = questions_number;
		this.questions = questions;
		this.author = author;
	}
}
