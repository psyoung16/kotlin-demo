package org.psy.demo.core.common.domain

data class File(

    val fileId: FileId,
    val url: String


){

    data class FileId(val id: String) {
        companion object {
            fun of(id: String): FileId = FileId(id)
        }
    }

}
