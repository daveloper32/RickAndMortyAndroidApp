package com.daveloper.rickandmortyapp.feature_character.utils.conversion

import com.daveloper.rickandmortyapp.core.data.network.response.InfoModel
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.string.StringUtils.getIdFromUrl
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.model.PageInfoData

object PageInfoUtils {
    fun InfoModel.toPageInfoData(): PageInfoData = PageInfoData(
        count = this.count,
        pages = this.pages,
        nextPage = this.nextPage?.getIdFromUrl() ?: Constants.INVALID_INT,
        previousPage = this.previousPage?.getIdFromUrl() ?: Constants.INVALID_INT,
    )
}