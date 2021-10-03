package pl.quiz.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ShortQuestion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String question;
	private String a;
	private String b;
	private String c;
	private char answer;
	
	public ShortQuestion(String question, String a, String b, String c, char answer) {
		this.question = question;
		this.a = a;
		this.b = b;
		this.c = c;
		this.answer = answer;
	}
	
	public String getAnswer(char answer) {
		switch (answer){
		case 'A': return a;
		case 'B': return b;
		case 'C': return c;
		}
		return "";
	}
}
