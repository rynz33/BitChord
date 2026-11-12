package com.music.bitchord.ui.screens

import com.music.bitchord.ui.components.trackArtSize
import androidx.compose.ui.layout.ContentScale
import com.music.bitchord.data.model.toSong
import com.music.bitchord.ui.components.SwipeToQueueBox
import com.music.bitchord.ui.components.contextClick
import androidx.compose.ui.unit.Dp
import com.music.bitchord.sharedui.resources.*
import org.jetbrains.compose.resources.stringResource
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import com.music.bitchord.ui.components.PillSegmentedControl
import com.music.bitchord.ui.components.longPressMenuClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.NorthWest
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.automirrored.rounded.PlaylistAdd
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import coil3.compose.AsyncImage
import com.music.bitchord.data.model.BrowseItem
import com.music.bitchord.data.model.BrowseType
import com.music.bitchord.data.model.ROW_ART_PX
import com.music.bitchord.data.model.SearchFilter
import com.music.bitchord.data.model.artworkAt
import com.music.bitchord.data.model.SearchResult
import com.music.bitchord.data.model.Song
import com.music.bitchord.data.model.playingRowMatcher
import com.music.bitchord.data.model.UiState
import com.music.bitchord.data.model.SearchHistoryEntity
import com.music.bitchord.data.model.EntityType
import com.music.bitchord.ui.components.MessageState
import com.music.bitchord.ui.components.PAGE_GUTTER
import com.music.bitchord.ui.components.RowMoreButton
import com.music.bitchord.ui.components.ROW_DIVIDER_INSET
import com.music.bitchord.ui.components.SearchField
import com.music.bitchord.ui.components.SongRow
import com.music.bitchord.ui.components.SearchPlayingBars
import com.music.bitchord.ui.components.thumbnailBorder
import com.music.bitchord.ui.components.songListSkeleton
import java.util.Locale

