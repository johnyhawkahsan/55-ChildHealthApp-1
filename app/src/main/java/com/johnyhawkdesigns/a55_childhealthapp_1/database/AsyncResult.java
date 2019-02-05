package com.johnyhawkdesigns.a55_childhealthapp_1.database;

import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;

// We use AsyncTask to perform a child search will need to return the results to the repository object.
public interface AsyncResult {
    void asyncFinished(Child foundChild);
}
