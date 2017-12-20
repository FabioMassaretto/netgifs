package br.com.fiap.interceptor;

import br.com.fiap.controller.GifController;
import br.com.fiap.controller.response.GifVO;
import br.com.fiap.entity.User;
import br.com.fiap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class SessionManager implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if(session.getAttribute("user") == null) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User user = userService.findUserByEmail(authentication.getName());
            session.setAttribute("user", user);
            session.setAttribute("isAdmin", authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));

            if(user.getFavorites() != null && !user.getFavorites().isEmpty()){
                List<GifVO> gifVOS = new ArrayList<>();
                user.getFavorites().forEach(favorite -> {
                    gifVOS.add(new GifVO(MvcUriComponentsBuilder.fromMethodName(GifController.class,
                            "serveFile", favorite.getPath()).build().toString(), favorite.getName(), favorite.getDescription()));
                });
                session.setAttribute("favorites", gifVOS);
            }

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
