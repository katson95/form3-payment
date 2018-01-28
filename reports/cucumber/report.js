$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("version.feature");
formatter.feature({
  "line": 1,
  "name": "the version can be retrieved",
  "description": "",
  "id": "the-version-can-be-retrieved",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 2,
  "name": "client makes call to GET /version",
  "description": "",
  "id": "the-version-can-be-retrieved;client-makes-call-to-get-/version",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 3,
  "name": "the client calls /version",
  "keyword": "When "
});
formatter.step({
  "line": 4,
  "name": "the client receives status code of 200",
  "keyword": "Then "
});
formatter.step({
  "line": 5,
  "name": "the client receives server version 1.0",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});