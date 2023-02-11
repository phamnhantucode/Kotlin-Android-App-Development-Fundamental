package com.learning.rxandroidtutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observable = getObservableUsers()
        val observer = getObserverUser()
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    }

    private fun getObserverUser(): Observer<User> {
        return object: Observer<User> {
            override fun onSubscribe(d: Disposable) {
                Log.i("TestObserver", "onSubscribe")
            }

            override fun onNext(t: User) {
                Log.i("TestObserver", "onNext: " + "${t.age}")
            }

            override fun onError(e: Throwable) {
                Log.i("TestObserver", "onError")
            }

            override fun onComplete() {
                Log.i("TestObserver", "onComplete")
            }

        }
    }

    private fun getObservableUsers() : Observable<User> {
        val listUsers = getListUser()
        return Observable.create {
            if (listUsers.isNullOrEmpty()) it.onError(Exception())

            for (user in listUsers) {
                if (!it.isDisposed) {
                    it.onNext(user)
                }
            }

            if (!it.isDisposed) it.onComplete()
        }
    }

    private fun getListUser(): MutableList<User> {
        val list = ArrayList<User>()
        list.add(User("User 1", 1))
        list.add(User("User 2", 2))
        list.add(User("User 3", 3))
        list.add(User("User 4", 4))
        list.add(User("User 5", 5))
        list.add(User("User 6", 6))
        list.add(User("User 7", 7))
        return list
    }
}

data class User(var name: String, var age: Int)