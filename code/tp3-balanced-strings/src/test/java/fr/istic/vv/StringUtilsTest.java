package fr.istic.vv;

import org.junit.Assert;
import org.junit.Test;

import static fr.istic.vv.StringUtils.isBalanced;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {
    @Test
    public void testIsBalanced() throws Exception{
        // string vide
        Assert.assertTrue(StringUtils.isBalanced(""));
        // un seul caractère
        Assert.assertFalse(isBalanced("{"));
        Assert.assertFalse(isBalanced("["));
        Assert.assertFalse(isBalanced("("));
        // pls caractères + seulement ouvrants + taille paire
        Assert.assertFalse(isBalanced("{([["));
        // seulement fermants
        Assert.assertFalse(isBalanced("})]"));
        // les 2 en proportion inégale
        Assert.assertFalse(isBalanced("[(})]"));
        // les 2 en proportion égale
        // bon ordre
        Assert.assertTrue(isBalanced("()"));
        Assert.assertTrue(isBalanced("{}"));
        Assert.assertTrue(isBalanced("[]"));
        Assert.assertTrue(isBalanced("[({})]"));
        // mauvais ordre
        Assert.assertFalse(isBalanced(")("));
        Assert.assertFalse(isBalanced("[{(})]"));
        // taille impaire
        Assert.assertFalse(isBalanced("[{(})]("));

    }

}