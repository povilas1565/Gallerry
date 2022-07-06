package com.example.gallerry.Uri.activity

import android.Manifest
import android.os.Bundle
import com.example.gallerry.R
import com.example.gallerry.Uri.adapters.FolderAdapter
import com.example.gallerry.Uri.decorators.GalleryDecorator
import com.example.gallerry.uri.Uri.listeners.FileSelectionListener
import com.example.gallerry.utils.getAllImages
import com.example.gallerry.utils.sortImagesByFolder
import java.io.File

class GalleryLandingActivity: BaseActivity(), FileSelectionListener {

    private val mAdapter = FolderAdapter(this@GalleryLandingActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        setupUi()
    }

    private fun setupUi() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(GalleryDecorator(this))
        recyclerView.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
    }

    private fun checkPermissions() {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, getString(R.string.permission_rational_read_external), REQUEST_STORAGE_READ_ACCESS_PERMISSION)
            return
        } else if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.permission_rational_read_external), REQUEST_STORAGE_WRITE_ACCESS_PERMISSION)
            return
        } else if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            fetchAllMediaFiles()
        }
    }

    private fun fetchAllMediaFiles() {
        compositeDisposable.add(
            Observable.just(sortImagesByFolder(getAllImages(this)))
                .subscribe(
                    { filesMap -> mAdapter.setItems(filesMap) },
                    { throwable: Throwable? -> handleError(throwable) }
                )
        )
    }


    override fun onFileSelected(file: File) = startActivity(GalleryBrowseFolderActivity.newIntent(this@GalleryLandingActivity, file))
}