@Composable
fun SearchScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    filter: SearchFilter,
    currentSong: Song?,
    isPlaying: Boolean,
    onFilterChange: (SearchFilter) -> Unit,
    results: UiState<List<SearchResult>>?,
    loadingMore: Boolean,
    onLoadMore: () -> Unit,
    listState: LazyListState,
    scrollResetTrigger: Int,
    focusRequested: Boolean,
    onFocusHandled: () -> Unit,
    onSongClick: (List<Song>, Int) -> Unit,
    onSongLongPress: (Song) -> Unit,
    onSongSwipe: (Song) -> Unit,
    onTopResultPlay: (Song) -> Unit,
    onTopResultPlaylist: (Song) -> Unit,
    onBrowseClick: (BrowseItem) -> Unit,
    /**
     * Holding an album or playlist hit rather than tapping it — the same menu
     * the shelves open, so a release found by searching can go on the queue
     * without a trip through its page.
     */
    onBrowseLongPress: ((BrowseItem) -> Unit)? = null,
    history: List<SearchHistoryEntity>,
    suggestions: List<String>,
    typeaheadResults: List<SearchResult>,
    onSubmit: () -> Unit,
    onSuggestionClick: (String) -> Unit,
    onHistoryClick: (SearchHistoryEntity) -> Unit,
    onHistoryRemove: (String) -> Unit,
    onHistoryClear: () -> Unit,
    /** Long-press handler for typeahead rows — opens the song actions sheet. */
    onTypeaheadLongPress: ((Song) -> Unit)? = null,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    /**
     * Room above the search field. The phone's frosted bar floats over the top
     * of the tab, so the field has to clear it; the desktop has no such bar.
     */
    topPadding: Dp,
    /**
     * Whether the page draws its own search field. The desktop's sidebar has
     * the one search box the window needs, so the page there is results only.
     */
    showField: Boolean = true,
    /**
     * Whether the page is searching the device's Local Music folder instead of
     * YouTube. The phone's top bar flips this; the desktop never does.
     */
    searchingLibrary: Boolean = false,
    /** Whether the page is showing the enabled addons' combined results; see [addonResults]. */
    searchingAddons: Boolean = false,
    /** The Addons source's tracks for the submitted query; null until one is submitted. */
    addonResults: UiState<List<Song>>? = null,
    /**
     * Addon rows the connection's quality setting won't play. Shown dimmed,
     * kept out of the queue a tap builds, and answered by [onBlockedSongClick]
     * instead of being played.
     */
    isBlocked: (Song) -> Boolean = { false },
    onBlockedSongClick: (Song) -> Unit = {},
    /** Words searched in the Addons tab before, newest first; shown while the field is empty. */
    addonHistory: List<String> = emptyList(),
    onAddonHistoryClick: (String) -> Unit = {},
    onAddonHistoryRemove: (String) -> Unit = {},
    onAddonHistoryClear: () -> Unit = {},
    /** The Library source's tracks for [query]; null while nothing is typed. */
    libraryResults: UiState<List<Song>>? = null,
    /** Files on the device are a running order to play through, not a station seed. */
    onLibrarySongClick: (List<Song>, Int) -> Unit = onSongClick,
    /**
     * The YouTube / Library switcher, full width at the head of the page. Up
     * only while the field is empty: once something is typed the page is
     * about the results, and the switcher would just push them down.
     */
    sourceSwitcher: (@Composable () -> Unit)? = null,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    // Tapping the search tab from the nav bar sets focusRequested;
    // respond by focusing the field and opening the keyboard.
    LaunchedEffect(focusRequested) {
        if (focusRequested && showField) {
            focusRequester.requestFocus()
            keyboardController?.show()
            onFocusHandled()
        }
    }
    // Search keeps one list state while its contents change. Reset it for each
    // new request so choosing a recent search cannot inherit the history's
    // previous scroll position (or a previous result page's position).
    LaunchedEffect(scrollResetTrigger) {
        if (scrollResetTrigger > 0) listState.scrollToItem(0)
    }
    // A non-empty suggestion list means the field is mid-edit — see
    // MainViewModel.suggestions. Nothing below it is worth showing while it is
    // up: the results are for whatever was searched before this edit began,
    // and so are the filter tabs above them.
    val suggesting = suggestions.isNotEmpty() && !searchingLibrary && !searchingAddons
    // Live media results arrive from the parallel typeahead pipeline; show
    // them only while the user is still typing (suggestions visible), so they
    // appear as a dropdown beneath the text completions rather than floating
    // after the search has committed.
    val showTypeahead = typeaheadResults.isNotEmpty() && suggesting
    LaunchedEffect(listState, results, loadingMore, searchingLibrary, searchingAddons) {
        if (results !is UiState.Success || searchingLibrary || searchingAddons) return@LaunchedEffect
        snapshotFlow {
            val layout = listState.layoutInfo
            (layout.visibleItemsInfo.lastOrNull()?.index ?: -1) to layout.totalItemsCount
        }.collect { (lastVisible, total) ->
            if (!loadingMore && total > 0 && lastVisible >= total - 4) onLoadMore()
        }
    }
    var isSearchFocused by remember { mutableStateOf(false) }
    val searchBorderColor by animateColorAsState(
        targetValue = when {
            isSearchFocused -> MaterialTheme.colorScheme.primary
            query.isNotEmpty() -> MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)
            else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.20f)
        },
        label = "searchBorderColor",
    )
    val searchGlowColor by animateColorAsState(
        targetValue = if (isSearchFocused) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        } else {
            Color.Transparent
        },
        label = "searchGlowColor",
    )
    Column(modifier = modifier.fillMaxSize()) {
        // Search field and filter tabs stay fixed at the top, outside the
        // scrolling list, so they're always reachable rather than scrolling
        // away with the results or recent searches beneath them.
        // The FrostedTopBar is visible on this tab (showing "Search"), so we
        // clear it fully — status bar inset + bar height + breathing gap — so
        // the search field sits cleanly below the bar instead of overlapping it.
        Column(modifier = Modifier.padding(top = topPadding)) {
            if (showField) {
                SearchHeaderAccentBar(
                    searchingLibrary = searchingLibrary,
                    searchingAddons = searchingAddons,
                    isFocused = isSearchFocused,
                )
                Box(
                    modifier = Modifier
                        .padding(start = PAGE_GUTTER, end = PAGE_GUTTER, bottom = 6.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(searchGlowColor)
                        .border(
                            width = if (isSearchFocused) 1.5.dp else 1.dp,
                            color = searchBorderColor,
                            shape = RoundedCornerShape(percent = 50),
                        )
                        .padding(if (isSearchFocused) 2.dp else 1.dp)
                        .onFocusChanged { isSearchFocused = it.hasFocus },
                ) {
                    SearchField(
                        query = query,
                        onQueryChange = onQueryChange,
                        onSubmit = onSubmit,
                        focusRequester = focusRequester,
                        placeholder = if (searchingLibrary) {
                            stringResource(Res.string.search_library_hint)
                        } else if (searchingAddons) {
                            stringResource(Res.string.search_addons_hint)
                        } else {
                            stringResource(Res.string.search_hint)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
            if (sourceSwitcher != null) {
                AnimatedVisibility(
                    visible = query.isEmpty(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    Box(Modifier.padding(start = PAGE_GUTTER, end = PAGE_GUTTER, bottom = 6.dp)) {
                        sourceSwitcher()
                    }
                }
            }
            // The filters only mean something once there is a result set to narrow;
            // they stay up for an empty or failed search too, or picking a filter
            // that finds nothing would take away the control needed to leave it.
            // They are YouTube's categories, so the Library source has none.
            if (results != null && !suggesting && !searchingLibrary && !searchingAddons) {
                SearchFilterChipRow(
                    filters = SearchFilter.entries,
                    selectedFilter = filter,
                    onFilterSelect = onFilterChange,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding()),
        ) {
            when {
                searchingLibrary || searchingAddons -> librarySearchResults(
                    query = query.trim(),
                    results = if (searchingAddons) addonResults else libraryResults,
                    addons = searchingAddons,
                    addonHistory = addonHistory,
                    onAddonHistoryClick = onAddonHistoryClick,
                    onAddonHistoryRemove = onAddonHistoryRemove,
                    onAddonHistoryClear = onAddonHistoryClear,
                    currentSong = currentSong,
                    isPlaying = isPlaying,
                    onSongClick = onLibrarySongClick,
                    onSongLongPress = onSongLongPress,
                    onSongSwipe = onSongSwipe,
                    isBlocked = if (searchingAddons) isBlocked else { _ -> false },
                    onBlockedSongClick = onBlockedSongClick,
                )
                suggesting -> {
                    searchSuggestions(
                        suggestions = suggestions,
                        // Picking one is done typing, so the keyboard comes down
                        // with it and the results get the whole screen.
                        onClick = { term ->
                            onSuggestionClick(term)
                            focusManager.clearFocus()
                        },
                        onFill = onQueryChange,
                    )
                    if (showTypeahead) {
                        searchTypeaheadDropdown(
                            typeaheadResults = typeaheadResults,
                            onSongClick = { song -> onTopResultPlay(song) },
                            onSongLongPress = onTypeaheadLongPress,
                            onBrowseClick = { item ->
                                onBrowseClick(item)
                            },
                            currentSong = currentSong,
                            isPlaying = isPlaying,
                        )
                    }
                }
                results == null -> if (history.isEmpty()) {
                    item { SearchEmptyAccentState(stringResource(Res.string.search_empty)) }
                } else {
                    recentSearches(
                        history = history,
                        currentSong = currentSong,
                        isPlaying = isPlaying,
                        onClick = onHistoryClick,
                        onSongLongPress = onSongLongPress,
                        onRemove = onHistoryRemove,
                        onClear = onHistoryClear,
                        onSongSwipe = onSongSwipe,
                    )
                }
                results is UiState.Loading -> songListSkeleton(circular = filter == SearchFilter.ARTISTS)
                results is UiState.Error -> item { SearchEmptyAccentState(results.message) }
                results is UiState.Success -> {
                    val tracks = results.data
                        .mapNotNull { row -> when (row) {
                            is SearchResult.TopTrack -> row.song
                            is SearchResult.Track -> row.song
                            is SearchResult.Browse -> null
                            else -> null
                        } }
                    val isCurrentRow = playingRowMatcher(tracks, currentSong)
                    val topResult = results.data.filterIsInstance<SearchResult.TopTrack>().firstOrNull()
                    if (filter == SearchFilter.ALL && topResult != null) {
                        item(key = "search:top-result:${topResult.song.videoId}") {
                            SwipeToQueueBox(
                                onSwipeToQueue = { onSongSwipe(topResult.song) },
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                TopResultCard(
                                    song = topResult.song,
                                    onPlay = { onTopResultPlay(topResult.song) },
                                    onPlaylist = { onTopResultPlaylist(topResult.song) },
                                    onLongPress = { onSongLongPress(topResult.song) },
                                )
                            }
                        }
                    }
                    searchSections(results.data, filter).forEach { section ->
                        section.title?.let { title ->
                            item(key = "search-section:$title") {
                                SearchSectionHeader(
                                    title = title,
                                    count = section.rows.size,
                                )
                            }
                        }
                        itemsIndexed(
                            items = section.rows,
                            key = { index, row ->
                                when (row) {
                                    is SearchResult.TopTrack -> "top_${row.song.videoId}_$index"
                                    is SearchResult.Track -> "track_${row.song.videoId}_$index"
                                    is SearchResult.Browse -> "browse_${row.item.browseId}_$index"
                                }
                            },
                        ) { index, row ->
                            when (row) {
                                is SearchResult.TopTrack -> Unit
                                is SearchResult.Track -> SongRow(
                                    song = row.song,
                                    onClick = {
                                        onSongClick(tracks, tracks.indexOf(row.song).coerceAtLeast(0))
                                    },
                                    onLongPress = { onSongLongPress(row.song) },
                                    onSwipeToQueue = { onSongSwipe(row.song) },
                                    isCurrent = isCurrentRow(row.song),
                                    isPlaying = isPlaying && isCurrentRow(row.song),
                                    searchPlayingStyle = true,
                                )
                                is SearchResult.Browse -> BrowseRow(
                                    item = row.item,
                                    onClick = { onBrowseClick(row.item) },
                                    onLongPress = onBrowseLongPress?.let { { it(row.item) } },
                                )
                            }
                            if (index < section.rows.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(start = ROW_DIVIDER_INSET),
                                    thickness = 0.5.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                )
                            }
                        }
                    }
                    if (loadingMore) {
                        songListSkeleton(
                            count = 3,
                            keyPrefix = "skeleton:search:more",
                            circular = filter == SearchFilter.ARTISTS,
                        )
                    }
                }
            }
        }
    }
}

/**
 * RynzMusic signature Search screen header strip: pairs a vertical primary
 * accent bar and two-tone title with an active source pill badge.
 */
@Composable
private fun SearchHeaderAccentBar(
    searchingLibrary: Boolean,
    searchingAddons: Boolean,
    isFocused: Boolean,
) {
    val modeLabel = when {
        searchingLibrary -> "LIBRARY"
        searchingAddons -> "ADDONS"
        else -> "YOUTUBE"
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = PAGE_GUTTER, end = PAGE_GUTTER, bottom = 6.dp),
    ) {
        Box(
            modifier = Modifier
                .width(3.5.dp)
                .height(16.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Rynz",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "Search",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.primary.copy(
                        alpha = if (isFocused) 0.22f else 0.12f,
                    ),
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(
                        alpha = if (isFocused) 0.45f else 0.25f,
                    ),
                    shape = RoundedCornerShape(percent = 50),
                )
                .padding(horizontal = 8.dp, vertical = 2.dp),
        ) {
            Text(
                text = modeLabel,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

/**
 * Dynamic RynzMusic filter chip strip for Search categories, highlighting the
 * active filter tab pill with [MaterialTheme.colorScheme.primary].
 */
@Composable
private fun SearchFilterChipRow(
    filters: List<SearchFilter>,
    selectedFilter: SearchFilter,
    onFilterSelect: (SearchFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = PAGE_GUTTER),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        items(filters, key = { it.name }) { item ->
            val selected = item == selectedFilter
            val backgroundColor = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            }
            val borderColor = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.22f)
            }
            val textColor = if (selected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .border(1.dp, borderColor, CircleShape)
                    .clickable { onFilterSelect(item) }
                    .padding(horizontal = 14.dp, vertical = 7.dp),
            ) {
                if (selected) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary),
                    )
                }
                Text(
                    text = item.label,
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor,
                    maxLines = 1,
                )
            }
        }
    }
}

