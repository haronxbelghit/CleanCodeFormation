package com.sqli.cleancodeformation.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sqli.cleancodeformation.domain.model.User

class UserPagingDataSource(
    private val userDataSource: UserDataSource,
) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 0

        return try {
            val entities = userDataSource.getUsersPaged(params.loadSize, page * params.loadSize)
            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
