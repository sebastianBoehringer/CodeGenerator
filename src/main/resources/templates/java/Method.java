[(${method.getVisibility().toString()})] [# th:if="${method.isAbstract()}"]abstract [/][(${method.getReturnParam().getType().getName()})] [(${method.getName()})]([# th:insert="Parameter"][/]){
[# th:each="statement : ${method.getLogic().getStatements()}"]        [# th:insert="Statement" th:with="state=${statement}"][/]
[/]
    };