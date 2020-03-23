[# th:if="${enumeration.getContainer()}"package [(${enumeration.getContainer().getFQName()})];[/]

[# th:each="import : ${imports}"]import [(${import})];
[/]

[(${enumeration.getVisibility().toString()})] enum [(${enumeration.getName()})][# th:if="${enumeration.getSuperTypes().size()} gt 0"] extends [# th:each="type : ${enumeration.getSuperTypes()}"][(${type.getName()})],[/][/]{
[# th:each="literal, iterStat: ${enumeration.getLiterals()}"]    [(${literal.toUpperCase()})][# th:unless="${iterStat.last}"],
[/][# th:if="${iterStat.last}"];[/][/]
[# th:each="attribute : ${enumeration.getFields()}"]    [# th:insert="Attribute"][/]
[/]
[# th:each="method : ${enumeration.getMethods()}"]    [# th:insert="Method"][/]
[/]
}