package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.ChildMedicalHistory;

public interface AsyncResultMedHistory{
    void asyncFinished(ChildMedicalHistory foundMedHistory);
}