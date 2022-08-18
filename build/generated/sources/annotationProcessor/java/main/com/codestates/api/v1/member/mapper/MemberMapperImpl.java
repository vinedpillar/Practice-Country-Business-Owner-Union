package com.codestates.api.v1.member.mapper;

import com.codestates.api.v1.member.dto.MemberDto.Post;
import com.codestates.api.v1.member.dto.MemberDto.Response;
import com.codestates.api.v1.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-18T16:47:40+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostToMember(Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member member = new Member();

        member.setUserId( requestBody.getUserId() );
        member.setName( requestBody.getName() );
        member.setGender( requestBody.getGender() );
        member.setCompanyName( requestBody.getCompanyName() );
        member.setCompanyRegistrationNumber( requestBody.getCompanyRegistrationNumber() );
        member.setCompanyTypeId( requestBody.getCompanyTypeId() );
        member.setCompanyLocationId( requestBody.getCompanyLocationId() );

        return member;
    }

    @Override
    public Response memberToMemberResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        Long memberId = null;
        String userId = null;
        String name = null;
        String gender = null;
        String companyName = null;
        String companyRegistrationNumber = null;
        int companyTypeId = 0;
        int companyLocationId = 0;

        memberId = member.getMemberId();
        userId = member.getUserId();
        name = member.getName();
        gender = member.getGender();
        companyName = member.getCompanyName();
        companyRegistrationNumber = member.getCompanyRegistrationNumber();
        companyTypeId = member.getCompanyTypeId();
        companyLocationId = member.getCompanyLocationId();

        Response response = new Response( memberId, userId, name, gender, companyName, companyRegistrationNumber, companyTypeId, companyLocationId );

        return response;
    }

    @Override
    public List<Response> membersToMemberResponses(List<Member> members) {
        if ( members == null ) {
            return null;
        }

        List<Response> list = new ArrayList<Response>( members.size() );
        for ( Member member : members ) {
            list.add( memberToMemberResponse( member ) );
        }

        return list;
    }
}
