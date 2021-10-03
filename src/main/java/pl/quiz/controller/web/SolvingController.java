package pl.quiz.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.quiz.model.Question;
import pl.quiz.model.Quiz;
import pl.quiz.model.ShortQuestion;
import pl.quiz.model.StartedQuiz;
import pl.quiz.model.Stats;
import pl.quiz.model.User;
import pl.quiz.model.WritingQuestion;
import pl.quiz.repository.QuizRepository;
import pl.quiz.repository.ShortQuestionRepository;
import pl.quiz.repository.StartedQuizRepository;
import pl.quiz.repository.StatsRepository;
import pl.quiz.repository.WritingQuestionRepository;

@Controller
@RequestMapping("/{id}")
public class SolvingController {
	
	private User currentUser;
	private ShortQuestionRepository shortQuestionRepo;
	private WritingQuestionRepository writingQuestionRepo;
	private QuizRepository quizRepo;
	private StartedQuizRepository startedQuizRepo;
	private StatsRepository statsRepo;
	private List<Question> questions;
	
	public SolvingController(User currentUser, ShortQuestionRepository shortQuestionRepo,
			WritingQuestionRepository writingQuestionRepo, QuizRepository quizRepo, StartedQuizRepository startedQuizzesRepo, StatsRepository statsRepo) {
		this.currentUser = currentUser;
		this.shortQuestionRepo = shortQuestionRepo;
		this.writingQuestionRepo = writingQuestionRepo;
		this.quizRepo = quizRepo;
		this.startedQuizRepo = startedQuizzesRepo;
		this.statsRepo = statsRepo;
	}

	@GetMapping("/{question}")
	public String showQuestion(Model model, @PathVariable long id, @PathVariable int question) {
		try {
			currentUser.setCurrentQuiz(quizRepo.findById(id).orElseThrow());
		}catch(NoSuchElementException e) {
			return "redirect:/";
		}
		if(currentUser.getStartedQuiz()==null || currentUser.getStartedQuiz().getQuiz().getId()!=id)
			currentUser.setStartedQuiz(startedQuizRepo.findByQuizAndUserId(currentUser.getCurrentQuiz(), currentUser.getId()).orElse(new StartedQuiz(currentUser.getCurrentQuiz(), currentUser.getId(), 1, new ArrayList<>())));
		int lastQuestion = currentUser.getStartedQuiz().getQuestion();
		if(question != lastQuestion)
			return "redirect:/"+currentUser.getCurrentQuiz().getId()+"/"+lastQuestion;
		questions = currentUser.getCurrentQuiz().getQuestions();
		Question q = questions.get(question-1);
		if(q.getQuestion_type()=='W') {
			WritingQuestion writingQuestion = writingQuestionRepo.findById(q.getQuestion_id()).get();
			model.addAttribute("question", writingQuestion.getQuestion());
			model.addAttribute("id", id);
			model.addAttribute("questionId", question);
			return "solve-writing";
		}else if(q.getQuestion_type()=='S') {
			ShortQuestion shortQuestion = shortQuestionRepo.findById(q.getQuestion_id()).get();
			model.addAttribute("question", shortQuestion.getQuestion());
			model.addAttribute("a", shortQuestion.getA());
			model.addAttribute("b", shortQuestion.getB());
			model.addAttribute("c", shortQuestion.getC());
			model.addAttribute("id", id);
			model.addAttribute("questionId", question);
			return "solve-short";
		}
		return "index";
	}
	
	@PostMapping("/{question}")
	public String getAnswer(@RequestParam String answer, @PathVariable long id, @PathVariable int question) {
		Quiz quiz = currentUser.getCurrentQuiz();
		StartedQuiz startedQuiz = currentUser.getStartedQuiz();
		startedQuiz.getAnswers().add(answer);
		question++;
		if(question<=questions.size()) {
			startedQuiz.setQuestion(question);
			startedQuizRepo.save(startedQuiz);
			return "redirect:/{id}/"+question;
		}else {
			Optional<StartedQuiz> dbStartedQuiz = startedQuizRepo.findByQuizAndUserId(quiz, currentUser.getId());
			if(dbStartedQuiz.isPresent())
				startedQuizRepo.deleteById(dbStartedQuiz.get().getId());
			return "redirect:/{id}/check";
		}
	}
	
	@GetMapping("/check")
	public String check(Model model) {
		Quiz quiz = currentUser.getCurrentQuiz();
		StartedQuiz startedQuiz = currentUser.getStartedQuiz();
		ArrayList<String> qst = new ArrayList<>();
		ArrayList<String> correctAnswers = new ArrayList<>();
		for (Question q : questions) {
			if(q.getQuestion_type()=='W') {
				WritingQuestion wQst = writingQuestionRepo.findById(q.getQuestion_id()).get();
				qst.add(wQst.getQuestion());
				correctAnswers.add(wQst.getAnswer());
			}else if(q.getQuestion_type()=='S') {
				ShortQuestion sQst = shortQuestionRepo.findById(q.getQuestion_id()).get();
				qst.add(sQst.getQuestion());
				correctAnswers.add(sQst.getAnswer(sQst.getAnswer()));
			}
		}
		ArrayList<String> userAnswers = startedQuiz.getAnswers();
		Stats stats = statsRepo.findByQuizAndUserId(quiz, currentUser.getId()).orElse(new Stats(quiz, 0, 0, currentUser.getId()));
		for (int i = 0; i < correctAnswers.size(); i++) {
			if(userAnswers.get(i).equals(correctAnswers.get(i)))
				stats.setCorrectAnswers(stats.getCorrectAnswers()+1);
			else
				stats.setWrongAnswers(stats.getWrongAnswers()+1);
		}
		statsRepo.save(stats);
		currentUser.setStartedQuiz(null);
		currentUser.setCurrentQuiz(null);
		model.addAttribute("questions", qst);
		model.addAttribute("answers", userAnswers);
		model.addAttribute("correctAnswers", correctAnswers);
		return "check";
	}
}
