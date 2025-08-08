package com.pmp.common.aop;

import com.pmp.application.service.auth.AuthService;
import com.pmp.common.exception.AccessDeniedException;
import com.pmp.common.util.TokenUtils;
import com.pmp.domain.model.auth.RequiresRoles;
import com.pmp.domain.model.auth.UserDO;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * 角色权限验证切面
 */
@Aspect
@Component
@RequiredArgsConstructor
public class RolePermissionAspect {

    private final AuthService authService;

    @Around("@annotation(com.pmp.domain.model.auth.RequiresRoles) || @within(com.pmp.domain.model.auth.RequiresRoles)")
    public Object checkRoles(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前用户的角色信息
        String userRole = getCurrentUserRole();

        // 获取方法或类上的注解
        RequiresRoles requiresRoles = getRequiresRolesAnnotation(joinPoint);
        if (requiresRoles == null) {
            return joinPoint.proceed();
        }

        // 验证角色权限
        if (!hasRequiredRoles(userRole, requiresRoles)) {
            throw new AccessDeniedException("权限不足，需要角色: " + Arrays.toString(requiresRoles.value()));
        }

        return joinPoint.proceed();
    }

    /**
     * 获取当前用户的角色信息
     */
    private String getCurrentUserRole() {
        // 从Security Context或JWT Token中获取用户角色
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 从请求中获取用户信息，从JWT token中解析
            String token = TokenUtils.extractAuthorizationHeader(request);
            UserDO user = authService.getUserByToken(token);
            return user.getRole();
        }

        // 返回默认角色
        return "guest";
    }

    /**
     * 获取注解信息
     */
    private RequiresRoles getRequiresRolesAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 先检查方法上的注解
        RequiresRoles methodAnnotation = method.getAnnotation(RequiresRoles.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }

        // 再检查类上的注解
        return method.getDeclaringClass().getAnnotation(RequiresRoles.class);
    }

    /**
     * 验证用户是否具备所需角色
     */
    private boolean hasRequiredRoles(String userRole, RequiresRoles requiresRoles) {
        String[] requiredRoles = requiresRoles.value();

        // 没有指定角色要求
        if (requiredRoles.length == 0) {
            return true;
        }

        // 用户具备所需角色
        for (String role : requiredRoles) {
            if (userRole.equals(role)) {
                return true;
            }
        }
        return false;
    }
}