/**
 * RynzMusic section header for mixed search result categories.
 */
@Composable
private fun SearchSectionHeader(
    title: String,
    count: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = PAGE_GUTTER,
                end = PAGE_GUTTER,
                top = 18.dp,
                bottom = 8.dp,
            ),
    ) {
        Box(
            modifier = Modifier
                .width(3.5.dp)
                .height(18.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.14f))
                .padding(horizontal = 8.dp, vertical = 2.dp),
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

/**
 * Primary-accented empty state for Search so all 6 RynzMusic themes shine
 * before a query is entered or when no matches are found.
 */
@Composable
private fun SearchEmptyAccentState(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PAGE_GUTTER, vertical = 18.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
                shape = RoundedCornerShape(18.dp),
            )
            .padding(top = 20.dp, bottom = 8.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f))
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.32f), CircleShape),
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp),
            )
        }
        MessageState(message)
    }
}

private data class SearchSection(val title: String?, val rows: List<SearchResult>)

/** The unfiltered page is useful only when its mixed result types are readable at a glance. */
private fun searchSections(rows: List<SearchResult>, filter: SearchFilter): List<SearchSection> {
    if (filter != SearchFilter.ALL) return listOf(SearchSection(null, rows))
    return listOf(
        SearchSection("Songs", rows.filterIsInstance<SearchResult.Track>()),
        SearchSection("Artists", rows.filterIsInstance<SearchResult.Browse>().filter { it.item.type == BrowseType.ARTIST }),
        SearchSection("Albums", rows.filterIsInstance<SearchResult.Browse>().filter { it.item.type == BrowseType.ALBUM }),
        SearchSection("Playlists", rows.filterIsInstance<SearchResult.Browse>().filter { it.item.type == BrowseType.PLAYLIST }),
        SearchSection("More", rows.filterIsInstance<SearchResult.Browse>().filter { it.item.type == BrowseType.OTHER }),
    ).filter { it.rows.isNotEmpty() }
}

