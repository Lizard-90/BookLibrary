package com.example.booklibrary.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booklibrary.CleanArchitectureBlueprintsApplication
import com.example.booklibrary.LayoutUtils
import com.example.booklibrary.entities.BookWithStatus
import com.example.booklibrary.R
import com.example.booklibrary.databinding.FragmentBooksBinding

class BooksFragment : Fragment() {
    private lateinit var booksAdapter: BookAdapter

    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!

    private val booksViewModel: BooksViewModel by viewModels {
        Log.i("KEK", requireActivity().application.toString())
        BooksViewModel.BooksViewModelFactory(
            ((requireActivity().application) as CleanArchitectureBlueprintsApplication).getBooksUseCase,
            ((requireActivity().application) as CleanArchitectureBlueprintsApplication).getBookmarksUseCase,
            ((requireActivity().application) as CleanArchitectureBlueprintsApplication).bookmarkBooksUseCase,
            ((requireActivity().application) as CleanArchitectureBlueprintsApplication).unbookmarkBookUseCase,
            ((requireActivity().application) as CleanArchitectureBlueprintsApplication).bookWithStatusMapper,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        booksAdapter = BookAdapter(requireContext(), object : BookAdapter.ActionClickListener {
            override fun bookmark(book: BookWithStatus) {
                booksViewModel.bookmark(book)
            }

            override fun unbookmark(book: BookWithStatus) {
                booksViewModel.unbookmark(book)
            }
        })

        booksViewModel.getBooks("Robert C. Martin")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_books, container, false)
        _binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        booksViewModel.books.observe(viewLifecycleOwner) {
            booksAdapter.submitUpdate(it)
        }

        booksViewModel.dataLoading.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> LayoutUtils.crossFade(binding.pbLoading, binding.rvBooks)
                false -> LayoutUtils.crossFade(binding.rvBooks, binding.pbLoading)
            }
        }

        binding.rvBooks.apply {
            layoutManager =
                GridLayoutManager(requireContext(), COLUMNS_COUNT)
            adapter = booksAdapter
        }

        booksViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                getString(R.string.an_error_has_occurred, it),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val COLUMNS_COUNT = 2
    }
}