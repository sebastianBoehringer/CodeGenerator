[# th:each="branch,iter: ${choice.getBranches()}"][# th:if="${iter.first}"]if[/][# th:unless="${iter.first}"][(${"    ".repeat(intendation)})]else if[/] ([(${branch.first()})]){
[# th:each="choicy : ${branch.second()}"][# th:insert="Statement" th:with="state = ${choicy}, intendation=${intendation+1}"][/]
[/]
[(${"    ".repeat(intendation)})]}
[/]