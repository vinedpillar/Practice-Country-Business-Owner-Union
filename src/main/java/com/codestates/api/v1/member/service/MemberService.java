package com.codestates.api.v1.member.service;

import com.codestates.api.v1.member.entity.Member;
import com.codestates.api.v1.member.mapper.MemberMapper;
import com.codestates.api.v1.member.repository.MemberRepository;
import com.codestates.event.MemberRegistrationApplicationEvent;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member createMember(Member member) {
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }


    /*
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getUserId())
                .ifPresent(userId -> findMember.setUserId(userId));
        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getGender())
                .ifPresent(gender -> findMember.setGender(gender));
        Optional.ofNullable(member.getCompanyName())
                .ifPresent(companyName -> findMember.setCompanyName(companyName));
        Optional.ofNullable(member.getCompanyRegistrationNumber())
                .ifPresent(companyRegistrationNumber -> findMember.setCompanyRegistrationNumber(companyRegistrationNumber));
        Optional.ofNullable(member.getCompanyTypeId())
                .ifPresent(companyTypeId -> findMember.setCompanyTypeId(companyTypeId));
        Optional.ofNullable(member.getCompanyLocationId())
                .ifPresent(companyLocationId -> findMember.setCompanyLocationId(companyLocationId));

        return memberRepository.save(findMember);
    }
    */

    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    /*
    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findMember);
    }
    */

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }

}
