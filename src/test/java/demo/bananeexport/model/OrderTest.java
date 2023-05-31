package demo.bananeexport.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

    Recipient classUnderTest;

    @BeforeEach
    void setUp() {
        this.classUnderTest = null;
    }

    @AfterEach
    void tearDown() {
        this.classUnderTest = null;
    }

    @Test
    void checkquantity(){
        /*
            Partitionnement en classes d'équivalence
            Entrée/sortie =>Quantity
            Classes valides:
                - v1 => %25
            Classes invalides:
                - u1 => <0
                - u2 => >100
                - u3 non entier
                - u4 vide
                - u5 != %25

         */
    }
}