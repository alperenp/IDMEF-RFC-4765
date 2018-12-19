# Java RFC 4765 (IDMEF) implementation

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.alperenp/tr.alperenp.idmef/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.alperenp/tr.alperenp.idmef)

## Description
This project is responsible for implementation of RFC 4765 standard. Contains the models defined in standard and de/serialization classes for io operations again defined in standard.

To test model, the examples from RFC site and some hand crafted samples are used according to the definitions. All de/serialization tests and acceptance tests are currently satisfying except the known issues below.

## Known Issues
- Time precision in serialization does not satisfy the definitions in standard. Milisecond part can have up to 5 digits in standard. However, serialization is limited up to 3 digits for miliseconds in serialization implementation.
  - Due to this problem, samples taken from standard are crafted to satisfy the test conditions