[(${class.getVisibility().toString()})] [# th:if="${class.isAbstract()}"]abstract [/][(${class.getName()})]{
        [# th:each="attribute : ${class.getFields()}"][(${attribute.getVisibility().toString()})] [(${attribute.getType().getName()})] [(${attribute.getName()})]
        [/]
}