package org.psy.demo.infra.repository

import org.psy.demo.common.PageParam
import org.psy.demo.core.phrase.domain.Phrase
import org.psy.demo.core.phrase.domain.PhraseManagement
import org.psy.demo.infra.jpaEntity.PhraseJpaEntity
import org.psy.demo.infra.jpaRepository.PhraseJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import java.util.*


@Component

class PhraseRepository(
    private val phraseJpaRepository: PhraseJpaRepository
){
    fun loadPhrase(position: String?): Phrase? {
        val phrase: List<PhraseJpaEntity> = phraseJpaRepository.findAllByPositionNotDate(position)
        return phrase.map(PhraseJpaEntity::mapToDomain).firstOrNull()
    }

    fun loadPhrases(position: String?): List<Phrase> {

        val phrase = phraseJpaRepository.findAllByPosition(Date(), Date(),  position)
        return phrase.map(PhraseJpaEntity::mapToDomain)
    }

    fun loadManagementPhrase(position: String?): PhraseManagement? {
        val phrase: PhraseJpaEntity? = phraseJpaRepository.findOneByPosition(position);
        return phrase?.mapToDomainManagement()
    }

    fun loadManagementPhrases(position: String?, pageParam: PageParam): Page<PhraseManagement> {
        val phrases = phraseJpaRepository.findByPosition(
            position, PageRequest.of(
                pageParam.page, pageParam.size, Sort.by(
                    Sort.Order.desc("id")
                )
            )
        )
        return phrases.map(PhraseJpaEntity::mapToDomainManagement)
    }

    fun createPhraseManagement(phrase: PhraseManagement) {
        //ok
        phraseJpaRepository.save(PhraseJpaEntity (
            phrase.id?.toInt(),
            null, //startAt 현재는 관리자에서 생성만 있음
            null, //endAt 현재는 관리자에서 생성만 있음
            phrase.createdAt,
            phrase.createdAt,
            phrase.phrase,
            phrase.position,
            phrase.id?.toInt(),
            phrase.boldPhrase,
        ))
    }
}
