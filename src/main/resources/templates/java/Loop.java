while( [(${loop.getCondition()})]{
    [# th:each="loopie : ${loop.getStatements()} [# th:insert="Statement"th:with="state = ${loopie}][/]
[/]
}