package ec.edu.espe.inscripciones.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditoriaAspect {

    @Around("execution(* ec.edu.espe.inscripciones.service.*.*(..))")
    public Object medirTiempo(ProceedingJoinPoint joinPoint)
            throws Throwable {

        long inicio = System.nanoTime();

        try {
            return joinPoint.proceed();
        } finally {
            long duracion = (System.nanoTime() - inicio) / 1_000_000;

            System.out.println("[AOP] "
                    + joinPoint.getSignature().getName()
                    + " ejecutado en " + duracion + " ms");
        }
    }
}