/**
 * The Library source's page: the Local Music folder's tracks that match, as
 * plain song rows. An artist or album hit needs no row of its own — every
 * track under that name matches too, so they are already here.
 */
private fun LazyListScope.librarySearchResults(
    query: String,
    results: UiState<List<Song>>?,
    addons: Boolean,
    addonHistory: List<String>,
    onAddonHistoryClick: (String) -> Unit,
    onAddonHistoryRemove: (String) -> Unit,
    onAddonHistoryClear: () -> Unit,
    currentSong: Song?,
    isPlaying: Boolean,
    onSongClick: (List<Song>, Int) -> Unit,
    onSongLongPress: (Song) -> Unit,
    onSongSwipe: (Song) -> Unit,
    isBlocked: (Song) -> Boolean,
    onBlockedSongClick: (Song) -> Unit,
) {
    when (results) {
        null -> if (addons && query.isEmpty() && addonHistory.isNotEmpty()) {
            recentAddonSearches(addonHistory, onAddonHistoryClick, onAddonHistoryRemove, onAddonHistoryClear)
        } else item(key = "library:empty") {
            SearchEmptyAccentState(
                stringResource(if (addons) Res.string.search_addons_empty else Res.string.search_library_empty),
            )
        }
        UiState.Loading -> songListSkeleton(keyPrefix = "skeleton:search:library")
        is UiState.Error -> item(key = "library:error") { SearchEmptyAccentState(results.message) }
        is UiState.Success -> if (results.data.isEmpty()) {
            item(key = "library:no-match") {
                SearchEmptyAccentState(
                    if (addons) {
                        stringResource(Res.string.search_addons_no_results, query)
                    } else {
                        stringResource(Res.string.search_library_no_results, query)
                    },
                )
            }
        } else {
            val tracks = results.data
            val isCurrentRow = playingRowMatcher(tracks, currentSong)
            itemsIndexed(tracks, key = { index, song -> "${if (addons) "addon" else "library"}_${song.videoId}_$index" }) { index, song ->
                val blocked = isBlocked(song)
                SongRow(
                    song = song,
                    // Dimmed rather than hidden, so the listener can see the
                    // addon has the song and tap to learn why it won't play.
                    modifier = if (blocked) Modifier.alpha(BLOCKED_ROW_ALPHA) else Modifier,
                    onClick = {
                        if (blocked) {
                            onBlockedSongClick(song)
                        } else {
                            // The queue skips blocked rows, or playing through
                            // it would stop at the first one with an error.
                            val playable = tracks.filterNot(isBlocked)
                            onSongClick(playable, playable.indexOfFirst { it === song })
                        }
                    },
                    onLongPress = { onSongLongPress(song) },
                    onSwipeToQueue = { if (blocked) onBlockedSongClick(song) else onSongSwipe(song) },
                    isCurrent = isCurrentRow(song),
                    isPlaying = isPlaying && isCurrentRow(song),
                    searchPlayingStyle = true,
                    showSourceQuality = addons,
                )
                if (index < tracks.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = ROW_DIVIDER_INSET),
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
        }
    }
}

/** The All response carries its highest-confidence music hit as a promoted card. */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TopResultCard(
    song: Song,
    onPlay: () -> Unit,
    onPlaylist: () -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = PAGE_GUTTER, end = PAGE_GUTTER, top = 14.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.28f),
                shape = RoundedCornerShape(16.dp),
            )
            .longPressMenuClickable(onClick = onPlay, onLongClick = onLongPress)
            .contextClick(onLongPress)
            .padding(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(3.5.dp)
                    .height(14.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f))
                    .padding(horizontal = 8.dp, vertical = 2.dp),
            ) {
                Text(
                    text = "TOP RESULT",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = song.thumbnailUrl?.artworkAt(ROW_ART_PX),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.trackArtSize(72.dp, song.isVideo).clip(RoundedCornerShape(10.dp)),
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = song.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            RowMoreButton(
                onClick = onLongPress,
                tint = MaterialTheme.colorScheme.onSurface,
                iconSize = 24.dp,
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = onPlay,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                    contentColor = MaterialTheme.colorScheme.primary,
                ),
                border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                    brush = SolidColor(MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)),
                ),
            ) {
                Icon(Icons.Rounded.PlayArrow, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text(stringResource(Res.string.play))
            }
            OutlinedButton(
                onClick = onPlaylist,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
                border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                    brush = SolidColor(MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
                ),
            ) {
                Icon(Icons.AutoMirrored.Rounded.PlaylistAdd, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(6.dp))
                Text(stringResource(Res.string.playlist_action))
            }
        }
    }
}

