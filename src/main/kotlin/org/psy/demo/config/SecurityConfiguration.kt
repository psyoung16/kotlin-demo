package org.psy.demo.config

//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import com.fasterxml.jackson.databind.ObjectMapper
import org.psy.demo.common.ResponseDataEntity
import org.psy.demo.common.exception.ErrorCode
import org.psy.demo.common.exception.GlobalExceptionHandler.ErrorResponse
import org.psy.demo.user.domain.AuthUser
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(

    private val jwtService: JwtService,
    private val objectMapper: ObjectMapper,

    ) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/test/**","/api/CLIENT/v3.0/test","/api/CLIENT/v3.0/static/*","/api/CLIENT/v3.0/static/swagger-ui/*","/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    .anyRequest().authenticated() // 나머지 요청은 인증 필요
            }
            .addFilterBefore(JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { exceptions ->
                exceptions
                    .authenticationEntryPoint { request, response, authException ->
                        writeErrorResponse(response, ErrorCode.PERMISSION_DENIED, HttpStatus.FORBIDDEN)
                    }
                    .accessDeniedHandler { request, response, accessDeniedException ->
                        writeErrorResponse(response, ErrorCode.PERMISSION_DENIED, HttpStatus.FORBIDDEN)
                    }
            }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }  //서버가 세션 상태를 유지하지 않음을 명시적으로 선언
            .csrf { it.disable() } //jwt라서 필요x
            .httpBasic { it.disable() } //jwt라서 필요x

        return http.build()
    }

    private fun writeErrorResponse(response: HttpServletResponse, errorCode: ErrorCode, status: HttpStatus) {
        val errorResponse = ResponseDataEntity.error(
            status = status,
            data = ErrorResponse(errorCode.code, errorCode.message),
            log = ""
        )
        response.apply {
            this.status = status.value()
            contentType = "application/json"
            characterEncoding = "UTF-8"
            outputStream.use { out ->
                out.write(objectMapper.writeValueAsString(errorResponse.body).toByteArray(Charsets.UTF_8))
                out.flush()
            }
        }
    }
}


//jwt
class JwtAuthenticationFilter(private val jwtService: JwtService) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("")) {
            val token = authorizationHeader
            if (jwtService.validateToken(token)) {
                val claims = jwtService.decodeJwt(token)
                val id = claims["id"]?.toString() ?: ""
                val nickname = claims["nickname"]?.toString() ?: ""
                val roles = (claims["roles"] as? List<String>) ?: listOf("USER")

                val userDetails = AuthUser(
                    userId = AuthUser.AuthUserId.of(id),
                    nickname = nickname,
                    deviceId = "",
                    password = "",
                    roles = roles
                )

                val authorities = userDetails.authorities
                val authentication = JwtAuthenticationToken(userDetails, null, authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                val context: SecurityContext = SecurityContextHolder.createEmptyContext()
                context.authentication = authentication
                SecurityContextHolder.setContext(context)
            }
        }

        filterChain.doFilter(request, response)
    }
}



class JwtAuthenticationToken(
    private val principal: AuthUser,
    private var credentials: String? = null,
    authorities: Collection<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {

    init {
        super.setAuthenticated(true)
    }

    override fun getCredentials(): Any? {
        return credentials
    }

    override fun getPrincipal(): Any {
        return principal
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun setAuthenticated(authenticated: Boolean) {
        require(!authenticated) { "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead" }
        super.setAuthenticated(false)
    }

    override fun eraseCredentials() {
        super.eraseCredentials()
        credentials = null
    }
}
