package com.ravi.practicaltaskmvvm.ui.fragment.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ravi.practicaltaskmvvm.R
import com.ravi.practicaltaskmvvm.common.BaseFragment
import com.ravi.practicaltaskmvvm.data.model.RepositoryEntity
import com.ravi.practicaltaskmvvm.databinding.FragmentDetailsBinding
import com.ravi.practicaltaskmvvm.ui.fragment.RepositoryViewModel
import com.ravi.practicaltaskmvvm.utils.ext.showToast
import com.ravi.practicaltaskmvvm.utils.roundToThousands
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding
        get() = FragmentDetailsBinding::inflate

    private val viewModel: RepositoryViewModel by activityViewModels()

    private val navArgs: DetailsFragmentArgs by navArgs()

    override fun setup() {
        setupUI(navArgs.repositoryEntity)
    }


    private fun setupUI(repository: RepositoryEntity) {
        binding?.apply {
            repositoryName.text = repository.name
            countNameTextView.text =roundToThousands(repository.stargazersCount)
            updateBookmarkIcon(repository.bookmarked)

            bookmarkImageView.setOnClickListener {
                repository.bookmarked = !repository.bookmarked
                viewModel.toggleBookmark(repository)
                updateBookmarkIcon(repository.bookmarked)
                val messageResId = if (repository.bookmarked) {
                    R.string.added_to_bookmarks
                } else {
                    R.string.removed_from_bookmarks
                }
                context?.showToast(getString(messageResId))
            }
        }
    }

    private fun updateBookmarkIcon(isBookmarked: Boolean) {
        val iconRes = if (isBookmarked) {
            R.drawable.ic_select_bookmark
        } else {
            R.drawable.ic_unselect_bookmark
        }
        binding?.bookmarkImageView?.setImageResource(iconRes)
    }
}