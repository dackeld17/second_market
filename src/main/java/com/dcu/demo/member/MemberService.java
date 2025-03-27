package com.dcu.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //이메일 중복확인 메서드
    boolean clientFindByEmailIspresent(String email){
        return memberRepository.findByEmail(email).isPresent();
    }
    //회원가입 메서드
    void signUp(Client member){
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }
    //이메일로 사용자 정보 조회하는 메서드
    Client findMemberByEmail(String email){
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 없음"));
    }

}
