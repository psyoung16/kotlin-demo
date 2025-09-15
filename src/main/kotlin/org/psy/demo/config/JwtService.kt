package org.psy.demo.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service


@Service
@Component
class JwtService {

    @Value("\${jwt.secret}") // Kotlin에서는 $ 문자를 이스케이프 처리해야 합니다.
    private lateinit var secretKey: String
//    private val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS512)

    fun decodeJwt(token: String): Claims {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.toByteArray())
                .build()
                .parseClaimsJws(token)
                .body

    }
    fun validateToken(token: String): Boolean {
        return try {
            decodeJwt(token)
            true
        } catch (e: Exception) {
            false
        }
    }
}
