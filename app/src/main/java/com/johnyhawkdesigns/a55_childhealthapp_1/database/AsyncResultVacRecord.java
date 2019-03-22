package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildVaccinationRecord;

public interface AsyncResultVacRecord {
    void asyncFinished(ChildVaccinationRecord foundVacRecord);
}
