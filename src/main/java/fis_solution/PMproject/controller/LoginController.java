package fis_solution.PMproject.controller;

import fis_solution.PMproject.argumentresolver.Login;
import fis_solution.PMproject.controller.dto.LoginRequest;
import fis_solution.PMproject.controller.dto.LoginResponse;
import fis_solution.PMproject.domain.management.Worker;
import fis_solution.PMproject.repository.WorkerRepository;
import fis_solution.PMproject.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//
//@RestController
//@RequiredArgsConstructor
//public class LoginController {
//
//    private final LoginService loginService;
//
//    @PostMapping("/login")
//    public String login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginRequest loginRequest){
//        //1. 로그인 api 전송시에 시점은 로그아웃이 되어 있는 상태이다. => 쿠키와 세션이 있다면 지워야 하는게 맞다
//        String Redirect_URL;
//        if(loginRequest == null){
//            return "failed";
//        }
//        if(request.getCookies() != null) {
//            loginService.remove_session_cookie(request.getCookies(), response);
//        }
//        try {
//            Worker worker = loginService.Login(loginRequest);
////            if (worker == null) {
////                Redirect_URL = "login";
////                return "failed";
////            }
//            HttpSession session = request.getSession();
//            // 세션 객체가 존재한다면 해당 세션 객체 초기화 진행
////            if (session != null) {
////                session.invalidate();
////            }
//            // DB에 worker 세션 정보 입력
//            loginService.create_worker_session(worker.getId(), session.getId());
//
//            Cookie cookie = new Cookie("session_id", session.getId());
//            Cookie username = new Cookie("username", worker.getW_name());
//            Cookie userid = new Cookie("userid", worker.getId().toString());
//            //쿠키 유효시간 한시간으로 설정 => 갱신시에 계속해서 1시간 적용
//            int cookie_life_time = 60 * 60;
//            cookie.setMaxAge(cookie_life_time);
//            username.setMaxAge(cookie_life_time * 10000);
//            userid.setMaxAge(cookie_life_time * 10000);
//            //cookie.setDomain("localhost");
//            cookie.setPath("/");
//            username.setPath("/");
//            userid.setPath("/");
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            response.addCookie(cookie);
//            response.addCookie(username);
//            response.addCookie(userid);
//            session.setAttribute("worker", worker);
//            return "success";
//        }
//        catch (Exception e){
//            System.out.println("exception = " + e);
//            Redirect_URL = "login";
//            return "failed";
//        }
//    }
//
//    @GetMapping("/logout")
//    public Boolean logout(HttpServletRequest request, HttpServletResponse response){
//        Cookie[] cookielist = request.getCookies();
//        Cookie session_cookie = null;
//        Cookie JSESSIONID = null;
//        Cookie username = null;
//        for(Cookie cookie : cookielist){
//            if(cookie.getName().equals("session_id")){
//                session_cookie = cookie;
//            }
//            if(cookie.getName().equals("JSESSIONID")){
//                JSESSIONID = cookie;
//            }
//            if(cookie.getName().equals("username")){
//                username = cookie;
//            }
//        }
//        System.out.println("session_cookie.getName() = " + session_cookie.getName());
//        session_cookie.setMaxAge(0);
//        username.setMaxAge(0);
//        JSESSIONID.setMaxAge(0);
//        response.addCookie(session_cookie);
//        response.addCookie(JSESSIONID);
//        response.addCookie(username);
//        return true;
//    }
//
//
//    // 각 컨트롤러에서 사용할 쿠키연장 == 로그인 연장 코드 쿠키가 메인이 되어야 한다.
//    /*
//        현승구 로그인 체킹 기능
//        check_login 에서 누가 로그인 했는지 반환 시켜줌
//     */
//    @GetMapping("/check_login")
//    public Boolean check_login(HttpServletRequest request, HttpServletResponse response, Worker worker) {
//        try{
//        HttpSession session = request.getSession();
//        Cookie[] cookielist = request.getCookies();
//        Cookie session_cookie = null;
//        Cookie username = null;
//        for (Cookie cookie : cookielist) {
//            if (cookie.getName().equals("session_id")) {
//                session_cookie = cookie;
//                System.out.println("session_cookie = " + session_cookie);
//            }
//            if (cookie.getName().equals("username")){
//                username = cookie;
//            }
//        }
//        //3. 그외에 쿠키가 없는 경우
//        if(username == null){
//            session.invalidate();
//            return false;
//        }
//
//        if (session_cookie == null) {
//            session.invalidate();
//            return false;
//        } else {
//            String session_cookie_value = session_cookie.getValue();
//            String session_id = session.getId();
//            if (session_cookie_value.equals(session_id)) {
//                worker = loginService.findWorkerBySessionId(session_cookie_value);
//                if (worker == null || !username.getValue().equals(worker.getNickname())) {
//                    System.out.println("username.getValue() = " + username.getValue());
//                    System.out.println("worker.getNickname() = " + worker.getNickname());
//                    System.out.println(" ================================================ ");
//                    session.invalidate();
//                    return false;
//                }
//                session_cookie.setMaxAge(60 * 60);
//                response.addCookie(session_cookie);
//                return true;
//            } else {
//                worker = loginService.findWorkerBySessionId(session_cookie_value);
//                if (worker != null) {
//                    // 새로운 세션아이디로 세션 쿠키를 업데이트 worker db에도 업데이트 진행
//                    loginService.update_worker_session(session_cookie_value, session_id);
//                    session_cookie.setValue(session_id);
//                    session_cookie.setMaxAge(60 * 60);
//                    session_cookie.setPath("/");
//                    response.addCookie(session_cookie);
//                    session.setAttribute("worker", worker);
//                    return true;
//                } else {
//                    // 해당 세션 쿠키를 가지고 있는 worker가 존재하지 않는다
//                    session_cookie.setMaxAge(0);
//                    session_cookie.setPath("/");
//                    response.addCookie(session_cookie);
//                    session.invalidate();
//                    return false;
//                }
//            }
//        }
//        //1. 세션 객체 존재하고 쿠키 session_id value가 현재 session_id와 일치하는 경우 => 쿠키의 생명주기만 늘려준다.
//        //2. 세션 객체 존재안하고 쿠키가 존재하는 경우 쿠키 값 확인 하고서 (worker에 있는 session_id 와 쿠키값을 비교) 현재 session_id를 쿠키에 업데이트(수정)하고 worker table에 업데이트 한다.
//    } catch (Exception e){
//            System.out.println(e);
//            return false;
//        }
//    }
//}

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {

        // 아이디 비번 확인
        Worker worker = loginService.login(loginRequest);
        if (worker == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        // 세션이 있으면 해당 세션 반환, 없으면 신규 생성
        HttpSession session = request.getSession();
        session.setAttribute("loginId", worker.getId());

        return new LoginResponse(worker.getId(), worker.getW_name(), worker.getAuthority());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @GetMapping("/userinfo")
    public LoginResponse workerInfo(@Login Long workerId) {
        LoginResponse loginResponse = loginService.workerInfo(workerId);
        return loginResponse;
    }
}
