//package com.opechain.wallet.base.mvp
//
//import com.opechain.wallet.base.BaseFragment
//import com.opechain.wallet.base.mvp.java.IView
//import rx.Subscription
//
///**
// * 类的描述：MVP fragment 基类
// * 创建人员：hewking
// * 创建时间：2018/2/9
// * 修改人员：hewking
// * 修改时间：2018/2/9
// * 修改备注：
// * Version: 1.0.0
// */
//abstract class BasePresenterFragment<P : BaseIPresenter<*>> : BaseFragment() ,BaseIView{
//
//    var presenter : P? = null
//
//    fun bindPresenter(presenter : P){
//        this.presenter = presenter
//        presenter.bindView(this)
//    }
//
//    fun addSubscription(subscription : Subscription){
//        add(subscription)
//    }
//
//}
