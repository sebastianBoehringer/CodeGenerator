[(${enum.getVisibility().toString()})] enum [(${enum.getName()})][# th:if="${enum.getSuperTypes().size()} gt 0"] extends [# th:each="type : ${enum.getSuperTypes()}"][(${type.getName()})],[/][/]{
        [# th:each="literal: ${enum.getLiterals()}"][(${literal.toUpperCase()})],
        [/]
        [# th:each="attribute : ${enum.getFields()}"][# th:insert="Attribute"][/]
        [/]
        [# th:each="method : ${enum.getMethods()}"][# th:insert="Method"][/]
        [/]
}