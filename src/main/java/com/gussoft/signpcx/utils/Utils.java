package com.gussoft.signpcx.utils;

import com.gussoft.signpcx.models.SignatureModel;
import com.gussoft.signpcx.models.SignatureParameters;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.MSCAPISignatureToken;
import eu.europa.esig.dss.token.SignatureTokenConnection;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.omg.CORBA.portable.ApplicationException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

public class Utils {

    public static SignatureTokenConnection getToken() {
        return new MSCAPISignatureToken();
    }

    public static DSSPrivateKeyEntry getSigner(List<DSSPrivateKeyEntry> keys) throws Exception {
        DSSPrivateKeyEntry selectedKey = null;
        initToolkit();
        if (eu.europa.esig.dss.utils.Utils.isCollectionEmpty(keys)) {
            throwException("No certificate found", null);
        } else if (eu.europa.esig.dss.utils.Utils.collectionSize(keys) == 1) {
            selectedKey = keys.get(0);
        } else {
            FutureTask<DSSPrivateKeyEntry> future = new FutureTask<>(new SelectCertificateTask(keys));
            Platform.runLater(future);
            selectedKey = future.get();
            if (selectedKey == null) {
                throwException("No selected certificate", null);
            }
        }
        return selectedKey;
    }

    private static void throwException(String message, Exception e) throws Exception {
        String exceptionMessage = message + ((e != null) ? " : " + e.getMessage() : "");
        throw new Exception(exceptionMessage, e);
    }

    public void addElementToSign(SignatureModel model) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF FILES", "pdf");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(filter);
        File[] files = null;
        List<SignatureParameters> parametersList = new ArrayList<>();

        int ok = fileChooser.showOpenDialog(fileChooser);
        if (ok == JFileChooser.APPROVE_OPTION) {
            files = fileChooser.getSelectedFiles();
            for (File f: files) {
                SignatureParameters parameters = new SignatureParameters();
                parameters.setFile(f);
                parameters.setX("200");
                parameters.setY("200");
                parameters.setWidth("140");
                parameters.setHeight("60");
                parameters.setPage(1);
                parameters.setTextSize(8);
                parametersList.add(parameters);
            }
        }

        model.setParametersList(parametersList);
        model.getParametersList().forEach(sign -> {
            System.out.println(sign.getFile().getAbsolutePath());
        });
    }

    public void SelectDirectorySave(SignatureModel model) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle("Seleccionar Directorio");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int ok = fileChooser.showOpenDialog(fileChooser);
        if (ok == JFileChooser.APPROVE_OPTION) {
            model.setFolderOut(fileChooser.getSelectedFile());
        }
    }

    public static void initToolkit() {
        final CountDownLatch latch = new CountDownLatch(1);
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new JFXPanel(); // initializes JavaFX environment
                    latch.countDown();
                }
            });
            latch.await();
        } catch (InterruptedException e) {

        }
    }
}
