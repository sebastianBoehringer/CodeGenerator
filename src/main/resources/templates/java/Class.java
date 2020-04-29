[# th:insert="PackageStatement" th:with="type=${class}"][/]

[# th:each="import : ${imports}"]import [(${import})];
[/]

[(${class.getVisibility().toString()})] [# th:if="${class.isFinal()}"]final [/][# th:if="${class.isAbstract()}"]abstract [/]class [(${class.getName()})][# th:if="${class.getSuperTypes().size()} gt 0"] extends [# th:each="type,iterStat : ${class.getSuperTypes()}"][(${type.getName()})][# th:unless="${iterStat.last}"], [/][/][/][# th:if="${class.getImplementedInterfaces().size()} gt 0"]implements [# th:each="iface,iterStat : ${class.getImplementedInterfaces()}"][(${iface.getName()})][# th:unless="${iterStat.last}"], [/][/][/] {
[# th:insert="TypeBody" th:with="type=${class}"][/]
}