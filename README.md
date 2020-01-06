**ATTENTION! This sample is built for outdated platform version. See [sample-akkount-cuba7](https://github.com/cuba-platform/sample-akkount-cuba7) for the latest version.**

Akkount
=======

This is a simple real-world application for managing personal finances. It contains some non-trivial code examples, such as customization of a visual component with client-side JavaScript code and an additional lightweight web UI for mobile devices.

Based on CUBA Platform 6.10.2

Features
--------

In short, the application solves two problems:

 1. It shows the current balance by all accounts: cash, credit cards, deposits, debts, etc.

 2. It can generate a report by expense and income categories that shows where the money came from and what they were spent on in some period of time.

Some details:

* There are _accounts_ that represent different kinds of money.

* There are _operations_: income to account, expense from account and transfer between accounts.

* A _category_ can be set for expense or income operations.

* The current balance is constantly displayed and is recalculated after each operation.

* Categories report shows the summary by two arbitrary periods of time to allow quick visual comparison. Any category can be excluded from the report. You can "drill down" into any row to see operations that comprise the row.

* The system consists of three web applications deployed onto one Tomcat instance:   
   
    - Middleware   
    - Full-functional CUBA Generic UI
    - Polymer UI for mobile devices. 

Usage
-----

- Open the project in Studio and execute **Run > Create database**, then **Run > Start application server**. 

- The application will start at `http://localhost:8080/app`. Use `admin` as both login and password to access the application. 

- You can generate test data in **Administration > JMX Console > app-core.akkount:type=SampleDataGenerator** bean. In order to create sample transactions, enter a number of days (e.g. 100) back from the current date into `generateSampleData()` method parameter and click **Invoke**. 

- After the method execution is finished, logout and login again. You will see the balance for sample accounts on the left panel.

## Issues
Please use https://www.cuba-platform.com/discuss for discussion, support, and reporting problems coressponding to this sample.
