package org.psy.demo.common

import com.epages.restdocs.apispec.*
import com.epages.restdocs.apispec.ResourceDocumentation.headerWithName
import com.epages.restdocs.apispec.ResourceDocumentation.resource
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder


class DefaultRestDocsUtil {

    data class Summary(
        val identifier: String = "",
        val tag: String = "",
        val summary: String = "",
    )

    companion object {

        //entity를 받아서 request 토대로 document만들면 하나로 함수 합칠 수 있지 않을까?
        // REQUest는 하는데 MockMvcRestDocumentationWrapper에서 자꾸 누락이 되서...
        // 근데 그럴 경우 Fail의 경우 산정이 많이 어려운가

        fun reqeustBuilder(
            getUrl: String,
            requestParam: Map<String, String>? = mapOf(),
            headers: Map<String, String> = mapOf(),
            vararg urlVariables: Any
        ): MockHttpServletRequestBuilder {
            return RestDocumentationRequestBuilders
                .get(getUrl, *urlVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .apply {
                    requestParam?.forEach { (key, value) ->
                        this.param(key, value)
                    }
                    headers.forEach { (key, value) ->
                        this.header(key, value)
                    }
                }
        }


        fun postBuilder(
            url: String,
            requestBody: Map<String, String>? = null,
            headers: Map<String, String> = mapOf(),
            vararg urlVariables: Any
        ): MockHttpServletRequestBuilder {
            val objectMapper = ObjectMapper()

            return RestDocumentationRequestBuilders
                .post(url, *urlVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .apply {
                    requestBody?.let {
                        this.content(objectMapper.writeValueAsString(it))
                    }
                    headers.forEach { (key, value) ->
                        this.header(key, value)
                    }
                }
        }



        fun MockMvcDocument2(
            tagSummary: Summary,
            parameters: List<ParameterDescriptorWithType>,
            vararg fields: FieldDescriptor,
        ): RestDocumentationResultHandler {

            return MockMvcRestDocumentationWrapper.document(
                tagSummary.identifier,
                preprocessRequest(prettyPrint()), //Request setting
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag(tagSummary.tag)
                        .summary(tagSummary.summary)
                        .requestHeaders(
                            //default 라서 추가 안함
                            headerWithName("os").description("os 정보 I/A").optional(),
                            headerWithName("version").description("버전 정보 2.3.4").optional(),
                        )
                        .queryParameters(
                            parameters
                        )
                        .responseFields(
                            *fields
                        )
                        .build()
                )
            )
        }

        fun MockMvcDocument(
            tagSummary: Summary,
            parameters: List<ParameterDescriptorWithType>,
            responseFieldDescriptors: FieldDescriptors,
            pathParamter: List<ParameterDescriptorWithType> = listOf(),
        ): RestDocumentationResultHandler {

            return MockMvcRestDocumentationWrapper.document(
                tagSummary.identifier,
                preprocessRequest(prettyPrint()), //Request setting
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag(tagSummary.tag)
                        .summary(tagSummary.summary)
                        /*.requestHeaders(
                            //default 라서 추가 안함
                            headerWithName("os").description("os 정보 I/A").optional(),
                            headerWithName("version").description("버전 정보 2.3.4").optional(),
                        )*/
                        .pathParameters(pathParamter)
                        .queryParameters(
                            parameters
                        )
                        .responseFields(
                            responseFieldDescriptors
                        )
                        .build()
                )
            )
        }
        fun MockMvcDocumentByPost(
            tagSummary: Summary,
            requestBody: FieldDescriptors,
            responseFieldDescriptors: FieldDescriptors,
            pathParamter: List<ParameterDescriptorWithType> = listOf(),
        ): RestDocumentationResultHandler {

            return MockMvcRestDocumentationWrapper.document(
                tagSummary.identifier,
                preprocessRequest(prettyPrint()), //Request setting
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag(tagSummary.tag)
                        .summary(tagSummary.summary)
                        .requestHeaders(
                            //default 라서 추가 안함
                            headerWithName("os").description("os 정보 I/A").optional(),
                            headerWithName("version").description("버전 정보 2.3.4").optional(),
                        )
                        .pathParameters(pathParamter)
                        .requestFields(
                            requestBody
                        )
                        .responseFields(
                            responseFieldDescriptors
                        )
                        .build()
                )
            )
        }
    }


}