/**
 * What YouTube would complete the half-typed query to, in place of the results
 * while it is being typed.
 *
 * The first row is the text as typed, put there by the view model rather than
 * taken from YouTube's answer, so running exactly what was asked for is always
 * the nearest row to the keyboard rather than something the thumb has to aim
 * past.
 */
private fun LazyListScope.searchSuggestions(
    suggestions: List<String>,
    onClick: (String) -> Unit,
    onFill: (String) -> Unit,
) {
    // This is a list-level inset rather than padding hidden inside the first
    // row. It keeps the gap under the field stable even when that row changes
    // its text or icon treatment.
    item(key = "suggestions:top-inset") { Spacer(Modifier.height(8.dp)) }
    // Skip the echo of the typed text (element 0) — it's already visible in
    // the search field itself — and cap at N so the list stays compact above
    // the playable-media cards.
    itemsIndexed(suggestions.drop(1).take(3), key = { _, term -> "suggest:$term" }) { _, term ->
        SuggestionRow(
            term = term,
            isQueryAction = false,
            onFill = { onFill(term) },
            onClick = { onClick(term) },
        )
    }
}

/**
 * One typeahead row: tap the text to search it, or the arrow to put it in the
 * field and carry on typing — the pair YouTube, Google and every mobile
 * keyboard's own suggestion strip use, and the reason a longer completion
 * isn't a dead end when it's only nearly right.
 */
