package org.psy.demo.web

import org.psy.demo.app.main.MainController
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.psy.demo.app.main.usecase.GetMainUseCase
import org.psy.demo.app.main.usecase.GetNotiDataUseCase
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.junit.jupiter.api.BeforeEach
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder


@ExtendWith(MockKExtension::class, SpringExtension::class, RestDocumentationExtension::class)
@WebMvcTest(MainController::class)
@AutoConfigureRestDocs
@ActiveProfiles("local")
class MainControllerTest{


    @TestConfiguration
    class Config {

        @Bean
        fun getMainUseCase() = mockk<GetMainUseCase>()

        @Bean
        fun getNotiUseCase() = mockk<GetNotiDataUseCase>()
    }

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc


    @Autowired
    @MockK
    private lateinit var getMainUseCase: GetMainUseCase
    @Autowired
    @MockK
    private lateinit var getNotiUseCase: GetNotiDataUseCase

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    //공통 에러 템플릿도 정의해야하는데....
    // 세션이 만료되었습니다 < 이거


    /*@Test
    @DisplayName("홈 메인 조회 성공 했을 때 - 본인")
    fun `getHomeMain_success`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every {
            getMainUseCase.getMainPopupList(
                any()
            )
        } returns MainMockDataSetting.getMainPopupList()
        every {
            getMainUseCase.getMainBannerList(
                any()
            )
        } returns MainMockDataSetting.getMainBannerList()
        every {
            getMainUseCase.getMainCategoryList(
            )
        } returns MainMockDataSetting.getMainCategoryList()
        every {
            getMainUseCase.getMainPhrase()
        } returns MainMockDataSetting.getPhraseCode()
        every {
            getMainUseCase.getBestPostList(
                any()
            )
        } returns MainMockDataSetting.getBestPosts()

        every {
            getMainUseCase.getMainProWriterWithGoodsList(
            )
        } returns MainMockDataSetting.getProWirter()
        every {
            getMainUseCase.getMainGoodsManagingWriterGoods(
            )
        } returns MainMockDataSetting.getGoodsManagingWriterGoods()
        every {
            getMainUseCase.getManageTagList(any())
        } returns MainMockDataSetting.getRecommendTag()
        every {
            getMainUseCase.getMainChallengeList(
            )
        } returns MainMockDataSetting.getMainChallengeList()
        every {
            getNotiUseCase.getMainNotiData(
                any()
            )
        } returns MainMockDataSetting.getNotiCountData()


        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/main/home",
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                tagSummary= DefaultRestDocsUtil.Summary("ㄹㅇㄴㄹㄴㅇㅇ", "ㅇㄴㄹ", "ㅋㅋ"),
                parameters= listOf(),
                responseFieldDescriptors= FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    "data.popup.slides[]" type ARRAY means "팝업 슬라이드",
                    *FieldDescriptorSub.getPopupResponseData("data.popup.slides[]"),
                    *FieldDescriptorSub.defaultMetaData("data.popup."),

                    "data.popup.slides[]" type ARRAY means "배너 슬라이드",
                    *FieldDescriptorSub.getBannerResponseData("data.banner.slides[]"),
                    *FieldDescriptorSub.defaultMetaData("data.banner."),

                    "data.categoryList[]" type ARRAY means "카테고리 리스트",
                    *FieldDescriptorSub.getCodeData("data.categoryList[]"),

                    "data.phrase[]" type ARRAY means "오스꾸 phrase 리스트",
                    *FieldDescriptorSub.getPhraseResponseData("data.phrase[]"),

                    "data.best" type OBJECT means "베스트 포스트 리스트",
                    "data.best.posts[]" type ARRAY means "베스트 포스트 리스트",
                    *FieldDescriptorSub.getBestPostResponseData("data.best.posts[]"),
                    *FieldDescriptorSub.defaultMetaData("data.best."),

                    "data.proWriter[]" type ARRAY means "프로작가 리스트",
                    *FieldDescriptorSub.getMainWriterResponseData("data.proWriter[]"),

                    "data.goodsManagingWriterGoods[]" type ARRAY means "프로작가 리스트",
                    *FieldDescriptorSub.goodsManagingWriterGoods("data.goodsManagingWriterGoods[]"),

                    "data.recommendTag[]" type ARRAY means "태그 리스트",
                    *FieldDescriptorSub.recomendTag("data.recommendTag[]"),

                    "data.challenge[]" type ARRAY means "챌린지 리스트",

                    "data.notiCountData" type OBJECT means "알림 카운트 데이터",
                    *FieldDescriptorSub.getNotiDataResponseData("data.notiCountData"),

                    "log" type STRING means "log",
                )
            )
        )






    }*/









}
