package org.wordpress.android.ui.stats.refresh.lists.sections.granular

import kotlinx.coroutines.CoroutineDispatcher
import org.wordpress.android.R
import org.wordpress.android.fluxc.model.SiteModel
import org.wordpress.android.fluxc.network.utils.StatsGranularity
import org.wordpress.android.fluxc.store.StatsStore.StatsTypes
import org.wordpress.android.ui.stats.refresh.lists.sections.BaseStatsUseCase.StatelessUseCase
import org.wordpress.android.ui.stats.refresh.lists.sections.BlockListItem
import java.util.Date

abstract class GranularStatelessUseCase<DOMAIN_MODEL>(
    type: StatsTypes,
    mainDispatcher: CoroutineDispatcher,
    val selectedDateProvider: SelectedDateProvider,
    val statsGranularity: StatsGranularity
) : StatelessUseCase<DOMAIN_MODEL>(type, mainDispatcher) {
    abstract suspend fun loadCachedData(selectedDate: Date, site: SiteModel): DOMAIN_MODEL?

    final override suspend fun loadCachedData(site: SiteModel): DOMAIN_MODEL? {
        val selectedDate = selectedDateProvider.getSelectedDate(statsGranularity)
        return selectedDate.date?.let { loadCachedData(selectedDate.date, site) }
    }

    abstract suspend fun fetchRemoteData(selectedDate: Date, site: SiteModel, forced: Boolean): State<DOMAIN_MODEL>

    final override suspend fun fetchRemoteData(site: SiteModel, forced: Boolean): State<DOMAIN_MODEL> {
        return selectedDateProvider.getSelectedDate(statsGranularity).let { date ->
            when {
                date.error -> State.Error("Missing date")
                date.date != null -> fetchRemoteData(date.date, site, forced)
                date.loading -> State.Loading()
                else -> State.Loading()
            }
        }
    }

    override fun buildEmptyItem(): List<BlockListItem> {
        return buildLoadingItem() + listOf(BlockListItem.Empty(textResource = R.string.stats_no_data_for_period))
    }
}
