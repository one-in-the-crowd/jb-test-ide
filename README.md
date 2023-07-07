# Parser generation project
Repository contains desktop application for code interpretation and execution.
Code parser generated for given language grammar (module `grammar`). User interacts with parser via graphic interface (module `ui`) or CLI (module `app`).

## Language grammar
```
expr ::= expr op expr | ident | { expr, expr } | number | map(expr, ident -> expr)
op ::= + | - | * | / | ^
stmt ::= var ident = expr | out expr | print "string"
program ::= stmt | program stmt
```

## Modules
- `grammar` - contains original grammar for parser generation;
- `app` - module generates parser based on grammar from `grammar`;
- `ui` - contains user interface that allows to input code. Code input forwarded to parser for parsing and execution.

## Parser execution example
1. Build the project with `.gradlew build`;
2. Navigate to `cli/build/libs`;
3. Create a file with parser input;
4. Use following command for parsing `java -jar parser.jar < input.txt`.

## Distribution
Execute `gradlew :ui:package` gradle task to create application distributive for all supported OS.

## Examples
Parser usage examples located at `cli/examples`.

1. Build the project with `.gradlew build`;
2. Navigate to `cli/build/libs`;
3. Create a file with parser input;
4. Execute parsing (e.g. `java -jar parser.jar < input.txt`).

## 3rd party solution
1. [javaCC][1] - parser generator;
2. [compose mutiplatform][2] - UI framework.

[1]: https://javacc.github.io/javacc/
[2]: https://www.jetbrains.com/lp/compose-multiplatform/

