package org.psy.demo.user.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthUser (
    private val userId: AuthUserId,
    private val nickname: String,
    private val deviceId: String,
    private val password: String,
    private val roles: List<String>
) : UserDetails {


    data class AuthUserId(val id: String) {
        companion object {
            fun of(id: String): AuthUserId {
                require(id.isNotBlank()) { "ID cannot be blank" }
                return AuthUserId(id)
            }
        }
    }


    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }
    }

    override fun getPassword(): String = password
    override fun getUsername(): String {
        return userId.id
    }


    // 계정 만료 여부
    override fun isAccountNonExpired(): Boolean = true

    // 계정 잠금 여부
    override fun isAccountNonLocked(): Boolean = true

    // 비밀번호 만료 여부
    override fun isCredentialsNonExpired(): Boolean = true

    // 계정 활성화 여부
    override fun isEnabled(): Boolean = true


}
