package com.test.msdiabetes.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class MedicalTerms {
    public static final List<String> TRIGGER_TERMS = Collections.unmodifiableList(
            Arrays.asList(
                    "Hémoglobine A1C",
                    "Micro albumine",
                    "Taille",
                    "Poids",
                    "Fumeur",
                    "Anormal",
                    "Cholestérol",
                    "Vertige",
                    "Rechute",
                    "Réaction",
                    "Anticorps"
            )
    );

    private MedicalTerms() {

    }
}