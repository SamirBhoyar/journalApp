package org.journalApp.versionBackup.repositoryBackup;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.journalApp.versionBackup.EntityBackup.JournalEntry_v2;

public interface JournalEntryRepository_v2 extends MongoRepository<JournalEntry_v2,Object> {

}
