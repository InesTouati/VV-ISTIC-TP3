# On assertions

Answer the following questions:

1. The following assertion fails `assertTrue(3 * .4 == 1.2)`. Explain why and describe how this type of check should be done.

2. What is the difference between `assertEquals` and `assertSame`? Show scenarios where they produce the same result and scenarios where they do not produce the same result.

3. In classes we saw that `fail` is useful to mark code that should not be executed because an exception was expected before. Find other uses for `fail`. Explain the use case and add an example.

4. In JUnit 4, an exception was expected using the `@Test` annotation, while in JUnit 5 there is a special assertion method `assertThrows`. In your opinion, what are the advantages of this new way of checking expected exceptions?

## Answer

1. A cause des problèmes de représentation des float des ordinateurs, 3*0.4 ne donnerait pas exactement 1.2. Il faudrait utiliser une méthode qui accepte un certain pas d'erreur.

2. assertEquals sert à comparer des valeurs, assertSame sert à comparer si 2 objets ont sont le même objet en mémoire.

int val1 = 17;
int val2 = val1.getInt();
assertEquals(val1, val2);
assertSame(val1, val2);

3. Ca peut être utilisé pour un test de fonctionnalité qui n'a pas encore été implémentée, et qui doit dont retourner fail jusqu'à ce qu'elle le soit.

```
@Test
public void testFeature() {
    // test
    try {
        myObject.feature();
        fail("Expected UnsupportedOperationException");
    } catch (UnsupportedOperationException e) {
        // test passed
    }
}
```

4.  Avec assertThrows on peut tester l'exception précise qui sera retourné et éviter des faux positifs en testant seulement la classe d'exception.
De plus assertThrows retourne l'exception qui a été retournée dans tous les cas, contrairement à l'annotation @Test qui informe seulement si l'exception attendu à eu lieu.

