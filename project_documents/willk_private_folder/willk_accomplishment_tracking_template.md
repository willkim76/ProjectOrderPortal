# William Kim - Lucas and the Willies Accomplishment Tracking

Each team member should have their own version of this document.

## Background

It's a great habit to record accomplishments and progress throughout your SDE
career. It's useful to reflect on what you've worked on in the past and comes in
handy during performance reviews and promotion cycles.

## Instructions

**Save a copy of this document in your “private” folder.**

Using the below template, keep track of what you’ve worked on each week during
the unit. 1-3 bullets under each section for each week should suffice. This
should only take 5 - 10 minutes of reflection each week.

As you track your work, think about how it relates to the SDE fundamental skills
laid out in the syllabus and how you are practicing them.

* Converts a design into code and delivers it using best practices
* Writes secure, testable, maintainable code
* Understands when to use (or not) a broad range of data structures and
  algorithms
* Creates unit tests that thoroughly test functionality
* Troubleshoots by debugging and reviewing errors, logfiles, and metrics
* Contributes to planning and design
* Escalates when projects hit roadblocks and risks

The important work samples don’t only include the things you authored, but
should include things like key CRs you reviewed that you are proud of as well!

_You will submit your completed Accomplishment Tracking Document to your
instructors by the end of the unit._

## Week 1

**Goals:**

1. Understand the role of a Scrum Master
2. Develop the initial design and technical requirements with the team
3. Determine the responsibilities of each team member
4. Establish the project environment.

**Activity:**

1. Initiated team meetings to develop the design and technical requirments of the project
2. Completed the prework for the Scrum Master role
3. Set the project environments modules, SDK, and overall structure

**Important Docs, Commits, or Code Reviews**:

**Things learned:**

1. The role and responsibility of Scrum Master
2. Estimating resources by difficulty of the task rather than time
3. Setting up a Gradle project in Intellij
4. Using build.gradle to pass external libraries into the project environment

## Week 2

**Goals:**

1. Implement the design and technical requirements of the project
2. Refine the design and technical requirements of the project

**Activity:**

1. Designed and defined the DAO, Dagger Modules/Dependency Injection, Utilities Classes, Converter Classes, Types, Data, and 
    Custom Exception classes 
2. Designed and Implemented the PlaceOrderActivity and associated Provider class
4. Designed and Implemented the UpdateOrderActivity and associated Provider class
4. Implemented unit tests for written code as appropriate

**Important Docs, Commits, or Code Reviews**:

1. Commited DAO, Dagger Modules/Dependency Injection, Utilities Classes, Converter Classes, Types, Data, and Custom Exceptions
2. Commited PlaceOrderActivity and associated Provider classes
3. Reviewed Java code base after major merge between two branches. Identified merge conflicts, escalated to Git Manager to resolve.

**Things learned:**

1. How to store arbitrary/custom data types to DyanamoDB using the DynamoDBTypeConverter interface with Project POJOs
2. How to deserialize a JSON file and map the data to a Java POJO using a 3rd party library JSON.simple

## Week 3

**Goals:**

1. Implement additional endpoint to incorporate Global Secondary Indexes into DynamoDB tables
2. Research and create/write Integeration Test plans. 
3. Import Java code into AWS/Amazon environments
4. Deploy a functional API

**Activity:**

1. Completed writing unit tests
2. Researched and watch videos about the different technologies/approaches for integeration tests
3. Reserached how to form DynamoDB quries respective to parition/range keys and non-key attributes.
4. Implemented and completed manual Integration test classes for all endpoints
5. Peformed AWS Lambda testing

**Important Docs, Commits, or Code Reviews**:

1. Performed a code review for the GetOrderActivity endpoint
2. Performed a code review for the DeleteOrderActivity endpoint
3. Performed a code review for the CreateSalesReportActivity

**Things learned:**

1. A query requires a full parition key or a partial range key match and filters can only be applied to non-key attributes
3. Do not enforce invariants of a POJO when the POJO is used to generate a query expression as certain attributes can be missing 
when generating the the associated Object for withTheHashKey method
4. A FileReader is exhausted once parsed. Attempting to read the same FileReader will result in an exception
5. The distinction between LocalDateTime and ZonedDateTime and how to set the ZonedDateTime for different regions
6. How to format a JSON Map for Lambda testing with a JSON Event within AWS Lambda
7. The characters that are Reserved, Unsafe, and Unreserved for URL
8. How to format the Integration Response to return proper response codes based on an error message thrown by Lambda

## Week 4

**Goals:**

**Activity:**

**Important Docs, Commits, or Code Reviews**:

**Things learned:**
