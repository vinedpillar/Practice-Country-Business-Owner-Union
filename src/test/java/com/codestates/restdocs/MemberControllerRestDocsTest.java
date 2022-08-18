package com.codestates.restdocs;

import com.codestates.api.v1.member.controller.MemberController;
import com.codestates.api.v1.member.dto.MemberDto;
import com.codestates.api.v1.member.entity.Member;
import com.codestates.api.v1.member.mapper.MemberMapper;
import com.codestates.api.v1.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.codestates.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper mapper;
    @Autowired
    private Gson gson;

    @Test
    public void getMemberTest() throws Exception {
        // given
        /*
        MemberDto.Post post = new MemberDto.Post(
            "hgd1443",
            "홍길동",
            "M",
            "홀길동 컴퍼니",
            "314-12-435321",
            001,
            001
        );
        String postContent = gson.toJson(post);
        */

        MemberDto.Response responseDto = new MemberDto.Response(
                1L,
                "hgd1443",
                "홍길동",
                "M",
                "홀길동 컴퍼니",
                "314-12-435321",
                001,
                001
        );
        // String responseContent = gson.toJson(responseDto);

        given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);


        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/v1/members/{member-id}", 1)
                );


        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(responseDto.getName()))
                /*
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.data.gender").value(responseDto.getGender()))
                .andExpect(jsonPath("$.data.companyName").value(responseDto.getCompanyName()))
                .andExpect(jsonPath("$.data.companyRegistrationNumber").value(responseDto.getCompanyRegistrationNumber()))
                .andExpect(jsonPath("$.data.companyTypeId").value(responseDto.getCompanyTypeId()))
                .andExpect(jsonPath("$.data.companyLocationId").value(responseDto.getCompanyLocationId()))
                */
                .andDo(document("get-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        /*
                        requestFields(
                                List.of(
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("companyRegistrationNumber").type(JsonFieldType.STRING).description("사업자등록번호"),
                                        fieldWithPath("companyTypeId").type(JsonFieldType.STRING).description("회사 분류"),
                                        fieldWithPath("companyLocationId").type(JsonFieldType.STRING).description("회사 위치 번호")
                                )


                        ),
                        */
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.STRING).description("아이디"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("data.companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("data.companyRegistrationNumber").type(JsonFieldType.STRING).description("사업자등록번호"),
                                        fieldWithPath("data.companyTypeId").type(JsonFieldType.NUMBER).description("회사 분류"),
                                        fieldWithPath("data.companyLocationId").type(JsonFieldType.NUMBER).description("회사 위치 번호")
                                )
                        )
                ));

    }

    /*
    @Test
    public void getMembersTest() throws Exception {
        Member member_1 = new Member();
        member_1.setMemberId(1L);
        member_1.setUserId("hgd1443");
        member_1.setName("홍길동");
        member_1.setGender("M");
        member_1.setCompanyName("홀길동 컴퍼니");
        member_1.setCompanyRegistrationNumber("314-12-45321");
        member_1.setCompanyTypeId(001);
        member_1.setCompanyLocationId(001);

        Member member_2 = new Member();
        member_2.setMemberId(2L);
        member_2.setUserId("gur1443");
        member_2.setName("고을노");
        member_2.setGender("F");
        member_2.setCompanyName("고을노 컴퍼니");
        member_2.setCompanyRegistrationNumber("314-12-45321");
        member_2.setCompanyTypeId(002);
        member_2.setCompanyLocationId(002);

        MemberDto.Response responseDto_1 = new MemberDto.Response(
                1L,
                "hgd1443",
                "홍길동",
                "M",
                "홀길동 컴퍼니",
                "314-12-45321",
                001,
                001
                );

        MemberDto.Response responseDto_2 = new MemberDto.Response(
                2L,
                "gur1443",
                "고을노",
                "F",
                "고을노 컴퍼니",
                "432-12-32113",
                002,
                002
                );

        PageImpl<Member> members = new PageImpl<>(List.of(member_1, member_2),
                PageRequest.of(0, 2, Sort.by("memberId").descending()), 2);

        List<MemberDto.Response> responses = List.of(responseDto_1, responseDto_2);

        // given
        given(memberService.findMembers(Mockito.anyInt(), Mockito.anyInt())).willReturn(members);
        given(mapper.membersToMemberResponses(Mockito.anyList())).willReturn(responses);

        //when
        ResultActions actions =
                mockMvc
                        .perform(get("/v1/members")
                                .param("page", "1")
                                .param("size", "1")
                                .accept(MediaType.APPLICATION_JSON));


        //then
        actions.andExpect(status().isOk())
                .andDo(document(
                        "get-members",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data.userId").type(JsonFieldType.STRING).description("아이디"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("data.companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                fieldWithPath("data.companyRegistrationNumber").type(JsonFieldType.STRING).description("사업자등록번호"),
                                fieldWithPath("data.companyTypeId").type(JsonFieldType.NUMBER).description("회사 분류"),
                                fieldWithPath("data.companyLocationId").type(JsonFieldType.NUMBER).description("회사 위치 번호"),

                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 개수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("페이지 총 개수")
                        )
                ));


    }
    */

}




    /*
    @Test
    public void getMembersTest() throws Exception {
        // given
        long memberId = 1L;
        MemberDto.Patch patch = new MemberDto.Patch(
                1L,
                "hgd1443",
                "홍길동",
                'M',
                "홀길동 컴퍼니",
                "314-12-435321",
                001,
                001,
                MemberStatus.MEMBER_ACTIVE
        );
        String content = gson.toJson(patch);

        MemberDto.Response responseDto = new MemberDto.Response(
                1L,
                "hgd1443",
                "홍길동",
                'M',
                "홀길동 컴퍼니",
                "314-12-435321",
                001,
                001,
                MemberStatus.MEMBER_ACTIVE
        );

        given(mapper.memberPatchToMember(Mockito.any(MemberDto.Patch.class))).willReturn(new Member());
        given(memberService.findMembers(Mockito.any(Member.class))).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/v1/members{member-id}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );


        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(patch.getUserId()))
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andExpect(jsonPath("$.data.gender").value(patch.getGender()))
                .andExpect(jsonPath("$.data.companyName").value(patch.getCompanyName()))
                .andExpect(jsonPath("$.data.companyRegistrationNumber").value(patch.getCompanyRegistrationNumber()))
                .andExpect(jsonPath("$.data.companyTypeId").value(patch.getCompanyTypeId()))
                .andExpect(jsonPath("$.data.companyLocationId").value(patch.getCompanyLocationId()))
                .andExpect(jsonPath("$.data.memberStatus").value(patch.getMemberStatus().getStatus()))
                .andDo(document("patch-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                            parameterWithName("member-Id").description("회원 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디").optional(),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름").optional(),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별").optional(),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사 이름").optional(),
                                        fieldWithPath("companyRegistrationNumber").type(JsonFieldType.STRING).description("사업자등록번호"),
                                        fieldWithPath("companyTypeId").type(JsonFieldType.STRING).description("회사 분류").optional(),
                                        fieldWithPath("companyLocationId").type(JsonFieldType.STRING).description("회사 위치 번호").optional(),
                                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태: MEMBER_ACTIVE / MEMBER_SLEEP / MEMBER_QUIT").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.STRING).description("아이디"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("data.companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("data.companyRegistrationNumber").type(JsonFieldType.STRING).description("사업자등록번호"),
                                        fieldWithPath("data.companyTypeId").type(JsonFieldType.STRING).description("회사 분류"),
                                        fieldWithPath("data.companyLocationId").type(JsonFieldType.STRING).description("회사 위치 번호"),
                                        fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태: 활동 / 휴면 / 탈퇴")
                                )
                        )
                ));

    }
    */
