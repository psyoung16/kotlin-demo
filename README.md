# Kotlin Demo

## 🎯 아키텍처 개요

이 프로젝트는 **단순화된 MVC 아키텍처**를 채택하며, Entity/Domain/Response DTO의 명확한 분리를 통해 각 계층이 독립적으로 진화할 수 있도록 설계되었습니다.

```
JpaEntity (DB 최적화) ↔ Domain (비즈니스 로직) ↔ Response DTO (클라이언트 최적화)
```

## 📁 프로젝트 구조

```
src/main/kotlin/org/psy/demo/
├── app/                         # 사용자 API 레이어 (54 files)
│   ├── main/                    # 메인 화면 기능
│   │   ├── controller/          # 메인 API 엔드포인트
│   │   ├── usecase/             # 메인에서 사용하는 모든 UseCase
│   │   │   ├── GetMainUseCase
│   │   │   ├── GetGoodsUseCase
│   │   │   ├── GetPostUseCase
│   │   │   └── ...
│   │   └── GetMainFacade.kt     # 메인 화면 데이터 조합
│   ├── sdui/                    # Server-Driven UI
│   │   ├── controller/
│   │   ├── translator/          # DTO 변환
│   │   └── usecase/
│   ├── sticker/                 # 스티커 기능
│   │   ├── controller/
│   │   ├── response/
│   │   └── usecase/
│   └── response/                # 공통 Response DTO
│
├── admin/ (6 files)             # 관리자 API
│   ├── controller/
│   ├── response/
│   ├── translator/
│   └── usecase/
│
├── core/ (88 files)             # 비즈니스 로직 레이어
│   ├── common/                  # 공통 도메인
│   │   └── domain/
│   ├── content/                 # 콘텐츠 도메인
│   │   ├── domain/
│   │   └── service/
│   ├── goods/                   # 상품 도메인
│   │   ├── domain/
│   │   └── service/
│   ├── phrase/                  # 문구 도메인
│   │   ├── domain/
│   │   └── service/
│   ├── post/                    # 게시물 도메인
│   │   ├── domain/
│   │   └── service/
│   ├── promotion/               # 프로모션 도메인
│   ├── sdui/                    # SDUI 비즈니스 로직
│   │   ├── facade/              # 여러 도메인 조합
│   │   └── mapper/
│   │       ├── compositor/
│   │       └── factory/
│   ├── sticker/                 # 스티커 도메인
│   │   ├── domain/
│   │   │   └── vo/
│   │   └── service/
│   ├── user/                    # 사용자 도메인
│   │   ├── domain/
│   │   └── service/
│   └── vo/                      # 공통 Value Objects
│
├── infra/ (90 files)            # 인프라스트럭처 레이어
│   ├── jpaEntity/               # JPA 엔티티 (@Entity)
│   ├── jpaRepository/           # Spring Data JPA 인터페이스
│   ├── repository/              # 비즈니스 Repository 구현체
│   ├── mapper/                  # Entity ↔ Domain 매핑
│   ├── sticker/                 # 스티커 인프라 (하위 모듈)
│   │   ├── jpaEntity/
│   │   └── repository/
│   └── vo/                      # 인프라 Value Objects
│
├── config/ (6 files)            # 설정
│   ├── DataSourceConfig.kt      # Read/Write DB 분리
│   ├── SecurityConfiguration.kt # Spring Security + JWT
│   ├── RedisConfig.kt           # Redis 캐싱
│   └── db/
│       └── RoutingDataSource.kt # DB 라우팅
│
├── common/ (18 files)           # 공통 유틸리티
│   ├── exception/               # 예외 처리
│   ├── validation/              # 커스텀 Validator
│   └── deserializer/            # JSON 역직렬화
│
└── user/                        # Spring Security 인증
    └── domain/
        └── AuthUser.kt

## 🏗️ 주요 설계 원칙

### 1. 3계층 분리 아키텍처
```kotlin
// 1️⃣ JpaEntity (영속성 최적화)
@Entity
class PostJpaEntity {
    @Id val id: Long
    @Column val title: String
    // DB 스키마에 최적화
}

// 2️⃣ Domain Model (비즈니스 핵심)
data class Post(
    val id: PostId,
    val title: String,
    val author: User
    // 비즈니스 로직 중심
)

// 3️⃣ Response DTO (API 최적화)
data class PostResponse(
    val id: Long,
    val title: String,
    val authorName: String
    // 클라이언트 요구사항에 최적화
)
```

### 2. Repository 패턴 (3단계)
```kotlin
// Spring Data JPA 인터페이스
interface ManageTagJpaRepository : JpaRepository<ManageTagJpaEntity, Long> {
    fun findByPosition(position: TagType): List<ManageTagJpaEntity>
}

// 비즈니스 Repository
@Component
class ManageTagRepository(
    private val jpaRepository: ManageTagJpaRepository
) {
    fun loadManageTag(position: TagType): List<ManageTag> {
        return jpaRepository.findByPosition(position)
            .map { it.toDomain() }  // Entity → Domain 변환
    }
}
```

### 3. Facade 패턴으로 복잡성 관리
- `GetMainFacade`: 메인 화면에 필요한 모든 도메인 데이터 조합
- 여러 UseCase를 조합하여 단일 진입점 제공

### 4. SDUI (Server-Driven UI)
- 서버에서 UI 구조와 데이터를 함께 전송
- 클라이언트 업데이트 없이 UI 변경 가능
- `translator`와 `mapper`를 통한 유연한 UI 조합

## 🚀 실행 방법

### 애플리케이션 실행
```bash
# 로컬 환경 실행
./gradlew bootRun --args='--spring.profiles.active=local'

# 개발 환경
./gradlew bootRun --args='--spring.profiles.active=dev'

# JAR 빌드 및 실행
./gradlew bootJar
java -jar build/libs/*.jar --spring.profiles.active=local
```

### 테스트
```bash
# 전체 테스트 실행
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests "*.MainControllerTest"

# 테스트 리포트 확인
open build/reports/tests/test/index.html
```

### API 문서
```bash
# OpenAPI 스펙 생성
./gradlew openapi3

# Swagger UI 업데이트
./gradlew copyOasToSwagger

# 실행 후 접속
# http://localhost:8080/swagger-ui.html
```

## 🛠️ 기술 스택

### Core
- **Kotlin 1.9.23** - K2 컴파일러 (experimental)
- **Spring Boot 3.2.4** - 최신 Spring 프레임워크
- **Spring Web MVC** - REST API

### Data
- **MySQL** - 메인 데이터베이스
  - Read/Write 분리 (RoutingDataSource)
  - HikariCP 커넥션 풀링
- **Redis** - 캐싱 및 세션 관리
- **JPA/Hibernate** - ORM

### Security
- **Spring Security** - 인증/인가
- **JWT (jjwt)** - 토큰 기반 인증

### Documentation & Testing
- **Spring REST Docs** - API 문서화
- **OpenAPI 3.0** - API 스펙
- **JUnit 5** - 단위 테스트
- **MockK** - Kotlin 모킹 프레임워크

## 📊 프로젝트 통계

- **총 파일 수**: 266개 Kotlin 파일
- **패키지별 분포**:
  - app: 54 files (API 레이어)
  - core: 88 files (비즈니스 로직)
  - infra: 90 files (인프라스트럭처)
  - admin: 6 files (관리자 API)
  - config: 6 files (설정)
  - common: 18 files (유틸리티)