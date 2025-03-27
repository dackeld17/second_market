package com.dcu.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/memberSignUp")
    String memberSignUp() {
        return "member/memberSignUp";
    }

    @PostMapping("/memberSignUp")
    String memberSignUp(String email, String password, String confirmPassword, String name, LocalDate birth, Model model) {
        Client member = new Client();
        //이메일 형식 맞는지 확인 @
        //null 값 허용x
        // 이메일 중복 확인
        if(memberService.clientFindByEmailIspresent(email)){
            model.addAttribute("error","이메일 중복됨");
            return "member/memberSignUp";
        }
        //비밀번호 일치여부 확인
        if(!password.equals(confirmPassword)){
            model.addAttribute("error","비밀번호 다름");
            return "member/memberSignUp";
        }
        //비밀번호 길이 확인 8글자 이상만 가능
        if(password.length() <8 ){
            model.addAttribute("error","비밀번호 8자 이상으로 입력하시오");
            return "member/memberSignUp";
        }
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        member.setBirth(birth);


        memberService.signUp(member);
        return "member/memberSignUp";
    }


    @GetMapping("/memberLogin")
    String memberLogin(){
        return "member/memberLogin";
    }

    @GetMapping("/memberPage")
    String memberPage(@AuthenticationPrincipal User user, Model model){
        Client client = memberService.findMemberByEmail(user.getUsername());
        model.addAttribute("client",client);
        return "member/memberPage";
    }




    // 유효성 검증 결과 메서드
    private void addAttributeValidationResult(Model model, String email, String name, LocalDate birth, String message) {
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("birth", birth);
        model.addAttribute("message", message);
    }

}








