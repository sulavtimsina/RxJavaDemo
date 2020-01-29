package com.sulav.rxjavademo;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sulav.rxjavademo.model.TaskModel;
import com.sulav.rxjavademo.repository.TaskDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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
//        func5();
//        func6();
//        func7();
//        func8();
//        func9();
    }

    /**
     * Will create Observable using just
     */
    public void func1(View view) {
        Observer<String> observer = new Observer<String>() {
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
    public void func2(View view){
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
    public void func3(View view){
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
    public void func4(View view){
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
    public void func5(View view){
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

    /**
     * Interval operator returns an Observable that emits an infinite sequence of ascending integers, with a constant interval of time of your choosing between emissions.
     * TakeWhile operator mirror items emitted by an Observable until a specified condition becomes false
     */
    public void func6(View view){
        Observable.interval(1, TimeUnit.SECONDS)
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong <= 5;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: "+ aLong);

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
     * FromIterable and FromCallable
     */
    public void func7(View view){
        List<String> myList = new ArrayList<>();
        myList.add("tom");
        myList.add("dick");
        myList.add("harry");
        myList.add("adam");
        myList.add("pop");

        Observable<String> myObservable = Observable.fromIterable(myList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

                myObservable.subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: to String emission");

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
                        Log.d(TAG, "onComplete: of String emission");

                    }
                });

        /*From Callable*/
        Observable<List<TaskModel>> taskModelObservable = Observable.fromCallable(new Callable<List<TaskModel>>() {
            @Override
            public List<TaskModel> call() throws Exception {
                return TaskDataSource.createTaskListSlow();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskModelObservable.subscribe(new Observer<List<TaskModel>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: to Callable");

            }

            @Override
            public void onNext(List<TaskModel> taskModels) {
                Log.d(TAG, "onNext: "+taskModels.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * Filter String Observable
     */
    public void func8(View view){
        List<String> myList = new ArrayList<>();
        myList.add("tom");
        myList.add("dick");
        myList.add("harry");
        myList.add("adam");
        myList.add("pop");

        Observable<String> myObservable = Observable.fromIterable(myList)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        if(s.length() > 3)
                            return true;
                        return false;
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        myObservable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

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

            }
        });

    }

    /**
     * Filter custom object
     */
    public void func9(View view){
        List<TaskModel> myList = TaskDataSource.createTasksList();


        Observable<TaskModel> listObservable = Observable.fromIterable(myList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<TaskModel>() {
            @Override
            public boolean test(TaskModel taskModel) throws Exception {
                if(taskModel.getCompleted())
                    return true;
                return false;
            }
        });

        listObservable.subscribe(new Observer<TaskModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TaskModel taskModel) {
                Log.d(TAG, "onNext: "+taskModel.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void func10(View view){
        Observable<TaskModel> modelObservable = TaskDataSource.createObservableTaskSlow();
        modelObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TaskModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TaskModel taskModel) {
                        Log.d(TAG, "onNext: "+taskModel.toString());
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
