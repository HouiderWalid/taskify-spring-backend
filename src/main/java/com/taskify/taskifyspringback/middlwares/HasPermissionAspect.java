package com.taskify.taskifyspringback.middlwares;

import java.util.ArrayList;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.taskify.taskifyspringback.models.controller.Response;
import com.taskify.taskifyspringback.models.entities.Permission;
import com.taskify.taskifyspringback.models.entities.User;
import com.taskify.taskifyspringback.models.implementations.TaskifyUserDetails;

@Aspect
@Component
@EnableAspectJAutoProxy
public class HasPermissionAspect {

    @Around("@annotation(hasPermission)")
    public Object checkPermission(ProceedingJoinPoint pjp, HasPermission hasPermission) throws Throwable {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new Response(404, new ArrayList<>(), "You\'re not authenticated !");
        }

        TaskifyUserDetails taskifyUserDetails = (TaskifyUserDetails) authentication.getPrincipal();
        User user = taskifyUserDetails.getUser();
        Set<Permission> permissions = user.getPermissions();
        if (permissions.stream().noneMatch(p -> p.getName().equals(hasPermission.permissionName()))) {
            return new Response(404, new ArrayList<>(), "You don\'t have permission to request this resource !");
        }

        return pjp.proceed();
    }
}
