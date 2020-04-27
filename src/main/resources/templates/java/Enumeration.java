[# th:insert="PackageStatement" th:with="type=${enumeration}"][/]

[# th:each="import : ${imports}"]import [(${import})];
[/]

[(${enumeration.getVisibility().toString()})] enum [(${enumeration.getName()})][# th:if="${enumeration.getSuperTypes().size()} gt 0"] extends [# th:each="type : ${enumeration.getSuperTypes()}"][(${type.getName()})],[/][/]{
[# th:each="literal, iterStat: ${enumeration.getLiterals()}"]    [(${literal.toUpperCase()})][# th:unless="${iterStat.last}"],
[/][# th:if="${iterStat.last}"];[/][/]
[# th:insert="TypeBody" th:with="type=${enumeration}"][/]
}