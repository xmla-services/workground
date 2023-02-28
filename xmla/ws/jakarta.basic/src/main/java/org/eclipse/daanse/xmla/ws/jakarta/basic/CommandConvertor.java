package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.xmla.api.xmla.Command;
import org.eclipse.daanse.xmla.model.record.xmla.CommandR;

import java.util.List;
import java.util.stream.Collectors;

public class CommandConvertor {

    public static List<Command> convertCommandList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Command> commandList) {
        if (commandList != null) {
            return commandList.stream().map(CommandConvertor::convertCommand).collect(Collectors.toList());
        }
        return null;

    }

    private static Command convertCommand(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Command command) {
        if(command != null) {
            //TODO
            /*return new CommandR(command.getStatement(),
                convertCreate(command.getCreate()),
                convertAlter(command.getAlter()),
                convertDelete(command.getDelete()),
                convertProcess(command.getProcess()),
                convertMergePartitions(command.getMergePartitions()),
                convertDesignAggregations(command.getDesignAggregations()),
                convertClearCache(command.getClearCache()),
                convertSubscribe(command.getSubscribe()),
                convertUnsubscribe(command.getUnsubscribe()),
                convertCancel(command.getCancel()),
                convertBeginTransaction(command.getBeginTransaction()),
                convertCommitTransaction(command.getCommitTransaction()),
                convertRollbackTransaction(command.getRollbackTransaction()),
                convertLock(command.getLock()),
                convertUnlock(command.getUnlock()),
                convertBackup(command.getBackup()),
                convertRestore(command.getRestore()),
                convertSynchronize(command.getSynchronize()),
                convertAttach(command.getAttach()),
                convertDetach(command.getDetach()),
                convertInsert(command.getInsert()),
                convertUpdate(command.getUpdate()),
                convertDrop(command.getDrop()),
                convertUpdateCells(command.getUpdateCells()),
                convertNotifyTableChange(command.getNotifyTableChange()),
                convertBatch(command.getBatch()),
                convertImageLoad(command.getImageLoad()),
                convertImageSave(command.getImageSave()),
                convertCloneDatabase(command.getCloneDatabase()),
                convertSetAuthContext(command.getSetAuthContext()),
                convertDBCC(command.getDBCC()));*/
        }
        return null;
    }

}
