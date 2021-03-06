package ajbc.learn.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import ajbc.learn.dao.DaoException;


@Aspect
@Component
public class MyCustomAspect {

//	public MyCustomAspect() {
//		System.out.println("hi");
//	}

	//this is an advice method
	//syntax: ? means optional
	//execution(modifier? return-type? method-pattern(argument-type , ...) exception-pattern?)
	@Before("execution(* learn.spring.dao.ProductDao.*(..))")
	public void logBefore(JoinPoint jp) {
		System.out.println("Before executing " + jp.getSignature().getName());
		System.out.println("Arguments are: " + Arrays.toString(jp.getArgs()));
	}


	@Around("execution(* learn.spring.dao.ProductDao.get*(Double, Double))")
	public Object swapInputs(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		Double min = (Double) args[0];
		Double max = (Double) args[1];
		if(min>max) {
			args = new Object[] {max, min};
		}
		
		return pjp.proceed(args);
	}
	

	@AfterThrowing(throwing = "t", pointcut = "execution(* learn.spring..*Dao.*(..))")
	public void convertToDaoException(Throwable t) throws DaoException {
		throw new DaoException(t);
	}
	
}
