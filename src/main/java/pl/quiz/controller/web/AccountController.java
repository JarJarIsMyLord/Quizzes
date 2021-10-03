package pl.quiz.controller.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.quiz.model.Stats;
import pl.quiz.model.User;
import pl.quiz.repository.StatsRepository;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	private User currentUser;
	private StatsRepository statsRepo;

	public AccountController(User currentUser, StatsRepository statsRepo) {
		this.currentUser = currentUser;
		this.statsRepo = statsRepo;
	}
	
	@GetMapping
	public String getPage(Model model) {
		List<Stats> stats = statsRepo.findAllByUserId(currentUser.getId()).get();
		model.addAttribute("username", currentUser.getUsername());
		model.addAttribute("stats", stats);
		Integer allCorrectAnswers = 0;
		Integer allWrongAnswers = 0;
		for (Stats stat : stats) {
			allCorrectAnswers += stat.getCorrectAnswers();
			allWrongAnswers += stat.getWrongAnswers();
		}
		model.addAttribute("allCorrectAnswers", allCorrectAnswers);
		model.addAttribute("allWrongAnswers", allWrongAnswers);
		return "account";
	}
	
	@GetMapping("/logout")
	public String logout() {
		currentUser.setUser(new User());
		return "redirect:/login";
	}
}
