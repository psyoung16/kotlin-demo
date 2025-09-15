package org.psy.demo.web

import com.epages.restdocs.apispec.FieldDescriptors
import org.psy.demo.common.*
import org.psy.demo.app.sdui.usecase.GetStorageUIUseCase
import org.psy.demo.app.sdui.translator.container.HeaderUI
import org.psy.demo.app.sdui.translator.container.SectionType
import org.psy.demo.user.domain.AuthUser
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.psy.demo.app.sdui.controller.StorageUIController
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
@WebMvcTest(StorageUIController::class)
@AutoConfigureRestDocs
@ActiveProfiles("local")
class StorageUIControllerTest {


    @TestConfiguration
    class Config {

        @Bean
        fun getStorageUIUseCase() = mockk<GetStorageUIUseCase>()

    }

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc


    @Autowired
    private lateinit var getStorageUIUseCase: GetStorageUIUseCase


    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    @DisplayName("보관함 탭 조회 성공했을 때")
    fun `getStorageUI_tab_success`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication


        every { getStorageUIUseCase.getStoargeTabs(any()) } returns MockDataSetting.getMockStorageTabs()

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/storage/tabs",
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("보관함 탭 조회 성공했을 때", "보관함-storage", "보관함 조회 - 탭"),
                listOf(),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    *FieldDescriptorSub.defaultSectionInfo("data", arrayOf(
                        "data.items[]" type ARRAY means "보관함-탭의 items",
                        *FieldDescriptorSub.communityTab("data.items[]")
                    )),

                    "log" type STRING means "log" isOptional true
                )
            )
        )
    }


    @Test
    @DisplayName("보관함 컨텐츠 조회 성공했을 때 - V_CONTENT_MIX_LIST")
    fun `getStorageUI_contents_success_mix_list`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        every { getStorageUIUseCase.getStorageItems( any(), any(), any() ) } returns MockDataSetting.getMockProductCompositorByCommunityContentsByType(SectionType.V_CONTENT_MIX_LIST,
            HeaderUI(
                "좋아요"
            )
        )

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/storage/contents",
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("보관함 탭 조회 성공했을 때 -  V_CONTENT_MIX_LIST ", "보관함-storage", "보관함 조회 - 컨텐츠"),
                listOf(),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    *FieldDescriptorSub.defaultSectionInfo("data", arrayOf(
                        "data.items[]" type ARRAY means "보관함-탭의 items",
                        *FieldDescriptorSub.myroomPost("data.items[]")
                    )),

                    "log" type STRING means "log" isOptional true
                )
            )
        )
    }

    @Test
    @DisplayName("보관함 컨텐츠 조회 성공했을 때 - V_CONTENT_IMAGE_PRICE_GRID")
    fun `getStorageUI_contents_success`() {

        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication


        every { getStorageUIUseCase.getStorageItems(any(), any(), any()) } returns MockDataSetting.getMockProductCompositorByGoods(SectionType.V_CONTENT_IMAGE_PRICE_GRID,
            HeaderUI(
                "찜한 상품"
            )
        )

        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/api/CLIENT/v3.0/storage/contents",
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk).andDo(
            DefaultRestDocsUtil.MockMvcDocument(
                DefaultRestDocsUtil.Summary("보관함 탭 조회 성공했을 때 - V_CONTENT_IMAGE_PRICE_GRID", "보관함-storage", "보관함 조회 - 컨텐츠"),
                listOf(),
                FieldDescriptors(
                    *FieldDescriptorSub.defaultData(),

                    *FieldDescriptorSub.defaultSectionInfo("data", arrayOf(
                        "data.items[]" type ARRAY means "보관함-contents의 items",
                        *FieldDescriptorSub.storageWishGoods("data.items[]")
                    )),

                    "log" type STRING means "log" isOptional true
                )
            )
        )
    }


}
