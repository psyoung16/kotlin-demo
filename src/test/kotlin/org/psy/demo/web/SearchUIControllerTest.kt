package org.psy.demo.web

import com.epages.restdocs.apispec.FieldDescriptors
import org.psy.demo.common.*
import org.psy.demo.config.JwtService
import org.psy.demo.app.sdui.usecase.GetSearchUIUseCase
import org.psy.demo.user.domain.AuthUser
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.psy.demo.app.sdui.controller.SearchUIController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@ExtendWith(MockKExtension::class, SpringExtension::class, RestDocumentationExtension::class)
@WebMvcTest(SearchUIController::class)
@AutoConfigureRestDocs
@ActiveProfiles("local")
class SearchUIControllerTest {


    @TestConfiguration
    class Config {
        @Bean
        fun jwtService() = mockk<JwtService>()

        @Bean
        fun getSearchUIUseCase() = mockk<GetSearchUIUseCase>()
    }

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc


    @Autowired
    @MockK
    private lateinit var getSearchUIUseCase: GetSearchUIUseCase

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    //공통 에러 템플릿도 정의해야하는데....
    // 세션이 만료되었습니다 < 이거


    @Test
    @DisplayName("태그 기반 검색이 성공했을 때")
    fun `getSearchTagUI_success`() {


        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every {
            getSearchUIUseCase.getTagSearchResult(
                any(),any(),any()
            )
        } returns MockDataSetting.getMockProductCompositorByFeed()
        //

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/search/tag",
                mapOf("tagName" to "#test"),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(

            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("태그 기반 검색 성공", "검색-tag", "태그 기반 검색"),
                listOf(
                    "tagName" type ParamterFieldType.STRING means "태그 이름",
                    "page" type ParamterFieldType.NUMBER means "페이지 번호 1부터 시작" isOptional true,
                    "size" type ParamterFieldType.NUMBER means "페이지 사이즈" isOptional true,
                ),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    "data.items[]" type ARRAY means "contents 하단 items",
                    *FieldDescriptorSub.defaultSectionInfo(
                        "data", arrayOf(
                            *FieldDescriptorSub.mykkoommingFeed("data.items[]")
                        )
                    ),

                    "log" type STRING means "log" isOptional true
                )
            )
        )
    }
    @Test
    @DisplayName("스타일 태그 기반 검색이 성공했을 때")
    fun `getStyleTagUI_success`() {


        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every {
            getSearchUIUseCase.getStyleTagSearchResult(
                any(),any(),any()
            )
        } returns MockDataSetting.getMockProductCompositorByFeed()
        //

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/search/styleTag",
                mapOf("tagName" to "KITCH"),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(

            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("스타일 태그 기반 검색 성공", "검색-tag", "스타일 태그 기반 검색"),
                listOf(
                    "tagName" type ParamterFieldType.STRING means "스타일 태그 type (KITCH,\n" +
                            "    LOVELY_CUTE,\n" +
                            "    CHIC,\n" +
                            "    EMOTION,\n" +
                            "    DREAM,\n" +
                            "    VINTAGE,\n" +
                            "    NATURAL,\n" +
                            "    ANIMAL,\n" +
                            "    TYPO,\n" +
                            "    SIMPLE)",
                    "page" type ParamterFieldType.NUMBER means "페이지 번호 1부터 시작" isOptional true,
                    "size" type ParamterFieldType.NUMBER means "페이지 사이즈" isOptional true,
                ),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    "data.items[]" type ARRAY means "contents 하단 items",
                    *FieldDescriptorSub.defaultSectionInfo(
                        "data", arrayOf(
                            *FieldDescriptorSub.mykkoommingFeed("data.items[]")
                        )
                    ),

                    "log" type STRING means "log" isOptional true
                )
            )
        )
    }





}