@Composable
private fun SuggestionRow(
    term: String,
    isQueryAction: Boolean,
    onFill: (() -> Unit)?,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                start = PAGE_GUTTER,
                end = 8.dp,
                top = 6.dp,
                bottom = 6.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Rounded.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp),
        )
        Spacer(Modifier.width(16.dp))
        Text(
            // The first row is the deliberate action to search the exact text
            // in the field, not a server-provided completion. Naming it makes
            // the otherwise duplicated wording read as intentional.
            text = if (isQueryAction) """${stringResource(Res.string.search)} "$term"""" else term,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        if (onFill != null) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onFill),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Rounded.NorthWest,
                    contentDescription = stringResource(Res.string.recent_search_edit, term),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp),
                )
            }
        } else {
            // Match the arrow button's full touch target, not only its width.
            // A width-only spacer made the first row shorter than the ones
            // below, so its vertical rhythm looked visibly uneven.
            Spacer(Modifier.size(40.dp))
        }
    }
}

/**
 * Hybrid dropdown shown beneath text suggestions while typing: a horizontal
 * divider, then live media rows (cover art + title + subtitle) that play on
 * tap and open the song menu on long-press.
 */
private fun LazyListScope.searchTypeaheadDropdown(
    typeaheadResults: List<SearchResult>,
    onSongClick: (Song) -> Unit,
    onSongLongPress: ((Song) -> Unit)?,
    onBrowseClick: (BrowseItem) -> Unit,
    currentSong: Song?,
    isPlaying: Boolean,
) {
    val isCurrentRow = playingRowMatcher(
        typeaheadResults.mapNotNull { (it as? SearchResult.Track)?.song ?: (it as? SearchResult.TopTrack)?.song },
        currentSong,
    )
    item(key = "typeahead:divider") {
        HorizontalDivider(
            modifier = Modifier.padding(start = PAGE_GUTTER, end = PAGE_GUTTER),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
        )
    }
    items(typeaheadResults, key = { result ->
        when (result) {
            is SearchResult.Track -> "ta:track:${result.song.videoId}"
            is SearchResult.Browse -> "ta:browse:${result.item.browseId}"
            is SearchResult.TopTrack -> "ta:top:${result.song.videoId}"
        }
    }) { result ->
        when (result) {
            is SearchResult.Track -> TypeaheadSongRow(
                song = result.song,
                onClick = { onSongClick(result.song) },
                onLongPress = onSongLongPress?.let { { it(result.song) } },
                isCurrent = isCurrentRow(result.song),
                isPlaying = isPlaying && isCurrentRow(result.song),
            )
            is SearchResult.Browse -> BrowseRow(
                item = result.item,
                onClick = { onBrowseClick(result.item) },
                onLongPress = onSongLongPress?.let { { /* browse long-press not applicable */ } },
            )
            is SearchResult.TopTrack -> TypeaheadSongRow(
                song = result.song,
                onClick = { onSongClick(result.song) },
                onLongPress = onSongLongPress?.let { { it(result.song) } },
                isCurrent = isCurrentRow(result.song),
                isPlaying = isPlaying && isCurrentRow(result.song),
            )
        }
    }
}

