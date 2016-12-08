package ru.sbertech.DAO;

import org.hibernate.Session;
import ru.sbertech.Logic.Document;

import java.util.List;

public interface DocumentDao {

    void saveDocument(Document document);

    void saveDocumentInTransaction(Document document, Session session);

    void updateStorno (long id, boolean storno, Session session);

    List<Document> documentList();

    void removeDocumentById(long id);

    void removeDocumentByPurpose(String purpose);

    Document getDocumentById(long id);

}
