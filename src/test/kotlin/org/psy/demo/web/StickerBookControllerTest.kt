package org.psy.demo.web


//import org.psy.kotlinhexagonaldemo.config.JwtAuthenticationToken
/*import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource*/
import com.epages.restdocs.apispec.FieldDescriptors
import org.psy.demo.common.*
import org.psy.demo.common.FieldDescriptorSub.errorMessageTemplate
import org.psy.demo.common.MockDataSetting.mockGoods
import org.psy.demo.common.MockDataSetting.mockManageTag
import org.psy.demo.common.MockDataSetting.mockStickerCalendarList
import org.psy.demo.common.MockDataSetting.mockStikerCalendar
import org.psy.demo.config.JwtService
import org.psy.demo.user.domain.AuthUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.psy.demo.app.sticker.controller.StickerBookController
import org.psy.demo.app.sticker.response.StickerResponse
import org.psy.demo.app.sticker.usecase.GetStickerUseCase
import org.psy.demo.app.sticker.usecase.StickingStickerUseCase
import org.psy.demo.core.sticker.domain.Sticker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
@WebMvcTest(StickerBookController::class)
@AutoConfigureRestDocs
@ActiveProfiles("local")
class StickerBookControllerTest {

    @TestConfiguration
    class Config {
        @Bean
        fun jwtService() = mockk<JwtService>()

        @Bean
        fun getStickerUseCase() = mockk<GetStickerUseCase>()

        @Bean
        fun stickingStickerUseCase() = mockk<StickingStickerUseCase>()
    }

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var getStickerUseCase: GetStickerUseCase

    @Autowired
    private lateinit var stickingStickerUseCase: StickingStickerUseCase

    data class claimDATa(
        val id: Long,
    )

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    @DisplayName("스티커북 리스트 조회 성공했을 때")
    fun `getStickerBook2`() {
        // Mock JWT token

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        /*// Create authorities and authentication token
        val authorities = listOf<GrantedAuthority>(SimpleGrantedAuthority("USER")) // Ensure roles match the expected format
        val authentication = JwtAuthenticationToken(mockClaims, authorities)

        // Mock the SecurityContextHolder to return the mocked authentication
        every { SecurityContextHolder.getContext().authentication } returns authentication*/


        // Mock 데이터 설정
        val stickers = StickerResponse(
            listOf(
                Sticker(
                    "1L",
                    "몬찌즈 스티커1",
                    "#몬찌즈",
                    "https://d16sraug4hsad1.cloudfront.net/STICKER/1.png",
                    "몬찌즈",
                    "몬찌즈"
                ),
                Sticker(
                    "2L",
                    "헤이모구 스티커1",
                    "#안녕",
                    "https://d16sraug4hsad1.cloudfront.net/STICKER/2.png",
                    "몬찌즈",
                    "몬찌즈"
                )
            ),
            MetaData.of(1, 10, 2)
        )

        val pageable: Pageable = PageRequest.of(0, 45)
        val page = PageImpl(
            listOf(
                Sticker(
                    "1L",
                    "몬찌즈 스티커1",
                    "#몬찌즈",
                    "https://d16sraug4hsad1.cloudfront.net/STICKER/1.png",
                    "몬찌즈",
                    "몬찌즈"
                ),
                Sticker(
                    "2L",
                    "헤이모구 스티커1",
                    "#안녕",
                    "https://d16sraug4hsad1.cloudfront.net/STICKER/2.png",
                    "몬찌즈",
                    "몬찌즈"
                )
            ), pageable, 2.toLong()
        )

        //pageParam을 명시적으로 줄 경우 이슈가 발생해서 any()로 주었는데 다른 방법이 있을까..?
        every { getStickerUseCase.getStickers("일상", any()) } returns page

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/sticker/list2",
                mapOf("offset" to "1", "size" to "9", "tagName" to "일상"),
                mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument2(
                DefaultRestDocsUtil.Summary("sticker-list", "스티커-sticker", "태그별 스티커 리스트 조회"),
                listOf(
                    "offset" type ParamterFieldType.NUMBER means "페이지 번호" isOptional true default 1,
                    "size" type ParamterFieldType.NUMBER means "페이지 사이즈" isOptional true default 9,
                    "tagName" type ParamterFieldType.STRING means "태그 이름"
                ),
                *FieldDescriptorSub.defaultData(),
                "data.stickers" type ARRAY means "스티커 리스트",
                "data.stickers[].id" type STRING means "스티커 ID",
                "data.stickers[].name" type STRING means "스티커 이름",
                "data.stickers[].tagName" type STRING means "스티커 태그 이름",
                "data.stickers[].imgUrl" type STRING means "이미지 URL",
                "data.stickers[].writerName" type STRING means "작가 이름",
                "data.stickers[].underLineColor" type STRING means "underLineColor 밑줄 컬러",
                "data.metadata" type OBJECT means "페이지 info",
                * FieldDescriptorSub.defaultMetaData("data."),
                "log" type STRING means "log" isOptional true
            )
        )

