[# th:insert="PackageStatement" th:with="type=${interface}"][/]

[# th:each="import : ${imports}"]import [(${import})];
[/]

[(${interface.getVisibility().toString()})] interface [(${interface.getName()})][# th:if="${interface.getSuperTypes().size()} gt 0"] extends [# th:each="type : ${interface.getSuperTypes()}"][(${type.getName()})],[/][/]{
[# th:insert="TypeBody" th:with="type=${interface}"][/]
}