/**
 * A single media row inside the typeahead dropdown: 52dp cover art, title,
 * and artist/album subtitle. Tap plays the track; long-press opens the menu.
 */
@Composable
private fun TypeaheadSongRow(
    song: Song,
    onClick: () -> Unit,
    onLongPress: (() -> Unit)? = null,
    isCurrent: Boolean = false,
    isPlaying: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .longPressMenuClickable(
                onClick = onClick,
                onLongClick = onLongPress,
                liftPadding = PaddingValues(horizontal = PAGE_GUTTER),
            )
            .contextClick(onLongPress)
            .padding(horizontal = PAGE_GUTTER, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.trackArtSize(52.dp, song.isVideo)) {
            AsyncImage(
                model = song.artworkAt(ROW_ART_PX),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(8.dp))
                    .thumbnailBorder(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            )
            if (isCurrent && isPlaying) SearchPlayingBars(Modifier.align(Alignment.Center))
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = listOfNotNull(song.artist).joinToString(" · "),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/**
 * What was searched for before, shown in place of the results while the field
 * is empty — the same spot Spotify and Apple Music put it, and the reason the
 * blank search page isn't just a sentence any more.
 */
private fun LazyListScope.recentSearches(
    history: List<SearchHistoryEntity>,
    currentSong: Song?,
    isPlaying: Boolean,
    onClick: (SearchHistoryEntity) -> Unit,
    onSongLongPress: (Song) -> Unit,
    onRemove: (String) -> Unit,
    onClear: () -> Unit,
    onSongSwipe: (Song) -> Unit,
) {
    item(key = "recent:header") {
        RecentSearchesHeader(onClear = onClear)
    }
    items(history, key = { "recent:${it.id}" }) { entity ->
        // A recent track gets the same menu and swipe as a result row, so it can
        // be queued without interrupting what's playing. The ids an old build
        // saved as raw queries ("q:…") have no track behind them.
        val song = entity.toSong()
        RecentSearchEntityRow(
            entity = entity,
            isCurrent = entity.entityType == EntityType.TRACK && entity.id == currentSong?.videoId,
            isPlaying = isPlaying,
            onClick = { onClick(entity) },
            onLongPress = song?.let { { onSongLongPress(it) } },
            onRemove = { onRemove(entity.id) },
            onSwipeToQueue = song?.let { { onSongSwipe(it) } },
        )
    }
}

@Composable
private fun RecentSearchesHeader(onClear: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PAGE_GUTTER, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(22.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = stringResource(Res.string.recent_searches),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = stringResource(Res.string.clear),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 50))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.28f), RoundedCornerShape(percent = 50))
                .clickable(onClick = onClear)
                .padding(horizontal = 12.dp, vertical = 5.dp),
        )
    }
}

