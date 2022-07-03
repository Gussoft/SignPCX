package com.gussoft.signpcx.models;

import java.io.File;
import java.util.List;

public class SignatureModel {

    private String signatureImagen;
    private String signatureText;
    private List<SignatureParameters> parametersList;

    private File folderOut;

    public SignatureModel() {
    }

    public String getSignatureImagen() {
        return signatureImagen;
    }

    public void setSignatureImagen(String signatureImagen) {
        this.signatureImagen = signatureImagen;
    }

    public String getSignatureText() {
        return signatureText;
    }

    public void setSignatureText(String signatureText) {
        this.signatureText = signatureText;
    }

    public List<SignatureParameters> getParametersList() {
        return parametersList;
    }

    public void setParametersList(List<SignatureParameters> parametersList) {
        this.parametersList = parametersList;
    }

    public File getFolderOut() {
        return folderOut;
    }

    public void setFolderOut(File folderOut) {
        this.folderOut = folderOut;
    }
}
