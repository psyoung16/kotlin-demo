package org.psy.demo.web

import com.epages.restdocs.apispec.FieldDescriptors
import org.psy.demo.common.*
import org.psy.demo.config.JwtService
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.psy.demo.app.sdui.controller.CommunityUIController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.psy.demo.app.sdui.usecase.GetCommunityUIMainUseCase
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.user.domain.AuthUser
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder


@ExtendWith(MockKExtension::class, SpringExtension::class, RestDocumentationExtension::class)
@WebMvcTest(CommunityUIController::class)
@AutoConfigureRestDocs
@ActiveProfiles("local")
class CommunityUIControllerTest {


    @TestConfiguration
    class Config {
        @Bean
        fun jwtService() = mockk<JwtService>()

        @Bean
        fun getCommunityMainUseCase() = mockk<GetCommunityUIMainUseCase>()

    }

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc


    @Qualifier("getCommunityMainUseCase")
    @Autowired
    private lateinit var getCommunityUIMainUseCase: GetCommunityUIMainUseCase


    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    @DisplayName("커뮤니티 메인 조회 성공했을 때")
    fun `getCommunityUIMain_success`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication


        every { getCommunityUIMainUseCase.getCommunityUITabs() } returns MockDataSetting.getMockTabProductCompositorByCommunity()

        every { getCommunityUIMainUseCase.getCommunityUIBanners(Tabs.TAB_ID.FEED) } returns MockDataSetting.getMockBannerProductCompositorByCommunity()

        every {
            getCommunityUIMainUseCase.getCommunityUICuration(
                any(),
                any()
            )
        } returns MockDataSetting.getMockProductCompositorByFeed()

        every { getCommunityUIMainUseCase.getCommunityUIFilters(Tabs.TAB_ID.FEED.name) } returns MockDataSetting.getMockFilterContainer()

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/community/main",
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("community-main", "커뮤니티-community", "커뮤니티 조회 - 메인"),
                listOf(
                    "tabId" type ParamterFieldType.STRING means "탭 아이디 3.1기준. tabs -> id로 던지기" isOptional true,
                ),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    "data.tabs" type ARRAY means "커뮤니티 상위 tab",
                    *FieldDescriptorSub.communityTab("data.tabs[]"),

                    "data.banners" type ARRAY means "커뮤니티 배너",
                    *FieldDescriptorSub.communityBanner("data.banners[]"),

                    "data.curations" type OBJECT means "커뮤니티 큐레이션 영역",
                    *FieldDescriptorSub.defaultSectionInfo(
                        "data.curations",
                        arrayOf(
                            "data.curations.items[]" type ARRAY means "커뮤니티 큐레이션 section items",
                            *FieldDescriptorSub.myroomFeed("data.curations.items[]")
                        )
                    ),
                    "data.filters" type OBJECT means "커뮤니티 필터",
                    *FieldDescriptorSub.communityFilters("data.filters"),

                    "log" type STRING means "log" isOptional true
                )
            )
        )


    }

    @Test
    @DisplayName("커뮤니티 컨텐츠 조회 성공했을 때")
    fun `getCommunityUIContents_success`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every { getCommunityUIMainUseCase.getCommunityUIContents(any()) } returns MockDataSetting.getMockProductCompositorByFeed()

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/community/contents",
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(

            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("community-contents", "커뮤니티-community", "커뮤니티 조회 - 하단 contents"),
                listOf(
                    "tabId" type ParamterFieldType.STRING means "탭 아이디 3.1기준. tabs -> id로 던지기" isOptional true,
                    "layoutIcon" type ParamterFieldType.STRING means "가져오려는 layoutIconType 1열보기 ex : V_CONTENT_IMAGE_GRID" isOptional true,
                    "postTags" type ParamterFieldType.STRING means "추천 태그 filters -> tags -> items.id 를 join 해서" isOptional true,
                    "styleTags" type ParamterFieldType.STRING means "스타일 태그 filters -> tags -> items.id 를 join 해서" isOptional true,
                    "page" type ParamterFieldType.NUMBER means "페이지 번호 1부터 시작" isOptional true default 1,
                    "size" type ParamterFieldType.NUMBER means "페이지 사이즈" isOptional true default 10,
                ),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    *FieldDescriptorSub.defaultSectionInfo("data", arrayOf(
                        "data.items[]" type ARRAY means "커뮤니티-하단 contents의 items",
                        *FieldDescriptorSub.myroomFeed("data.items[]")
                    )),

                    "log" type STRING means "log" isOptional true
                )
            )
        )


    }


}
