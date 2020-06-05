package com.huxh.mvvmdemo.tasks

import com.fmt.launch.starter.task.Task
import com.huxh.mvvmdemo.article.ArticleRepository
import com.huxh.mvvmdemo.article.ArticleViewModel
import com.huxh.mvvmdemo.article.net.ArticleApi
import com.huxh.mvvmdemo.data.dao.articleDao
import com.huxh.mvvmdemo.data.net.ArticleServer
import com.huxh.mvvmdemo.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class InitKoInTask : Task() {
    override fun run() {
        startKoin {
            modules(appModules)
        }
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
//    viewModel { ReceivedEventViewModel(get()) }
//    viewModel { ReposViewModel(get()) }
    viewModel { ArticleViewModel(get()) }
}

val reposModule = module {
    //factory 每次注入时都重新创建一个新的对象
//    factory { ReposRepository(get()) }
    factory { ArticleRepository(get(), get()) }
}

val remoteModule = module {
    //single 单列注入
//    single<ReposApi> { ReposService }

    single<ArticleApi> { ArticleServer }
}

val localModule = module {
    single { articleDao }
}


val appModules = listOf(viewModelModule, reposModule, remoteModule, localModule)