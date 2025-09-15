package org.psy.demo.app.sdui.translator

import org.psy.demo.app.sdui.translator.container.SectionType

data class Tabs(
    val id: TAB_ID,
    val title: String
) {
    enum class TAB_ID {
        FEED, ETC,
        WRITTEN_POST, RECENT_COMMENT,
        LIKED, SCRAP ,GOODS_WISH;


        companion object {
            val COMMUNITY_TABS = setOf(FEED, ETC)
            val MYKKOOMMING_POST_TABS = setOf(WRITTEN_POST, RECENT_COMMENT)
            val STORAGE_TABS = setOf(LIKED, SCRAP, GOODS_WISH)

            fun getNames(tabs: Set<TAB_ID>): Array<String> = tabs.map { it.name }.toTypedArray()
        }
    }
}


//section 안정성 강화를 위해
//ex: 특정 화면에서 특정 section을 체크하는데, 그 section은 TAB_ID로 구별함
sealed class CommunityTab(val tabId: Tabs.TAB_ID) {
    abstract val sectionTypes: List<SectionType>
    abstract val defaultSectionType: SectionType
    abstract val title: String

    object Feed : CommunityTab(Tabs.TAB_ID.FEED) {
        override val sectionTypes = listOf(SectionType.H_CURATION_IMAGE_ROW, SectionType.H_CURATION_IMAGE_TEXT_ROW, SectionType.V_CONTENT_MIX_LIST)
        override val defaultSectionType = SectionType.H_CURATION_IMAGE_ROW
        override val title = "유저들이 좋아하는 콘텐츠"
    }

    object Etc : CommunityTab(Tabs.TAB_ID.ETC) {
        override val sectionTypes = listOf(SectionType.H_CURATION_IMAGE_TEXT_ROW, SectionType.H_CURATION_IMAGE_ROW, SectionType.V_CONTENT_MIX_LIST)
        override val defaultSectionType = SectionType.H_CURATION_IMAGE_TEXT_ROW
        override val title = "지금 뜨고 있는 글"
    }

    companion object {
        fun fromTabId(tabId: Tabs.TAB_ID): CommunityTab = when (tabId) {
            Tabs.TAB_ID.FEED -> Feed
            Tabs.TAB_ID.ETC -> Etc
            else -> throw IllegalArgumentException("Invalid tabId value")
        }
    }

    fun isValidSectionType(sectionType: SectionType): Boolean = sectionType in sectionTypes
}
