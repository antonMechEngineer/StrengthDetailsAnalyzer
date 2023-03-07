package strengthDetailsAnalyzer.aop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@Slf4j
public class LoggerExceptionAspect {

    StringBuilder stringBuilder = new StringBuilder();

    @AfterThrowing(pointcut = "execution(* strengthDetailsAnalyzer.service.DetailService.write(..))" , throwing = "exception")
    private void afterError(JoinPoint joinPoint, Throwable exception){
        stringBuilder.append("Входные параметры метода service.write(detailTextData, detailNumericalData, specifiedData): ");
        List<Object> argsObject = Arrays.asList(joinPoint.getArgs());
        argsObject.stream().map(o -> o.toString() + "    ").forEach(stringBuilder::append);
        List<StackTraceElement> stackTraceElements = Arrays.asList(exception.getStackTrace());
        stackTraceElements.stream().map(stackTraceElement -> stackTraceElement.toString() + "\n\t").forEach(stringBuilder::append);
        log.error(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length());
    }
}
