package com.example.agricult.paging

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.agricult.models.category.CategoryModel
import com.example.agricult.models.category.Data
import com.example.agricult.viewmodel.CategoryViewModel
import com.example.agricult.viewmodel.DataStoreViewModel
import retrofit2.HttpException

class ResultSource(
    private val categoryViewModel: CategoryViewModel,
    private val dataStoreViewModel: DataStoreViewModel,
    private val categoryId: Int? = null,
    private val priceFrom: Int? = null,
    private val priceTo: Int? = null,
    private val orderBy: String? = null,
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {


        val getToken = mutableStateOf("")
        getToken.value = dataStoreViewModel.readFromDataStore.value.toString()


        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(20)

        val response = categoryId?.let {
            categoryViewModel.getCategoryRequest(
                token = "Bearer $getToken",
                categoryId = it,
                priceFrom = priceFrom,
                priceTo = priceTo,
                orderBy = orderBy,
                page = page
            )
        }

        val articlesData = checkNotNull(categoryViewModel.getCategoryModel.value)
        val nextKey = if (articlesData.size < pageSize) null else page + 1
        val prevKey = if (page == 1) null else page - 1

        return LoadResult.Page(articlesData, prevKey, nextKey)

    }


    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }


}