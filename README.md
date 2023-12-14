# Coding task

Local launch
`./mvnw clean package` after packaging use
`docker-compose up` for running containers


# Links
Swagger for application: 'http://localhost:8081/swagger-ui/index.html'

Admin-panel: 'http://localhost:8080'

# Modules

# application
Main service. Uploads CSV file with data, extracts and populates storage.

# admin-panel

Spring admin-server used for web services monitoring.

# Assumptions

For monitoring used `spring-admin-server`

Due to the non-functional requirement "file upload time less than 60 minutes" I assumed that it should be large CSV files.
So depends on that I implemented runtime stream reading, parsing and storing into storage records.

If record doesn't have a PRIMARY_KEY or UPDATED_TIMESTAMP is invalid format - record skipped. 

Decided to use MySQL as storage

Runtime saving works using batches. I used default `saveAll()` method for saving and this method doesn't throw `ConstraintViolationException` due the default `SimpleJpaRepository` implementation, but I added handling for case if other storage/method will be used which can throw any exceptions while data storing. By my implementation if something was thrown - we will try save this batch one-by-one and skip problematic record(can do something else with these records). 

# Possible more reliable approach
More reliable approach is to implement it asynchronously(save data to filesystem, after that send message to broker and trigger asynchronous parsing and storing), but implementation of this mechanism is beyond the scope of this task.


# Instructions 

For uploading large file please use `/upload` endpoint with content-type `application/octet-stream`
in Advanced REST Client or Postman.