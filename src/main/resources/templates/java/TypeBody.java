[# th:each="attribute,iterStat : ${type.getFields()}"]    [# th:insert="Attribute"][/][# th:unless="${iterStat.last}"]
[/][/]

[# th:each="method,iterStat : ${type.getMethods()}"]    [# th:insert="Method" th:with="intendation=2"][/][# th:unless="${iterStat.last}"]
[/][/]