
SBT manages your projects dependencies, and provides many tasks for you to run.

You can run a task by providing it as an argument to SBT. (E.g. `sbt run`)

You can run multiple tasks by providing multiple arguments to SBT. (E.g. `sbt clean run`)

### Useful tasks
* `compile` will compile all your production code
* `clean` will delete the compiled code
* `test` runs all the tests in a project
* `run` starts the application in development mode
* `start` starts the application in production mode

### Continually running a task
By prefixing any task with a tilde `~`, SBT will continually run the task on any source change.   

E.g. `sbt ~test` will run all the tests after every change

### Useful test tasks
* **`test-only`** lets you restrict the tests:
    * `./sbt "test-only *.HotelsControllerSpec"`
* `test-only` can be combined with `~` and can run multiple tests:
    * `./sbt "~test-only *.HotelsControllerSpec *.HotelsServiceTest"`
* **`test-quick`** will only run tests that failed in the previous run. 
    * `sbt ~test-quick`  This will run all tests, and then only the tests that have failed.