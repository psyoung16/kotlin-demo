package org.psy.demo.web

import com.epages.restdocs.apispec.FieldDescriptors
import org.psy.demo.common.*
import org.psy.demo.common.FieldDescriptorSub.errorMessageTemplate
import org.psy.demo.common.exception.CustomException
import org.psy.demo.common.exception.ErrorCode
import org.psy.demo.app.sdui.usecase.GetMyRoomUIUseCase
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.user.domain.AuthUser
import org.psy.demo.core.user.domain.User
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.psy.demo.app.sdui.controller.MyRoomUIController
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
@WebMvcTest(MyRoomUIController::class)
@AutoConfigureRestDocs
@ActiveProfiles("local")
class MyRoomUIControllerTest {


    @TestConfiguration
    class Config {

        @Bean
        fun getMyRoomUIUseCase() = mockk<GetMyRoomUIUseCase>()
    }

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc


    @Autowired
    @MockK
    private lateinit var getMyRoomUIUseCase: GetMyRoomUIUseCase

    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    //공통 에러 템플릿도 정의해야하는데....
    // 세션이 만료되었습니다 < 이거


    @Test
    @DisplayName("마이룸 메인 조회 성공 했을 때 - 본인")
    fun `getMyRoomUI_success`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every {
            getMyRoomUIUseCase.getMyRoomMyProfile(
                any(),
                any()
            )
        } returns MockDataSetting.getMockProductCompositorByProfile()
        every {
            getMyRoomUIUseCase.getMyRoomTodayMySticker(
                any(), any()
            )
        } returns MockDataSetting.getMockProductCompositorBySticker()
        every {
            getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                any(), any(), any(), any(), any()
            )
        } returns MockDataSetting.getMockProductCompositorByFeed()
        every {
            getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                any(), any(), any(), any(), any()
            )
        } returns MockDataSetting.getMockProductCompositorByPost()
        every {
            getMyRoomUIUseCase.getMyRoomMyBadge(
                any()
            )
        } returns MockDataSetting.getMockProductCompositorByBadge()

        //

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/myroom/main",
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(

            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("마이룸 조회 성공", "마이룸-myRoom", "마이룸 메인 조회 - 본인"),
                listOf(
                ),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    "data.profile" type OBJECT means "마이룸 프로필 static info",
                    *FieldDescriptorSub.myroomProfile("data.profile"),

                    "data.todayMySticker" type OBJECT means "마이룸 스티커 static info",
                    *FieldDescriptorSub.myroomSticker("data.todayMySticker"),

                    "data.recentUploadFeed" type OBJECT means "마이룸 피드1번째 section",
                    *FieldDescriptorSub.defaultSectionInfo(
                        "data.recentUploadFeed", arrayOf(
                            "data.recentUploadFeed.items[]" type ARRAY means "마이룸 피드1번째 section items",
                            *FieldDescriptorSub.myroomFeed("data.recentUploadFeed.items[]")
                        )
                    ),

                    "data.recentUploadPost" type OBJECT means "마이룸 피드2번째 section",
                    *FieldDescriptorSub.defaultSectionInfo(
                        "data.recentUploadPost", arrayOf(
                            "data.recentUploadPost.items[]" type ARRAY means "마이룸 피드2번째 section items",
                            *FieldDescriptorSub.myroomPost("data.recentUploadPost.items[]")
                        )
                    ),

                    "data.myBadge" type OBJECT means "마이룸 뱃지 section",
                    *FieldDescriptorSub.defaultSectionInfo(
                        "data.myBadge", arrayOf(
                            "data.myBadge.items[]" type ARRAY means "마이룸 뱃지 section items",
                            *FieldDescriptorSub.myroomMyBadge("data.myBadge.items[]")
                        )
                    ),

                    "log" type STRING means "log" isOptional true
                )
            )
        )
    }

    @Test
    @DisplayName("마이룸 메인 조회 성공 했을 때 - 타인")
    fun `getMyroomUI_other_success`() {


        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every {
            getMyRoomUIUseCase.getMyRoomMyProfile(
                any(), any()
            )
        } returns MockDataSetting.getMockProductCompositorByProfile()
        every {
            getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                any(), any(), any(), any(), any()
            )
        } returns MockDataSetting.getMockProductCompositorByFeed()
        every {
            getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                any(), any(), any(), any(), any()
            )
        } returns MockDataSetting.getMockProductCompositorByPost()
        every {
            getMyRoomUIUseCase.getMyRoomMyBadge(
                any()
            )
        } returns MockDataSetting.getMockProductCompositorByBadge()


        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/myroom/someoneMain",
                requestParam= mapOf(
                    "targetUserId" to "1"
                ),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )

        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("타인 마이룸 조회 성공", "마이룸-myRoom", "마이룸 메인 조회 - 타인"),
                listOf(
                    "targetUserId" type ParamterFieldType.STRING means "조회하려는 사용자 userId"
                ),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    "data.profile" type OBJECT means "마이룸 프로필 static info",
                    *FieldDescriptorSub.myroomProfile("data.profile"),

                    "data.recentUploadFeed" type OBJECT means "마이룸 피드1번째 section",
                    *FieldDescriptorSub.defaultSectionInfo(
                        "data.recentUploadFeed", arrayOf(
                            "data.recentUploadFeed.items[]" type ARRAY means "마이룸 피드1번째 section items",
                            *FieldDescriptorSub.myroomFeed("data.recentUploadFeed.items[]")
                        )
                    ),

                    "data.recentUploadPost" type OBJECT means "마이룸 피드2번째 section",
                    *FieldDescriptorSub.defaultSectionInfo(
                        "data.recentUploadPost", arrayOf(
                            "data.recentUploadPost.items[]" type ARRAY means "마이룸 피드2번째 section items",
                            *FieldDescriptorSub.myroomPost("data.recentUploadPost.items[]")
                        )
                    ),

                    "data.myBadge" type OBJECT means "마이룸 뱃지 section",
                    *FieldDescriptorSub.defaultSectionInfo(
                        "data.myBadge", arrayOf(
                            "data.myBadge.items[]" type ARRAY means "마이룸 뱃지 section items",
                            *FieldDescriptorSub.myroomMyBadge("data.myBadge.items[]")
                        )
                    ),

                    "log" type STRING means "log" isOptional true
                )
            )
        )

    }

    @Test
    @DisplayName("마이룸 메인 조회시 실패 했을 때 - 타인 - request value error")
    fun `getMyroomUI_other_validationError`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/myroom/someoneMain",
                mapOf(
                    "targetUserIddf" to "null"
                ),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isBadRequest).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary(
                    "[0002] validation 에러 공통",
                    "마이룸-myRoom",
                    "마이룸 메인 조회했을 때 - 타인 - 회원이 없는 경우"
                ),
                listOf(),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),
                    *errorMessageTemplate(),
                    "log" type STRING means "log" isOptional true
                )
            )
        )
    }

    @Test
    @DisplayName("마이룸 메인 조회시 실패 했을 때 - 타인 - 해당 회원이 없는 경우")
    fun `getMyroomUI_other_notfound`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication


        val userIdSlot1 = slot<User.UserId>()
        val userIdSlot2 = slot<User.UserId>()
        every {
            getMyRoomUIUseCase.getMyRoomMyProfile(
                capture(userIdSlot1),
                capture(userIdSlot2)
            )
        } throws CustomException(ErrorCode.USER_NOT_FOUND)

        // 값 주입
        try {
            getMyRoomUIUseCase.getMyRoomMyProfile(User.UserId("1"), User.UserId("null"))
        } catch (e: CustomException) {
            // Handle exception if needed
        }


        /*verify { getMyroomUIUseCase.getMyroomMyProfile(capture(userIdSlot1), capture(userIdSlot2)) }

        assertEquals("1", userIdSlot1.captured.id)
        assertEquals("null", userIdSlot2.captured.id)*/

        //다른 useCase 생략

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/myroom/someoneMain",
                mapOf(
                    "targetUserId" to "null"
                ),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )

        ).andExpect(MockMvcResultMatchers.status().isBadRequest).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary(
                    "[1002] 조회하려는 사용자가 없을 때",
                    "마이룸-myRoom",
                    "마이룸 메인 조회했을 때 - 타인 - 회원이 없는 경우"
                ),
                listOf(),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),
                    *errorMessageTemplate(),
                    "log" type STRING means "log" isOptional true
                )
            )
        )


    }

    @Test
    @DisplayName("마이룸 메인 조회시 실패 했을 때 - 타인 - 내가 차단한 회원일 경우")
    fun `getMyroomUI_other_block`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication


        val userIdSlot1 = slot<User.UserId>()
        val userIdSlot2 = slot<User.UserId>()
        every {
            getMyRoomUIUseCase.getMyRoomMyProfile(
                capture(userIdSlot1),
                capture(userIdSlot2)
            )
        } throws CustomException(ErrorCode.USER_ALREADY_BLOCKED)

        // 값 주입
        try {
            getMyRoomUIUseCase.getMyRoomMyProfile(User.UserId("1"), User.UserId("null"))
        } catch (e: CustomException) {
            // Handle exception if needed
        }


        /*verify { getMyroomUIUseCase.getMyroomMyProfile(capture(userIdSlot1), capture(userIdSlot2)) }

        assertEquals("1", userIdSlot1.captured.id)
        assertEquals("null", userIdSlot2.captured.id)*/

        //다른 useCase 생략

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/myroom/someoneMain",
                mapOf(
                    "targetUserId" to "null"
                ),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )

        ).andExpect(MockMvcResultMatchers.status().isBadRequest).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary(
                    "[1015] 조회하려는 사용자가 내가 차단한 회원일 때",
                    "마이룸-myRoom",
                    "마이룸 메인 조회했을 때 - 타인 - 해당 사용자를 내가 차단했을 때"
                ),
                listOf(),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),
                    *errorMessageTemplate(),
                    "log" type STRING means "log" isOptional true
                )
            )
        )


    }

    @Test
    @DisplayName("마이룸 피드(section1) 조회 성공 했을 때 - 본인&타인")
    fun `getMyroomUI_feedList_success`() {


        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication


        every {
            getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                any(), any(), any(), any(), any()
            )
        } returns MockDataSetting.getMockProductCompositorByFeed()


        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/myroom/feeds",
                mapOf(
                    "targetUserId" to "1"
                ),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(

            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("마이룸 첫번째 section 조회 성공", "마이룸-myRoom", "마이룸 피드 리스트 조회"),
                listOf(
                    "page" type ParamterFieldType.NUMBER means "페이지 번호 1부터 시작" isOptional true,
                    "size" type ParamterFieldType.NUMBER means "페이지 사이즈" isOptional true,
                    "targetUserId" type ParamterFieldType.STRING means "조회하려는 사용자 userId"
                ),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    "data.items[]" type ARRAY means "마이룸-게시글 상단의 tab items",
                    *FieldDescriptorSub.defaultSectionInfo(
                        "data", arrayOf(
                            *FieldDescriptorSub.myroomFeed("data.items[]")
                        )
                    ),

                    "log" type STRING means "log" isOptional true
                )
            )

        )

    }

    @Test
    @DisplayName("마이룸 게시글(section2) > 상단 탭 조회 성공 했을 때 - 본인&타인")
    fun `getMyroomUI_postTabs_success`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication


        every {
            getMyRoomUIUseCase.getMyRoomPostTabs(
                any()
            )
        } returns MockDataSetting.getMockPostTabs()

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/myroom/postTabs",
                mapOf(
                    "targetUserId" to "1"
                ),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("마이룸 두번째 section 탭 상단 조회 성공", "마이룸-myRoom", "마이룸 상단 게시글 탭 조회"),
                listOf(
                    "targetUserId" type ParamterFieldType.STRING means "조회하려는 사용자 userId"
                ),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    *FieldDescriptorSub.defaultSectionInfo(
                        "data", arrayOf(
                            "data.items[]" type ARRAY means "마이룸-게시글 상단의 items",
                            *FieldDescriptorSub.myroomPostTabs("data.items[]")
                        )
                    ),
                    "log" type STRING means "log" isOptional true
                )
            )

        )


    }

    @Test
    @DisplayName("마이룸 게시글(section2) 조회 성공 했을 때 - 본인&타인")
    fun `getMyRoomUI_postList_success`() {


        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication


        every {
            getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                any(),
                any(),
                any(),
                any(),
                Tabs.TAB_ID.WRITTEN_POST
            )
        } returns MockDataSetting.getMockProductCompositorByPost()


        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/myroom/posts",
                mapOf(
                    "targetUserId" to "1",
                    "tabId" to Tabs.TAB_ID.WRITTEN_POST.name
                ),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("마이룸 2번째 section 조회 성공", "마이룸-myRoom", "마이룸 게시글 리스트 조회"),
                listOf(
                    "targetUserId" type ParamterFieldType.STRING means "조회하려는 사용자 userId",
                    "tabId" type ParamterFieldType.STRING means "조회하려는 탭 id (WRITTEN_POST, RECENT_COMMENT)" isOptional true
                ),

                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    *FieldDescriptorSub.defaultSectionInfo(
                        "data", arrayOf(
                            "data.items[]" type ARRAY means "마이룸-게시글 하단의 items",
                            *FieldDescriptorSub.myroomPost("data.items[]")
                        )
                    ),

                    "log" type STRING means "log" isOptional true
                )
            )
        )

    }

    @Test
    @DisplayName("마이룸 스토리 리스트 조회 - 본인&타인")
    fun `getMyroomUI_storyList_success`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication


        every {
            getMyRoomUIUseCase.getMyRoomStorys(
                any()
            )
        } returns MockDataSetting.getMockProductCompositorByStory()

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/myroom/storys",
                mapOf(
                    "targetUserId" to "1"
                ),
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(

            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("mykkommming-storys", "마이룸-myRoom", "마이룸 스토리 리스트 조회"),
                listOf(
                    "targetUserId" type ParamterFieldType.STRING means "조회하려는 사용자 userId"
                ),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    *FieldDescriptorSub.defaultSectionInfo(
                        "data", arrayOf(
                            "data.items[]" type ARRAY means "마이룸-스토리 하단의 items",
                            *FieldDescriptorSub.myroomStory("data.items[]")
                        )
                    ),

                    "log" type STRING means "log" isOptional true
                )
            )
        )


    }


}
