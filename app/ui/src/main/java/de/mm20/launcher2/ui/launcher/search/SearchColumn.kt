package de.mm20.launcher2.ui.launcher.search

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mm20.launcher2.search.AppShortcut
import de.mm20.launcher2.search.Application
import de.mm20.launcher2.search.Article
import de.mm20.launcher2.search.CalendarEvent
import de.mm20.launcher2.search.Contact
import de.mm20.launcher2.search.File
import de.mm20.launcher2.search.Location
import de.mm20.launcher2.search.SavableSearchable
import de.mm20.launcher2.search.Website
import de.mm20.launcher2.ui.component.LauncherCard
import de.mm20.launcher2.ui.component.PartialLauncherCard
import de.mm20.launcher2.ui.launcher.search.apps.AppResults
import de.mm20.launcher2.ui.launcher.search.calculator.CalculatorResults
import de.mm20.launcher2.ui.launcher.search.calendar.CalendarResults
import de.mm20.launcher2.ui.launcher.search.common.grid.GridItem
import de.mm20.launcher2.ui.launcher.search.common.list.ListItem
import de.mm20.launcher2.ui.launcher.search.contacts.ContactResults
import de.mm20.launcher2.ui.launcher.search.favorites.SearchFavorites
import de.mm20.launcher2.ui.launcher.search.favorites.SearchFavoritesVM
import de.mm20.launcher2.ui.launcher.search.files.FileResults
import de.mm20.launcher2.ui.launcher.search.filters.SearchFilters
import de.mm20.launcher2.ui.launcher.search.location.LocationResults
import de.mm20.launcher2.ui.launcher.search.shortcut.ShortcutResults
import de.mm20.launcher2.ui.launcher.search.unitconverter.UnitConverterItem
import de.mm20.launcher2.ui.launcher.search.unitconverter.UnitConverterResults
import de.mm20.launcher2.ui.launcher.search.website.WebsiteItem
import de.mm20.launcher2.ui.launcher.search.website.WebsiteResults
import de.mm20.launcher2.ui.launcher.search.wikipedia.ArticleItem
import de.mm20.launcher2.ui.launcher.search.wikipedia.ArticleResults
import de.mm20.launcher2.ui.launcher.sheets.HiddenItemsSheet
import de.mm20.launcher2.ui.launcher.sheets.LocalBottomSheetManager
import de.mm20.launcher2.ui.locals.LocalCardStyle
import de.mm20.launcher2.ui.locals.LocalGridSettings
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SearchColumn(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState(),
    reverse: Boolean = false,
    userScrollEnabled: Boolean = true,
) {

    val columns = LocalGridSettings.current.columnCount
    val context = LocalContext.current

    val viewModel: SearchVM = viewModel()

    val favoritesVM: SearchFavoritesVM = viewModel()
    val favorites by favoritesVM.favorites.collectAsState(emptyList())

    val hideFavs by viewModel.hideFavorites
    val favoritesEnabled by viewModel.favoritesEnabled.collectAsState(false)
    val apps by viewModel.appResults
    val workApps by viewModel.workAppResults
    val appShortcuts by viewModel.appShortcutResults
    val contacts by viewModel.contactResults
    val files by viewModel.fileResults
    val events by viewModel.calendarResults
    val unitConverter by viewModel.unitConverterResults
    val calculator by viewModel.calculatorResults
    val wikipedia by viewModel.articleResults
    val locations by viewModel.locationResults
    val website by viewModel.websiteResults
    val hiddenResults by viewModel.hiddenResults

    val bestMatch by viewModel.bestMatch

    val isSearchEmpty by viewModel.isSearchEmpty

    val missingCalendarPermission by viewModel.missingCalendarPermission.collectAsState(false)
    val missingShortcutsPermission by viewModel.missingAppShortcutPermission.collectAsState(false)
    val missingContactsPermission by viewModel.missingContactsPermission.collectAsState(false)
    val missingLocationPermission by viewModel.missingLocationPermission.collectAsState(false)
    val missingFilesPermission by viewModel.missingFilesPermission.collectAsState(false)

    val pinnedTags by favoritesVM.pinnedTags.collectAsState(emptyList())
    val selectedTag by favoritesVM.selectedTag.collectAsState(null)
    val tagsScrollState = rememberScrollState()
    val favoritesEditButton by favoritesVM.showEditButton.collectAsState(false)
    val favoritesTagsExpanded by favoritesVM.tagsExpanded.collectAsState(false)

    var showWorkProfileApps by remember { mutableStateOf(false) }
    val separateWorkProfile by viewModel.separateWorkProfile.collectAsState(true)
    val visibleApps by remember {
        derivedStateOf {
            when {
                !separateWorkProfile -> (apps + workApps).sorted()
                workApps.isEmpty() -> apps
                apps.isEmpty() -> workApps
                showWorkProfileApps -> workApps
                else -> apps
            }
        }
    }

    var expandedCategory: SearchCategory? by remember(isSearchEmpty) { mutableStateOf(null) }

    var selectedContactIndex: Int by remember(contacts) { mutableIntStateOf(-1) }
    var selectedFileIndex: Int by remember(files) { mutableIntStateOf(-1) }
    var selectedCalendarIndex: Int by remember(events) { mutableIntStateOf(-1) }
    var selectedLocationIndex: Int by remember(events) { mutableIntStateOf(-1) }
    var selectedShortcutIndex: Int by remember(events) { mutableIntStateOf(-1) }
    var selectedArticleIndex: Int by remember(events) { mutableIntStateOf(-1) }
    var selectedWebsiteIndex: Int by remember(events) { mutableIntStateOf(-1) }

    val showFilters by viewModel.showFilters

    AnimatedContent(
        showFilters,
        modifier = modifier.padding(horizontal = 8.dp),
    ) {
        if (it) {
            BackHandler {
                viewModel.showFilters.value = false
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = if (reverse) Alignment.BottomCenter else Alignment.TopCenter,
            ) {
                SearchFilters(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                            MaterialTheme.shapes.medium
                        )
                        .padding(12.dp),
                    filters = viewModel.filters.value,
                    onFiltersChange = {
                        viewModel.setFilters(it)
                    }
                )
            }
        } else {
            LazyColumn(
                state = state,
                userScrollEnabled = userScrollEnabled,
                contentPadding = paddingValues,
                reverseLayout = reverse,
            ) {
                if (!hideFavs && favoritesEnabled) {
                    SearchFavorites(
                        favorites = favorites,
                        selectedTag = selectedTag,
                        pinnedTags = pinnedTags,
                        tagsExpanded = favoritesTagsExpanded,
                        onSelectTag = { favoritesVM.selectTag(it) },
                        reverse = reverse,
                        onExpandTags = {
                            favoritesVM.setTagsExpanded(it)
                        },
                        editButton = favoritesEditButton
                    )
                }

                AppResults(
                    apps = visibleApps,
                    showTabs = separateWorkProfile && apps.isNotEmpty() && workApps.isNotEmpty(),
                    highlightedItem = bestMatch as? Application,
                    selectedTab = if (showWorkProfileApps) 1 else 0,
                    onSelectedTabChange = { showWorkProfileApps = it == 1 },
                    columns = columns,
                    reverse = reverse
                )

                if (!isSearchEmpty) {

                    ShortcutResults(
                        shortcuts = appShortcuts,
                        missingPermission = missingShortcutsPermission,
                        onPermissionRequest = {
                            viewModel.requestAppShortcutPermission(context as AppCompatActivity)
                        },
                        onPermissionRequestRejected = {
                            viewModel.disableAppShortcutSearch()
                        },
                        reverse = reverse,
                        selectedIndex = selectedShortcutIndex,
                        onSelect = { selectedShortcutIndex = it },
                        highlightedItem = bestMatch as? AppShortcut,
                    )

                    UnitConverterResults(
                        converters = unitConverter,
                        reverse = reverse,
                        truncate = expandedCategory != SearchCategory.UnitConverter,
                        onShowAll = {
                            expandedCategory = SearchCategory.UnitConverter
                        }
                    )

                    CalculatorResults(
                        calculator,
                        reverse = reverse
                    )

                    CalendarResults(
                        events = events,
                        missingPermission = missingCalendarPermission,
                        onPermissionRequest = {
                            viewModel.requestCalendarPermission(context as AppCompatActivity)
                        },
                        onPermissionRequestRejected = {
                            viewModel.disableCalendarSearch()
                        },
                        reverse = reverse,
                        selectedIndex = selectedCalendarIndex,
                        onSelect = { selectedCalendarIndex = it },
                        highlightedItem = bestMatch as? CalendarEvent,
                    )

                    ContactResults(
                        contacts = contacts,
                        missingPermission = missingContactsPermission,
                        onPermissionRequest = {
                            viewModel.requestContactsPermission(context as AppCompatActivity)
                        },
                        onPermissionRequestRejected = {
                            viewModel.disableContactsSearch()
                        },
                        reverse = reverse,
                        selectedIndex = selectedContactIndex,
                        onSelect = { selectedContactIndex = it },
                        highlightedItem = bestMatch as? Contact,
                    )

                    LocationResults(
                        locations = locations,
                        missingPermission = missingLocationPermission,
                        onPermissionRequest = {
                            viewModel.requestLocationPermission(context as AppCompatActivity)
                        },
                        onPermissionRequestRejected = {
                            viewModel.disableLocationSearch()
                        },
                        reverse = reverse,
                        selectedIndex = selectedLocationIndex,
                        onSelect = { selectedLocationIndex = it },
                        highlightedItem = bestMatch as? Location,
                    )
                    ArticleResults(
                        articles = wikipedia,
                        selectedIndex = selectedArticleIndex,
                        onSelect = { selectedArticleIndex = it },
                        highlightedItem = bestMatch as? Article,
                        reverse = reverse,
                    )
                    WebsiteResults(
                        websites = website,
                        selectedIndex = selectedWebsiteIndex,
                        onSelect = { selectedWebsiteIndex = it },
                        highlightedItem = bestMatch as? Website,
                        reverse = reverse,
                    )
                    FileResults(
                        files = files,
                        onPermissionRequest = {
                            viewModel.requestFilesPermission(context as AppCompatActivity)
                        },
                        onPermissionRequestRejected = {
                            viewModel.disableFilesSearch()
                        },
                        reverse = reverse,
                        highlightedItem = bestMatch as? File,
                        missingPermission = missingFilesPermission,
                        selectedIndex = selectedFileIndex,
                        onSelect = {
                            selectedFileIndex = it
                        }
                    )
                }
            }
        }

    }


    val sheetManager = LocalBottomSheetManager.current
    if (sheetManager.hiddenItemsSheetShown.value) {
        HiddenItemsSheet(
            items = hiddenResults,
            onDismiss = { sheetManager.dismissHiddenItemsSheet() })
    }
}


fun LazyListScope.SingleResult(
    highlight: Boolean = false,
    content: @Composable (() -> Unit)?
) {
    if (content == null) return
    item {
        LauncherCard(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                ),
            color = if (highlight) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surface.copy(LocalCardStyle.current.opacity)
        ) {
            content()
        }
    }
}

enum class SearchCategory {
    Apps,
    Calculator,
    Calendar,
    Contacts,
    Files,
    UnitConverter,
    Wikipedia,
    Website,
    Location,
    Shortcut,
}