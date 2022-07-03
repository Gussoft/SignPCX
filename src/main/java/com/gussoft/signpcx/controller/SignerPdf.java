package com.gussoft.signpcx.controller;

import com.gussoft.signpcx.models.SignatureModel;
import com.gussoft.signpcx.models.SignatureParameters;
import com.gussoft.signpcx.pades.SignPAdES;
import com.gussoft.signpcx.utils.Utils;

import java.io.File;
import java.util.List;

public class SignerPdf {
    public static void main(String[] args) {
        SignerPdf signerPdf = new SignerPdf();
        Utils util = new Utils();
        SignatureModel model = new SignatureModel();
        util.addElementToSign(model);
        util.SelectDirectorySave(model);
        signerPdf.getSignerPdf(model);
    }

    private void getSignerPdf(SignatureModel model) {
        List<SignatureParameters> parametersList = model.getParametersList();
        try {
            SignPAdES signPAdES = new SignPAdES(model);
            for (SignatureParameters parameters: parametersList) {
                signPAdES.getSignDocument(parameters.getFile(), model.getFolderOut(), parameters);
            }

        } catch (Exception e) {

        }

    }
}
