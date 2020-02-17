# Welcome to Plushy Manufacturing API

Hi, thanks for taking a look at this project. Lets make this easy so you can get back to your important stuff.

## Quick Start
1. `mvn clean test spring-boot:run`

    a. If this doesn't work, for whatever reason, try

        mvn -N io.takari:maven:0.7.7:wrapper
        ./mvnw clean test spring-boot:run 

    as an alternative

    b. All tests should pass

2. Run a quick integration test in a new shell to ensure everything is running: 
`python ./simple_test.sh` 

    a. Test should run through without any AssertionExceptions


## API Documentation: Required Endpoints
1. /parameters/ [POST] Insert item to a parameter. Ensure proper Json in payload (view assumptions)

    a. Payload is: {"machineCode": "filler", "parameter": {"param1": 1, "param2":...}}

    NOTE: The example payload in the documentation isn't valid json. Parameters are now wrapped as a json object instead of as an array.

2. /parameters/latest [GET] Get all parameters and metadata only if there is an event. If there are events, return the latest event.

    a. All values are returned as strings. Consumer must decide what to do with type.

3. /parameters/aggregated/{minutesAsInt} Pass a positive value to minutesAsInt to get the aggregated data between that time range.


## API Documetation: Debug endpoints
1. /parameters [GET] Get all parameters and metadata WITHOUT latest
2. /parameters/aggregated/ [GET] Pass a startDate and endDate parameter to get fine grained. startDate and endDate must be ISO formatted strings.
3. /parameters/ [DELETE] Delete all events in database

## Assumptions
1. /parameters [POST] Boolean parameters are serialized to false if invalid. Valid booleans are true and false case-insensitive.
2. /parameters [POST] Int:Quantity must be greater than 0 and real:% must be between 0 and 100.
3. /parameters [POST] If any of the parameters are invalid, we abort and return invalid request. Valid parameters match in name and value to an existing parameter.
4. /parameters [POST] If same key is passed multiple times in parameters map, the last value is taken.
5. /parameters/latest will only return parameters that have an event registered. This is to avoid empty parameters. 

    - For all parameter metadata regardless of events, use /parameters[GET]

6. /parameters/aggregated/{numberOfMinutes} will only return parameters that can be aggregated on. Again we avoid empty results.
7. /parameters/aggregated/{numberOfMinutes} numberOfMinutes can not be negative.

## Nice To Haves
1. DevOps-y: Dockerized and Automated CI across environments
2. User Roles and extend API. For example, the Machine Users can register themselves and create parameters and be allowed to send events. However, Monitoring users can only request data.
3. /parameters/latest should return proper type of variable in json
4. Json on POST is validated by function with validation rules. A more elegant solution must exist.
5. JavaDocs: More documentation!
