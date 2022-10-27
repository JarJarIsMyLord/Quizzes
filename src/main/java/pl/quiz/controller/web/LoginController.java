package pl.quiz.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import pl.quiz.model.User;
import pl.quiz.repository.UserRepository;

@Controller
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {
	
	private UserRepository userRepo;
	private User currentUser;
	
	@GetMapping
	public String show() {
		if(currentUser.getUsername()!=null)
			return "redirect:/";
		return "login";
	}
	
	@PostMapping(params = {"username", "password", "register"})
	public String register(Model model, @RequestParam String username, @RequestParam String password, @RequestParam String register) {
		if(userRepo.findByUsername(username)!=null) {
			model.addAttribute("register_error", true);
			return "login";
		}
		if(password.isBlank()) {
			model.addAttribute("password_error", true);
			return "login";
		}
		User user = new User(username, password);
		userRepo.save(user);
		currentUser.setUser(userRepo.findByUsername(username));
		return "redirect:/";
	}
	
	@PostMapping(params = {"username", "password", "login"})
	public String login(Model model, @RequestParam String username, @RequestParam String password, @RequestParam String login) {
		User user = userRepo.findByUsername(username);
		if(user == null || !(user.getPassword().equals(password))) {
			model.addAttribute("login_error", true);
			return "login";
		}
		currentUser.setUser(user);
		return "redirect:/";
	}
}
