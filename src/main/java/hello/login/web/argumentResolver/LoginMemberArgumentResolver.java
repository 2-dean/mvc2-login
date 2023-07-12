package hello.login.web.argumentResolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class); // 애노테이션붙어있나
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType; // 둘다 만족하면 resolveArgument 실행
    }


    // 컨트롤러 호출 직전에 호출되어 필요한 파라미터 정보를 생성해준다, 여기에서 반횐된 member 객체를 파라미터에 넣어줌
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolverArgument 실행"); // 실행됐을때 어떤값을 넣어줄지
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null ) {
            return null;
        }
        return session.getAttribute(SessionConst.LOGIN_MEMBER); // 있으면 Member 객체를 반환함
    }
}
