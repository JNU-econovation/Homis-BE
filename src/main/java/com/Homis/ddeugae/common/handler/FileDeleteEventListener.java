package com.Homis.ddeugae.common.handler;

import com.Homis.ddeugae.common.dto.DeletedFiles;
import com.Homis.ddeugae.common.util.BlobStorageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FileDeleteEventListener {
    private final BlobStorageManager blobStorageManager;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void deleteFiles(DeletedFiles files){
        files.deleteAllFiles(blobStorageManager);
    }
}
