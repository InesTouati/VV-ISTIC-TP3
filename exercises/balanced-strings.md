# Balanced strings

A string containing grouping symbols `{}[]()` is said to be balanced if every open symbol `{[(` has a matching closed symbol `]}` and the substrings before, after and between each pair of symbols is also balanced. The empty string is considered as balanced.

For example: `{[][]}({})` is balanced, while `][`, `([)]`, `{`, `{(}{}` are not.

Implement the following method:

```java
public static boolean isBalanced(String str) {
    ...
}
```

`isBalanced` returns `true` if `str` is balanced according to the rules explained above. Otherwise, it returns `false`.

Use the coverage criteria studied in classes as follows:

1. Use input space partitioning to design an initial set of inputs. Explain below the characteristics and partition blocks you identified.
2. Evaluate the statement coverage of the test cases designed in the previous step. If needed, add new test cases to increase the coverage. Describe below what you did in this step.
3. If you have in your code any predicate that uses more than two boolean operators check if the test cases written so far satisfy *Base Choice Coverage*. If needed add new test cases. Describe below how you evaluated the logic coverage and the new test cases you added.
4. Use PIT to evaluate the test suite you have so far. Describe below the mutation score and the live mutants. Add new test cases or refactor the existing ones to achieve a high mutation score.

Write below the actions you took on each step and the results you obtained.
Use the project in [tp3-balanced-strings](../code/tp3-balanced-strings) to complete this exercise.

## Answer

## 1

| Characteristic          | Block 1       | Block 2         | Block 3                    | Block 4                    | Block 5 |
|-------------------------|---------------|-----------------|----------------------------|----------------------------|---------|
| Taille du string        | 0             | 1               | >1                         | paire                      | impaire |
| Type de symbole         | ouvrants      | fermants        | les 2 en proportion inégale| les 2 en proportion égale  |         | 
| Ordre des symboles      | ordre correct | ordre incorrect |                            |                            |         |

## 2
Couverture de 100%.
Écriture de tests correspondants aux blocs définis. Les tests sont commentés.

## 3
Il y a 2 prédicats concernés :
```
current == '(' || current == '[' || current == '{'
```

```
(last == '(' && current == ')') ||
(last == '[' && current == ']') ||
(last == '{' && current == '}')
```

Les tests écrits jusqu'à présent satisfont déjà le BCC.

## 4
Première execution :
18/20 mutants tués (90%)
```
 13 		
1. Replaced integer modulus with multiplication → SURVIVED
2. negated conditional → KILLED

14 		
1. replaced boolean return with true for fr/istic/vv/StringUtils::isBalanced → KILLED

17 		
1. changed conditional boundary → KILLED
2. negated conditional → KILLED

19 		
1. negated conditional → KILLED
2. negated conditional → KILLED
3. negated conditional → KILLED

20 		
1. removed call to java/util/LinkedList::addLast → KILLED

23 		
1. negated conditional → KILLED

24 		
1. replaced boolean return with true for fr/istic/vv/StringUtils::isBalanced → KILLED

27 		
1. negated conditional → KILLED
2. negated conditional → KILLED

28 		
1. negated conditional → KILLED
2. negated conditional → KILLED

29 		
1. negated conditional → KILLED
2. negated conditional → KILLED

32 		
1. replaced boolean return with true for fr/istic/vv/StringUtils::isBalanced → KILLED

35 		
1. replaced boolean return with false for fr/istic/vv/StringUtils::isBalanced → KILLED
2. replaced boolean return with true for fr/istic/vv/StringUtils::isBalanced → SURVIVED
```

Le 1er mutant n'est pas tuable.
Replaced integer modulus with multiplication → SURVIVED
En effet         
```
if(len%2 == 1){ return false}
```
permet d'éviter des tests inutiles si la ligne est impaire (il ne peut y avoir que des pairs de portes).
len*1 == 1 ne provoque pas d'erreur dans les tests mais ne peut pas non plus faire valider de string à tors, puis la condition sera toujours fausse (len étant un integer).

La correction apportée pour corriger l'autre mutant était de rajouter un symbole dans ce test :
Assert.assertFalse(isBalanced("{([")); pour "{([[".
Ce string était éliminé par le test de chaîne impair et aucun des tests ne fournissait de lifo non vide.