/** The Addons tab's recents: just the words that were searched, no song behind them. */
private fun LazyListScope.recentAddonSearches(
    history: List<String>,
    onClick: (String) -> Unit,
    onRemove: (String) -> Unit,
    onClear: () -> Unit,
) {
    item(key = "addon-recent:header") {
        RecentSearchesHeader(onClear = onClear)
    }
    items(history, key = { "addon-recent:$it" }) { term ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(term) }
                .padding(start = PAGE_GUTTER, end = 8.dp, top = 6.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Rounded.History,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(22.dp),
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = term,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { onRemove(term) },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Rounded.Close,
                    contentDescription = stringResource(Res.string.recent_search_remove, term),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}

/**
 * Spotify-style entity row: square thumbnail, bold title, subtitle with type,
 * and a removal button. Tapping navigates to the entity or plays it directly;
 * holding a track opens its menu.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RecentSearchEntityRow(
    entity: SearchHistoryEntity,
    isCurrent: Boolean,
    isPlaying: Boolean,
    onClick: () -> Unit,
    onLongPress: (() -> Unit)?,
    onRemove: () -> Unit,
    onSwipeToQueue: (() -> Unit)? = null,
) {
    SwipeToQueueBox(
        onSwipeToQueue = onSwipeToQueue,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .longPressMenuClickable(
                    onClick = onClick,
                    onLongClick = onLongPress,
                    liftPadding = PaddingValues(start = PAGE_GUTTER, end = 8.dp),
                )
                .contextClick(onLongPress)
                .padding(start = PAGE_GUTTER, end = 8.dp, top = 6.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.size(52.dp)) {
                AsyncImage(
                    model = entity.artworkUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .thumbnailBorder(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                )
                if (isCurrent && isPlaying) {
                    SearchPlayingBars(Modifier.align(Alignment.Center))
                }
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = entity.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = entity.subtitle.ifBlank { entity.entityType.name.lowercase(Locale.ROOT).replaceFirstChar { it.uppercase(Locale.ROOT) } },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onRemove),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Rounded.Close,
                    contentDescription = stringResource(Res.string.recent_search_remove, entity.title),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BrowseRow(item: BrowseItem, onClick: () -> Unit, onLongPress: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .longPressMenuClickable(
                onClick = onClick,
                onLongClick = onLongPress,
                liftPadding = PaddingValues(horizontal = PAGE_GUTTER),
            )
            .contextClick(onLongPress)
            .padding(horizontal = PAGE_GUTTER, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = item.thumbnailUrl.artworkAt(ROW_ART_PX),
            contentDescription = null,
            modifier = Modifier
                .size(52.dp)
                .clip(
                    if (item.type == BrowseType.ARTIST) CircleShape
                    else RoundedCornerShape(8.dp),
                )
                .thumbnailBorder(
                    if (item.type == BrowseType.ARTIST) CircleShape
                    else RoundedCornerShape(8.dp),
                )
                .background(MaterialTheme.colorScheme.surfaceVariant),
        )
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.subtitle.ifBlank { item.type.name.lowercase(Locale.ROOT).replaceFirstChar { it.uppercase(Locale.ROOT) } },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/** How far an addon row the quality setting won't play is dimmed; Material's disabled level. */
private const val BLOCKED_ROW_ALPHA = 0.38f
