package [(${interface.getContainer().getName()})]

[(${interface.getVisibility().toString()})] interface [(${interface.getName()})][# th:if="${interface.getSuperTypes().size()} gt 0"] extends [# th:each="type : ${interface.getSuperTypes()}"][(${type.getName()})],[/][/]{
        [# th:each="attribute : ${interface.getFields()}"][# th:insert="Attribute"][/]
        [/]
        [# th:each="method : ${interface.getMethods()}"][# th:insert="Method"][/]
        [/]
}