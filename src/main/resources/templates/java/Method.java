[(${method.getVisibility().toString()})] [# th:if="${method.isAbstract()}"]abstract [/][(${method.getReturnParam().getType().getName()})] [(${method.getName()})]([# th:insert="Parameter"][/])[# th:if="${method.getExceptions().size() >0}"]
    throws [# th:each="ex,iter: ${method.getExceptions()}"][(${ex.getName()})][# th:unless="${iter.last}"], [/][/][/]{
[# th:each="statement : ${method.getLogic().getStatements()}"][# th:insert="Statement" th:with="state=${statement},intendation=${intendation}"][/]
[/]    throw new UnsupportedOperationException();};