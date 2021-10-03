package pl.quiz.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.quiz.model.User;

@Aspect
@Component
public class LoginAspect {
	
	@Autowired
	private User currentUser;
	
	@Around("execution(public * *(..)) && within(pl.quiz.controller.web.*) && @annotation(org.springframework.web.bind.annotation.GetMapping) && !target(pl.quiz.controller.web.LoginController)")
	public Object checkUser(ProceedingJoinPoint pjp) throws Throwable{
		if(currentUser.getUsername()==null) {
			return "redirect:/login";
		}else {
			return pjp.proceed();
		}
	}
}
