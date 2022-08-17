package com.codestates.restdocs;

import com.codestates.api.v1.member.controller.MemberController;
import com.codestates.api.v1.member.dto.MemberDto;
import com.codestates.api.v1.member.entity.Member;
import com.codestates.api.v1.member.entity.MemberStatus;
import com.codestates.api.v1.member.mapper.MemberMapper;
import com.codestates.api.v1.member.service.MemberService;
import com.codestates.helper.StubData;
import com.google.gson.Gson;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.codestates.util.ApiDocumentUtils.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.util.ApiDocumentUtils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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

    public void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post(
            "hgd1443",
            "홍길동",
            'M',
            "홀길동 컴퍼니",
            "314-12-435321",
            001,
            001
        );
        String content = gson.toJson(post);

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

        given(mapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/v1/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );


        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.userId").value(post.getUserId()))
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.gender").value(post.getGender()))
                .andExpect(jsonPath("$.data.companyName").value(post.getCompanyName()))
                .andExpect(jsonPath("$.data.companyRegistrationNumber").value(post.getCompanyRegistrationNumber()))
                .andExpect(jsonPath("$.data.companyTypeId").value(post.getCompanyTypeId()))
                .andExpect(jsonPath("$.data.companyLocationId").value(post.getCompanyLocationId()))
                .andDo(document("post-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
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
                                        fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태")
                                )
                        )
                ));

    }


    public void patchMemberTest() throws Exception {
        // given
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
        given(memberService.updateMember(Mockito.any(Member.class))).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/v1/members/{member-id}", memberId)
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
}
