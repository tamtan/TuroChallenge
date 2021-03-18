package vinh.tan.tam.com.myapplication.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import vinh.tan.tam.com.myapplication.R
import vinh.tan.tam.com.myapplication.databinding.ActivityMainBinding
import vinh.tan.tam.com.myapplication.model.Business
import vinh.tan.tam.com.myapplication.network.RetrofitFactory
import vinh.tan.tam.com.myapplication.network.SearchService
import vinh.tan.tam.com.myapplication.network.Status
import vinh.tan.tam.com.myapplication.viewmodel.MainViewModel
import vinh.tan.tam.com.myapplication.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListAdapter
    var list = ArrayList<Business>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.mainActivity = this

        setupViewModel()

        setupUI()

        setUpObserver()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(RetrofitFactory.retrofit.create(SearchService::class.java))
        ).get(MainViewModel::class.java)
        mainBinding.lifecycleOwner = this
        mainBinding.viewModel = viewModel
    }

    private fun setupUI() {

        mainBinding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainBinding.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if ((recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == (list.size - 1)) {
                    fetchdata(true)
                }
            }
        })

        mainBinding.recyclerView.itemAnimator = DefaultItemAnimator()

        adapter = ListAdapter(list)
        mainBinding.recyclerView.adapter = adapter
    }

    private fun setUpObserver() {
        viewModel.resoure.observe(this, Observer {

            it?.let { resource ->
                when (resource.status) {

                    Status.SUCCESS -> {
                        showProgress(false)
                        Log.d("tanvinhtam", "success")

                        resource.data?.let { response ->
                            retrieveList(
                                response.body()?.businesses ?: ArrayList<Business>()
                            )
                            Log.d("tanvinhtam", response.toString())
                        }
                    }
                    Status.ERROR -> {
                        showProgress(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        Log.d("tanvinhtam", it.message)
                    }
                    Status.LOADING -> {
                        Log.d("tanvinhtam", "loading")
                        showProgress(true)
                    }
                }
            }
        })
        viewModel.isLoading.observe(this, Observer() {
            if (it) hideKeyboard(this)
        })
        fetchdata(false)
    }

    private fun fetchdata(isLoadMore: Boolean) {
        viewModel.search(
            viewModel.item,
            viewModel.location,
            viewModel.latitude,
            viewModel.longitude,
            viewModel.limit,
            if (isLoadMore) viewModel.offset + 1 else 0,
            viewModel.sort_by,
            isLoadMore
        )
    }

    private fun retrieveList(list: List<Business>) {
        if (list.size > 0) {
            list.forEach {
                Log.d("tanvinhtam", it.name)
            }
            if (!viewModel.isLoadMore) {
                this.list.clear()
            }
            this.list.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }


    private fun showProgress(status: Boolean) {
        if (status) {
            mainBinding.showProgress.visibility = View.VISIBLE
        } else {
            mainBinding.showProgress.visibility = View.GONE
        }
    }
    fun hideKeyboard(activity: Activity) {
        val inputManager: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            // check if no view has focus:
            val view = activity.currentFocus
            if (view != null) {
                inputManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}