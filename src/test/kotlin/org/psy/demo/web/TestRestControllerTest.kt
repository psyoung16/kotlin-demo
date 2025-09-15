package org.psy.demo.web

import com.epages.restdocs.apispec.FieldDescriptors
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceDocumentation.resource
import com.epages.restdocs.apispec.ResourceSnippetParameters
import org.psy.demo.TestRestController
import org.psy.demo.common.*
import org.psy.demo.sticker.application.port.`in`.GetStickerUseCase
import org.psy.demo.sticker.domain.entity.Sticker
import org.psy.demo.user.domain.AuthUser
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
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
@WebMvcTest(TestRestController::class)
@AutoConfigureRestDocs
@ActiveProfiles("local")
class TestRestControllerTest {


    @TestConfiguration
    class Config {

        @Bean
        fun getStorageUIUseCase() = mockk<GetStickerUseCase>()

    }

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc


    @Autowired
    private lateinit var getStickerUseCase: GetStickerUseCase


    @BeforeEach
    internal fun setUp(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }


    @Test
    @DisplayName("스티커 단건 조회 성공")
    fun `getSticker`() {

        //header 세팅
        val authUser = AuthUser(AuthUser.AuthUserId.of("test"), "USER", "닉네임", "비밀번호", listOf("USER"))
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
        SecurityContextHolder.getContext().authentication = authentication
        // ./header 세팅

        //mocking
        every { getStickerUseCase.getSticker(any()) } returns Sticker(
            id = "1".toLong().toString(),
            name = "스티커",
            tagName = "태그",
            imgUrl = null,
            writerName = "작성자",
            underLineColor = "#000000",
        )

        //test
        mockMvc.perform(
            DefaultRestDocsUtil.reqeustBuilder(
                "/test/3",
                headers = mapOf(
                    "Authorization" to
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI0OTcsInJvbGUiOiJVU0VSIiwibmlja25hbWUiOiLrrLTrr7zsnbQxIiwiZGV2aWNlSWQiOjI1MzQyLCJpYXQiOjE3MTc1MTA1MjQsImV4cCI6MTcxODExNTMyNH0.vXZtjO1EhI3qtCSLb3p7lx6SS98ggYCd0XWwtVskkzA"
                )
            )
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    "getSticker",
                    preprocessRequest(prettyPrint()), //Request setting
                    preprocessResponse(prettyPrint()),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("tag")
                            .summary("summary")
                            .responseFields(
                                FieldDescriptors(
                                    fieldWithPath("id").description("id").type(JsonFieldType.STRING),
                                    fieldWithPath("name").description("name").type(JsonFieldType.STRING),
                                    fieldWithPath("tagName").description("tagName").type(JsonFieldType.STRING),
                                    fieldWithPath("imgUrl").description("imgUrl").optional().type(JsonFieldType.STRING),
                                    fieldWithPath("writerName").description("writerName").type(JsonFieldType.STRING),
                                    fieldWithPath("underLineColor").description("underLineColor").type(JsonFieldType.STRING),
                                )
                            )
                            .build()
                    )
                )
            )

    }
            /*.andExpect(MockMvcResultMatchers.status().isOk).andDo(
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
        )*/



}
