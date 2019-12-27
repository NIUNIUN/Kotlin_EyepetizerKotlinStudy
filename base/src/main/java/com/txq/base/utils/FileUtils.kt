package com.txq.base.utils

import java.io.File

/**
 * Created by tang_xqing on 2019/12/13.
 */
class FileUtils private constructor() {
    companion object {

        fun createFiles(filePath: String, fileName: String? = null): File {
            var file = if (fileName.isNullOrEmpty()) File(filePath) else File(filePath, fileName)
            if (!file.exists()) {
                file.mkdirs()
            }
            return file
        }

        fun createFile(
            fileName: String,
            filePath: String,
            createPath: Boolean = false,
            delExistFile: Boolean = false
        ): File? {
            var file = File(fileName, filePath)
            if (!file.parentFile.exists()) {
                if (!createPath) {
                    return null
                }
                createFiles(filePath)
            }
            if (!file.exists()) {
                file.createNewFile()
            } else if (delExistFile) {
                file.delete()
                file.createNewFile()
            }
            return file
        }

        fun deleteFile(file: File): Boolean {
            return file.delete()
        }
    }
}