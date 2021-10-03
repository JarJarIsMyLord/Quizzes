package pl.quiz.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import pl.quiz.repository.QuizRepository;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class HomeController {

	private QuizRepository quizRepo;

	@GetMapping
	public String home(Model model) {
		model.addAttribute("quizzes", quizRepo.findAll());
		return "index";
	}
}
