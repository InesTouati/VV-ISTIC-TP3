# Test the Date class

Implement a class `Date` with the interface shown below:

```java
class Date implements Comparable<Date> {

    public Date(int day, int month, int year) { ... }

    public static boolean isValidDate(int day, int month, int year) { ... }

    public static boolean isLeapYear(int year) { ... }

    public Date nextDate() { ... }

    public Date previousDate { ... }

    public int compareTo(Date other) { ... }

}
```

The constructor throws an exception if the three given integers do not form a valid date.

`isValidDate` returns `true` if the three integers form a valid year, otherwise `false`.

`isLeapYear` says if the given integer is a leap year.

`nextDate` returns a new `Date` instance representing the date of the following day.

`previousDate` returns a new `Date` instance representing the date of the previous day.

`compareTo` follows the `Comparable` convention:

* `date.compareTo(other)` returns a positive integer if `date` is posterior to `other`
* `date.compareTo(other)` returns a negative integer if `date` is anterior to `other`
* `date.compareTo(other)` returns `0` if `date` and `other` represent the same date.
* the method throws a `NullPointerException` if `other` is `null` 

Design and implement a test suite for this `Date` class.
You may use the test cases discussed in classes as a starting point. 
Also, feel free to add any extra method you may need to the `Date` class.


Use the following steps to design the test suite:

1. With the help of *Input Space Partitioning* design a set of initial test inputs for each method. Write below the characteristics and blocks you identified for each method. Specify which characteristics are common to more than one method.
2. Evaluate the statement coverage of the test cases designed in the previous step. If needed, add new test cases to increase the coverage. Describe below what you did in this step.
3. If you have in your code any predicate that uses more than two boolean operators check if the test cases written to far satisfy *Base Choice Coverage*. If needed add new test cases. Describe below how you evaluated the logic coverage and the new test cases you added.
4. Use PIT to evaluate the test suite you have so far. Describe below the mutation score and the live mutants. Add new test cases or refactor the existing ones to achieve a high mutation score.

Use the project in [tp3-date](../code/tp3-date) to complete this exercise.

## Answer

### 1
#### isValideDate

| Characteristic    | Block 1                  | Block 2                    | Block 3             | Block 4  |
|-------------------|--------------------------|----------------------------|---------------------|----------|
| valeur du jour    | <1  && \> taille du mois | \>= 1 && <= taille du mois |                     |          |
| valeur du mois    | <1  && \> 12             | \>=1  && <= 12             | mois de 30/31 jours | février  | 
| valeur de l'année | leap year                | not leap year              |                     |          |

#### isLeapYear
| Characteristic    | Block 1 | Block 2          | Block 3                     | Block 4                     | 
|-------------------|---------|------------------|-----------------------------|-----------------------------|
| valeur de l'année | %4!=0   | %4==0 && %100!=0 | %4==0 && %100==0 && %400!=0 | %4==0 && %100==0 && %400==0 | 

### compareTo
| Characteristic    | Block 1 | Block 2                  | Block 3                  |
|-------------------|---------|--------------------------|--------------------------|
| valeur du jour    | égaux   | date postérieure à other | date antérieure à other  |
| valeur du mois    | égaux   | date postérieure à other | date antérieure à other  |
| valeur de l'année | égaux   | date postérieure à other | date antérieure à other  |

La caractéristique de la valeur de l'année est commune entre isValidDate et isLeapYear.

### 2 
Couverture insuffisante au 1er essai, il manquait des tests pour couvrir toutes les tailles de mois pour previousDate() :
Après leur ajout, couverture de 100% des méthodes et lignes.

### 3
La plupart des prédicats de plus d'un boolean represent soit une interval ([1-30]jours), soit une liste de possibilité pour une valeur (mois de 31 jours).
Le prédicat notable est :
```(year*10000 + month*100 + day) - (other.year*10000 + other.month*100 + other.day)```
qui permet de calculer la différence entre 2 jours en donnant plus de poids à l'année puis au mois.

Le BCC est respecté.

### 4
Au premier lancement de pitest :
Generated 76 mutations Killed 75 (99%)

Le mutant restant concernait le cas particulier de juillet et août qui ont tous 2 31 jours.
Le mutant est tué en rajoutant ces 2 données pour le test de previousDate():
``` 
Arguments.of(new Date(31,7,2023), new Date(1,8,2023)), 
Arguments.of(new Date(31,8,2023), new Date(1,9,2023)) 
```
