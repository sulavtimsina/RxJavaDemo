package com.sulav.rxjavademo;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;

import com.sulav.rxjavademo.model.TaskModel;
import com.sulav.rxjavademo.repository.TaskDataSource;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Observable<String> observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");
//        func1();
//        func2();
//        func3();
//        func4();
        func5();

    }

    /**
     * Will create Observable using just
     */
    void func1() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };

        Observable.just("hello")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * Will create Observable using create()
     */
    void func2(){
        // This taskmodel will be emitted by the Observable
        final TaskModel taskModel = new TaskModel("Eat Food", false, 2);
        // Create Observable
        Observable<TaskModel> singleTaskObservable = Observable
                .create(new ObservableOnSubscribe<TaskModel>() {
                    @Override
                    public void subscribe(ObservableEmitter<TaskModel> emitter) throws Exception {
                        if(!emitter.isDisposed()){
                            emitter.onNext(taskModel);
                            emitter.onComplete();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        // Subscribe to the Observable created above and receive the emitted taskModel object
        singleTaskObservable.subscribe(new Observer<TaskModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(TaskModel taskModel) {
                Log.d(TAG, "onNext: "+taskModel.toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });

    }

    /**
     * Will create Observable which emits list of Objects using create()
     */
    void func3(){
        Log.d(TAG, "func3: Calling createTaskListSlow Now....");
        final List<TaskModel> taskModelList = TaskDataSource.createTaskListSlow();
        //Create Observable
        Observable<TaskModel> taskListObservable = Observable
                .create(new ObservableOnSubscribe<TaskModel>() {
                    @Override
                    public void subscribe(ObservableEmitter<TaskModel> emitter) throws Exception {
                        for(TaskModel task: taskModelList) {
                            if (!emitter.isDisposed()) {
                                emitter.onNext(task);
                            }

                        }
                        if(!emitter.isDisposed())
                            emitter.onComplete();
                    }
                });

        taskListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TaskModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(TaskModel taskModel) {
                        Log.d(TAG, "onNext: "+taskModel.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /**
     * Will Create Observable using just. Just can emit maximum 4 objects
     */
    void func4(){
        Observable.just("one", "two", "three", "four")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: "+s);
        
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                    }
                });
    }

    /**
     * Uses range and repeat
     * range will generate range of objects from minimum(inclusive) to maximum(exclusive) value
     * repeat must be used in conjunction with another operator, it will repeat the emission n times
     */
    void func5(){
        Observable.range(0,4)
                .repeat(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: "+integer.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
