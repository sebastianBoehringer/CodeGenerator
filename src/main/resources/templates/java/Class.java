[(${class.getVisibility().toString()})] [# th:if="${class.isAbstract()}"]abstract [/]class [(${class.getName()})][# th:if="${class.getSuperTypes().size()} gt 0"] extends [# th:each="type,iterStat : ${class.getSuperTypes()}"][(${type.getName()})][# th:unless="${iterStat.last}"], [/][/][/] {
[# th:each="attribute,iterStat : ${class.getFields()}"]    [# th:insert="Attribute"][/][# th:unless="${iterStat.last}"]
[/][/]

[# th:each="method,iterStat : ${class.getMethods()}"]    [# th:insert="Method"][/][# th:unless="${iterStat.last}"]
[/][/]
}