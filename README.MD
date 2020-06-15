####Setup
Used Java SDK version `11.0.2` and level `7` to compile.

* To run UI tests, execute: `mvn -Dtest=Runner test`
* To run API tests, execute: `mvn -Dtest=ApiTests test`
---
####Comments
* The first UI test is different to that in the task, because full list loads 200 results by default.
* Did not use test ids provided by website devs, because it is a rare case and not always possible.
