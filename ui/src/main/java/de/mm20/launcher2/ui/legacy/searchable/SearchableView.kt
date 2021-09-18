package de.mm20.launcher2.ui.legacy.searchable

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.transition.*
import de.mm20.launcher2.search.data.*
import de.mm20.launcher2.transition.TextResize
import de.mm20.launcher2.ui.legacy.data.InformationText
import de.mm20.launcher2.ui.legacy.search.*
import de.mm20.launcher2.ui.legacy.transition.LauncherCards
import de.mm20.launcher2.ui.legacy.transition.LauncherIconViewTransition
import de.mm20.launcher2.ui.legacy.view.AspectRationImageView

@SuppressLint("ViewConstructor")
open class SearchableView(context: Context, representation: Int) : FrameLayout(context) {

    var searchable: Searchable? = null
        set(value) {
            field = value
            updateRepresentation(null)
        }


    private var defaultRepresentation = representation

    var representation = representation
        set(value) {
            val oldVal = field
            field = value
            if (oldVal != value) {
                onRepresentationChange(oldVal, value)
            }
            updateRepresentation(oldVal)
        }
    var onRepresentationChange: (Int, Int) -> Unit = { _, _ -> }

    init {
        clipChildren = false
        updateRepresentation(null)
    }

    internal open fun updateRepresentation(previousRepresentation: Int?) {
        when (representation) {
            REPRESENTATION_FULL -> setFullRepresentation(previousRepresentation)
            REPRESENTATION_LIST -> setListRepresentation(previousRepresentation)
            REPRESENTATION_GRID -> setGridRepresentation(previousRepresentation)
            else -> throw IllegalArgumentException("Must be REPRESENTATION_GRID, REPRESENTATION_LIST or REPRESENTATION_FULL")
        }
    }


    private fun setGridRepresentation(previousRepresentation: Int?) {
        val searchable = searchable
        if (searchable == null) {
            removeAllViews()
            return
        }
        val scene = BasicGridRepresentation().getScene(this, searchable, null)
        applyScene(scene)
    }

    private fun setListRepresentation(previousRepresentation: Int?) {
        val searchable = searchable
        if (searchable == null) {
            removeAllViews()
            return
        }
        val representation = when (searchable) {
            is File -> FileListRepresentation()
            is Contact -> ContactListRepresentation()
            is CalendarEvent -> CalendarListRepresentation()
            is Website -> WebsiteListRepresentation()
            is Wikipedia -> WikipediaListRepresentation()
            is InformationText -> InformationListRepresentation()
            is MissingPermission -> PermissionListRepresentation()
            else -> return
        }
        applyScene(representation.getScene(this, searchable, previousRepresentation))
    }


    private fun setFullRepresentation(previousRepresentation: Int?) {
        val searchable = searchable
        if (searchable == null) {
            removeAllViews()
            return
        }
        val representation = when (searchable) {
            is Application -> ApplicationDetailRepresentation()
            is Website -> WebsiteDetailRepresentation()
            is File -> FileDetailRepresentation()
            is Contact -> ContactDetailRepresentation()
            is CalendarEvent -> CalendarDetailRepresentation()
            is Wikipedia -> WikipediaDetailRepresentation()
            is AppShortcut -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                AppShortcutDetailRepresentation()
            } else {
                return
            }
            else -> return
        }
        applyScene(representation.getScene(this, searchable, previousRepresentation))
    }

    private fun applyScene(scene: Scene) {
        val transition = TransitionSet().apply {
            addTransition(ChangeBounds().setInterpolator(DecelerateInterpolator()).excludeTarget(
                AspectRationImageView::class.java, true))
            addTransition(LauncherIconViewTransition())
            addTransition(TextResize())
            addTransition(LauncherCards())
            ordering = TransitionSet.ORDERING_TOGETHER
            setMatchOrder(Transition.MATCH_NAME, Transition.MATCH_ID)
        }
        TransitionManager.go(scene, transition)
    }

    var onBack: (() -> Unit)? = null

    fun back() {
        if (!hasBack()) {
            onBack?.invoke()
            return
        }
        representation = defaultRepresentation
    }

    fun hasBack(): Boolean {
        return defaultRepresentation != REPRESENTATION_FULL
    }

    companion object {
        const val REPRESENTATION_GRID = 0
        const val REPRESENTATION_LIST = 1
        const val REPRESENTATION_FULL = 2

        fun getView(context: Context, searchable: Searchable?, representation: Int): SearchableView {
            return SearchableView(context, representation)
        }
    }
}