# aws-helpers

This is just some helper functions for AWS. Planning on adding more helpers so I'll continue to update this when time
permits. Thanks for reading & enjoy!

## Getting Started

This project is not in a releasable state. Don't use for production use.

### Prerequisites

This is what the future prereqs will be by first release:
Docker
AWS Account (with valid credentials set up) TODO : explain how to do that

```
java -jar aws-helpers.jar -i s3://bucket/key -ir us-west-1 -o s3://bucket/key -or us-west-1 -nf 10
```

### Installing

Planning on dockerizing this guy. Otherwise feel free to build the project using gradle & run.

## Running the tests

./gradlew tests

## Deployment

Build image, run container & go

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* TODO : Add gradle

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository]
(https://github.com/your/project/tags).

## Authors

* **allmarsh**

## License

## Acknowledgments

* Thanks AWS for building cool stuff