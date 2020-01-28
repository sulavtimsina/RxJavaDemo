package com.sulav.rxjavademo.repository

import com.sulav.rxjavademo.model.TaskModel
import io.reactivex.Observable

class TaskDataSource{

    companion object {
        /**
         * Returns the list abruptly
         */
        @JvmStatic
        fun createTasksList(): List<TaskModel> {
            var task1 = TaskModel("Eat food", true, 1)
            var task2 = TaskModel("Dance", false, 2)
            var task3 = TaskModel("Drive", true, 3)
            var task4 = TaskModel("Work", false, 4)
            var task5 = TaskModel("Drink", true, 5)
            var task6 = TaskModel("Run", false, 6)
            var task7 = TaskModel("Cry", true, 7)
            var task8 = TaskModel("Walk", false, 8)
            var task9 = TaskModel("Go home", true, 9)
            var task10 = TaskModel("Do Code", false, 10)

            var tasksList: List<TaskModel> = listOf(task1, task2, task3, task4, task5, task6, task7, task8, task9, task10)

            return tasksList
        }

        /**
         * Returns the list after 5 seconds and simulates slow database operation
         */
        @JvmStatic
        fun createTaskListSlow(): List<TaskModel> {
            Thread.sleep(5000)
            return createTasksList()
        }

        /**
         * Returns the Observable task after 5 seconds and simulates slow database operation
         */
        @JvmStatic
        fun createObservableTaskSlow(): Observable<TaskModel> {
            Thread.sleep(5000)
            return Observable.just(TaskModel("Go Office", true, 11))
        }



    }

}