package pl.quiz.controller.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.quiz.model.Question;
import pl.quiz.model.Quiz;
import pl.quiz.model.ShortQuestion;
import pl.quiz.model.User;
import pl.quiz.model.WritingQuestion;
import pl.quiz.repository.QuestionRepository;
import pl.quiz.repository.QuizRepository;
import pl.quiz.repository.ShortQuestionRepository;
import pl.quiz.repository.WritingQuestionRepository;

@Controller
@RequestMapping("/add")
public class AddQuizController {

	private QuizRepository quizRepo;
	private ShortQuestionRepository shortQuestionRepo;
	private WritingQuestionRepository writingQuestionRepo;
	private QuestionRepository questionRepo;
	private User currentUser;
	private Quiz quiz;
	private String title;
	private String description;
	private ArrayList<Question> questions;
	private ArrayList<WritingQuestion> wQuestions;
	private ArrayList<ShortQuestion> shQuestions;
	private ArrayList<Character> order;
	
	@Autowired
	public AddQuizController(QuizRepository quizRepo, ShortQuestionRepository shortQuestionRepo,
			WritingQuestionRepository writingQuestionRepo, QuestionRepository questionRepo, User currentUser) {
		this.quizRepo = quizRepo;
		this.shortQuestionRepo = shortQuestionRepo;
		this.writingQuestionRepo = writingQuestionRepo;
		this.questionRepo = questionRepo;
		this.currentUser = currentUser;
		questions = new ArrayList<>();
		wQuestions = new ArrayList<>();
		shQuestions = new ArrayList<>();
		order = new ArrayList<>();
	}

	@GetMapping
	public String show() {
		return "add";
	}
	
	@GetMapping("/writing")
	public String showWriting(){
		return "writing";
	}
	
	@GetMapping("/short")
	public String showShort() {
		return "short";
	}
	
	@PostMapping("/submit")
	public String submit() {
		wQuestions.forEach(writingQuestionRepo::save);
		shQuestions.forEach(shortQuestionRepo::save);
		int w = 0;
		int s = 0;
		for (Character character : order) {
			switch (character) {
			case 'w': {
				questions.add(new Question(wQuestions.get(w).getId(), 'W'));
				w++;
				break;
			}
			case 's':
				questions.add(new Question(shQuestions.get(s).getId(), 'S'));
				s++;
				break;
			}
		}
		
		quiz = new Quiz(title, description, order.size(), questions, currentUser.getUsername());
		quizRepo.save(quiz);
		questions.forEach(q -> q.setQuiz(quiz));
		questions.forEach(questionRepo::save);
		return "redirect:/";
	}
	
	@PostMapping
	public String addTitle(String title, String description) {
		this.title = title;
		this.description = description;
		return "redirect:/add/short";
	}
	
	@PostMapping("/writing")
	public String addWritingQuestion(String question, String answer) {
		wQuestions.add(new WritingQuestion(question, answer));
		order.add('w');
		return "writing";
	}
	
	@PostMapping("/short")
	public String addShortQuestion(String question, String a, String b, String c, char answer) {
		shQuestions.add(new ShortQuestion(question, a, b, c, answer));
		order.add('s');
		return "short";
	}
}