        verify { getStickerUseCase.getStickers("일상", any()) }
    }


    @Test
    @DisplayName("스티커북 안에 있는 태그리스트 조회 성공했을 때")
    fun `getStickerBookTag`() {
        // Mock JWT token

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every { getStickerUseCase.getManageTagBySticker(any()) } returns mockManageTag()

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/sticker/manageTags",
                mapOf("offset" to "1", "size" to "10"),
                mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument2(
                DefaultRestDocsUtil.Summary("sticker-manage-tag-list", "스티커-sticker", "스티커 북 태그 리스트 조회"),
                listOf(
                    "offset" type ParamterFieldType.NUMBER means "페이지 번호" isOptional true default 1,
                    "size" type ParamterFieldType.NUMBER means "페이지 사이즈" isOptional true default 10,
                ),
                *FieldDescriptorSub.defaultData(),
                "data.tags" type ARRAY means "태그 리스트",
                "data.tags[].id" type STRING means "태그 ID",
                "data.tags[].tagName" type STRING means "태그 이름",
                "log" type STRING means "log" isOptional true
            )
        )

    }

    @Test
    @DisplayName("스티커 붙이기 날짜별 상세 조회 성공했을 때")
    fun `getStickingSticker`() {
        // Mock JWT token

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every { getStickerUseCase.getCaledarUserStickersByDate(any(), any()) } returns mockStikerCalendar()

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/sticker/sticking/{date}",
                requestParam = mapOf(),
                mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                ),
                "2024-04-03"
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("sticker-sticking-sticker-get-detail", "스티커-sticker", "스티커 붙이기 날짜별 상세 조회"),
                pathParamter = listOf("date" type ParamterFieldType.STRING means "날짜"),
                parameters = listOf(
                    "offset" type ParamterFieldType.NUMBER means "페이지 번호" isOptional true default 1,
                    "size" type ParamterFieldType.NUMBER means "페이지 사이즈" isOptional true default 10,
                ),
                responseFieldDescriptors = FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),
                    "data.date" type NUMBER means "스티커 붙인 요일",
                    "data.fullDate" type STRING means "스티커 붙인 날짜 yyyy-MM-dd",

                    "data.userSticker" type OBJECT means "스티커 데이터" isOptional true,
                    "data.userSticker.id" type STRING means "붙인 유저 스티커 ID",
                    "data.userSticker.title" type STRING means "스티커 이름",
                    "data.userSticker.description" type STRING means "스티커 이름",
                    "data.userSticker.stickerId" type STRING means "붙인 스티커 id",
                    "data.userSticker.userId" type NUMBER means "스티커 소유자 Id",
                    "data.userSticker.date" type STRING means "붙인 스티커 날짜",
                    "data.userSticker.imgUrl" type STRING means "붙인 스티커 이미지 url",
                    "data.userSticker.tagName" type STRING means "붙인 스티커 태그 이름",
                    "data.userSticker.underLineColor" type STRING means "붙인 스티커 컬러값",

                    "log" type STRING means "log" isOptional true
                )
            )
        )

    }


    @Test
    @DisplayName("나의 스티커북 조회 성공 했을 때")
    fun `getMyCalendar`() {

        // Mock JWT token

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every { getStickerUseCase.getCalendarUserStickers(any(), any(), any()) } returns mockStickerCalendarList()

        every { getStickerUseCase.getCalendarGoods(any(), any(), any()) } returns mockGoods()


        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/sticker/calendar",
                requestParam=  mapOf("date" to "2024-04"),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                ),
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("sticker-get-calendar", "스티커-sticker", "스티커북 캘린더 조회"),
                parameters = listOf(
                    "date" type ParamterFieldType.STRING means "조회 년 월 yyyy-MM",
                ),
                responseFieldDescriptors = FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    "data.calendars" type ARRAY means "스티커북 캘린더 리스트",
                    *FieldDescriptorSub.stickerCalendarData("data.calendars[]"),

                    "data.goodsList" type ARRAY means "스티커북 캘린더 하단 굿즈리스트",
                    *FieldDescriptorSub.stickerGoodsOld("data.goodsList[]"),

                    "data.isShowPrizeButton" type BOOLEAN means "상품 추첨 버튼 표시 여부",

                    "log" type STRING means "log" isOptional true
                )
            )
        )




    }

    @Test
    @DisplayName("나의 스티커북 조회시 입력값 미스일때 ")
    fun `getMyCalendar_fail`() {

        // Mock JWT token

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every { getStickerUseCase.getCalendarUserStickers(any(), any(), any()) } returns mockStickerCalendarList()

        every { getStickerUseCase.getCalendarGoods(any(), any(), any()) } returns mockGoods()


        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/sticker/calendar",
                requestParam = mapOf("monthYear" to "2024-04-03"),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                ),
            )
        ).andExpect(MockMvcResultMatchers.status().isBadRequest).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("sticker-get-calendar-fail", "스티커-sticker", "스티커북 캘린더 조회"),
                parameters = listOf(
                    "monthYear" type ParamterFieldType.STRING means "조회 년 월 yyyy-MM",
                ),
                responseFieldDescriptors = FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),
                    *errorMessageTemplate(),

                    "log" type STRING means "log" isOptional true
                )
            )
        )




    }

    @Test
    @DisplayName("스티커 붙이는 걸 성공했을 때")
    fun `stickingSticker`() {
        // Mock JWT claims

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every { stickingStickerUseCase.stickingSticker(any(), any()) } returns Unit

        mockMvc.perform(
            DefaultRestDocsUtil.postBuilder(
                "/api/CLIENT/v3.0/sticker/sticking",
                requestBody =
                    mapOf(
                        "stickerId" to "1",
                        "title" to "스마트폰 삼매경",
                        "description" to "오늘 하루는 참 참 힘들었다.",
                        "date" to "2024-05-05",
                        "underLineColor" to "#F8E1E5"
                    ),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                ),
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocumentByPost(
                DefaultRestDocsUtil.Summary("스티커 저장 성공", "스티커-sticker", "스티커북 스티커 저장"),
                requestBody = FieldDescriptors(
                    "stickerId" type STRING means "스티커 ID" ,
                    "title" type STRING means "스티커 제목",
                    "description" type STRING means "스티커 설명" isOptional true,
                    "date" type STRING means "날짜",
                    "underLineColor" type STRING means "밑줄 컬러"
                ),
                responseFieldDescriptors = FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    "data.message" type STRING means "메시지",

                    "log" type STRING means "log" isOptional true
                )
            )
        )

        // Verify that createStickerManagement was called
        verify(exactly = 1) { stickingStickerUseCase.stickingSticker(any(), any()) }


    }
    @Test
    @DisplayName("스티커 붙이는 걸 실패했을 때")
    fun `stickingSticker_fail`() {
        // Mock JWT claims
        val mockClaims: Claims = Jwts.claims()
        mockClaims["userId"] = "1"
        mockClaims["userName"] = "test"

        every { jwtService.decodeJwt(any()) } returns mockClaims

        every { stickingStickerUseCase.stickingSticker(any(), any()) } returns Unit

        mockMvc.perform(
            DefaultRestDocsUtil.postBuilder(
                "/api/CLIENT/v3.0/sticker/sticking",
                requestBody =
                    mapOf(
                        "title" to "스마트폰 삼매경",
                        "description" to "오늘 하루는 참 참 힘들었다.",
                        "date" to "2024-05-05",
                        "underLineColor" to "#F8E1E5"
                    ),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                ),
            )
        ).andExpect(MockMvcResultMatchers.status().isBadRequest).andDo(
            DefaultRestDocsUtil.MockMvcDocumentByPost(
                DefaultRestDocsUtil.Summary("스티커 저장 실패-값을 빼먹음", "스티커-sticker", "스티커북 스티커 저장"),
                requestBody = FieldDescriptors(
                    "title" type STRING means "스티커 제목",
                    "description" type STRING means "스티커 설명",
                    "date" type STRING means "날짜",
                    "underLineColor" type STRING means "밑줄 컬러"
                ),
                responseFieldDescriptors = FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),
                    *errorMessageTemplate(),


                    "log" type STRING means "log" isOptional true
                )
            )
        )

        // Verify that createStickerManagement was called
//        verify(exactly = 1) { stickingStickerUseCase.stickingSticker(any(), any()) }


    }


    @Test
    @DisplayName("스티커 수정하는 걸 성공했을 때")
    fun `stickingStickerUpdate`() {
    }

}
