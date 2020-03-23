[# th:each="iter,branch: ${choice.getBranches()"][# th:if="${iter.first}"]if[/][
 # th:unless="${iter.first}"]else if[/] ([(${branch.first()})]{
[# th:each="choicy : ${branch.second()}"]    [# th:insert="Statement" th:with="state = ${choicy}"][/]
[/]
}
[/]